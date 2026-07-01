# Architecture Review

This document records the current architecture and a proposed refactoring direction for the project. The goal is to make the repository easier to understand and evolve while preserving the original academic project and its working behavior.

## Current Package Map

The project currently contains 141 production Java classes and 81 test classes under one root package.

```text
repolezanettiperuzzi
  application
    actions
  client
  common
  controller
  domain
    cards
      publiccards
      toolcards
  infrastructure
    client
      rmi
      socket
  model
  presentation
    cli
    gui
  shared
    dto
```

### `model`

Core game concepts:

- `GameBoard`
- `Player`
- `Window`
- `Box`
- `Die`
- `DiceBag`
- `RoundTrack`
- `Colour`
- `Value`
- `Deck`

This package contains the main board state and most domain objects. Some objects expose mutable internal state or contain utility methods used by controller and card logic.

### `application.actions`

Application/game-use-case classes:

- round/turn flow: `BeginRound`, `BeginTurn`, `EndRound`
- setup: `InitializeGame`, `SetWindowAction`, `TakeTwoCardWindowAction`
- player actions: `InsertDieWithCheckAction`, `UseCardAction`
- support/parsing: `CreateListForCardAction`, `CreateListForInsertDieAction`, `ParametersRequestCardAction`, `WhichErrorAction`
- scoring: `CalculateScore`
- validation: `CheckCostToolCardAction`

These classes are close to a Command/use-case layer. They now live outside `model` to make the application-flow responsibility clearer.

### `domain.cards.publiccards`

Public scoring card behavior:

- abstract base: `PublicCard`
- factory: `FactoryPublicCard`
- one concrete class per public card

This is a Strategy-style design. Each public card computes its score through `effect(Window)`.

### `domain.cards.toolcards`

Tool card behavior:

- abstract base: `ToolCard`
- factory: `FactoryToolCard`
- one concrete class per tool card

This is a Strategy-style design. Each tool card implements its own `check` and `effect`.

### `controller`

Server-side orchestration:

- state machine: `ControllerState`, `SetConnectionState`, `FetchState`, `BeginRoundState`, `TurnState`, `EndRoundState`, `EndGameState`, `ResetGameState`
- network handlers: socket and RMI handlers/stubs/skeletons
- server entry point: `MasterGame`
- timers and shutdown hooks

This is where game flow, networking, timers, and player connection handling meet. It is the most coupled area of the project.

### `client`

Client-side application coordination:

- entry point and client coordinator: `GameView`
- selects CLI or JavaFX presentation
- owns the chosen socket/RMI client transport

This package keeps the client startup and cross-presentation coordination separate from rendering and transport details.

### `presentation`

Client-side presentation:

- CLI: `GameViewCLI`, `ConsoleInputReadTask`, `CLITimer`
- JavaFX GUI: `GameViewGUI`, `FXMLController`, `*FXMLController`, `WindowGenerator`, `Coordinates`
- presentation helpers: `ErrorFactory`

Presentation code still calls the client coordinator for user actions, but rendering code is no longer mixed with socket/RMI implementation classes.

### `infrastructure.client`

Client-side transport adapters:

- Socket: `GameViewSocket`, socket message parsers, outgoing message builders
- RMI: `GameViewRMIServer`, client callback/export contract

This package owns client transport details and keeps wire-message parsing away from CLI/JavaFX rendering classes.

### `common`

Shared transport contracts:

- RMI interfaces: `ClientStubRMI`, `ControllerStubRMI`
- path helper: `DynamicPath`

### `shared`

Client-facing transfer objects:

- DTOs: `shared.dto.*`

These classes transfer game state to the client without exposing the server model directly.

## Patterns In Use

### MVC

The project has a clear MVC intention:

- Model: `model`
- View: `presentation`
- Client coordinator: `client`
- Controller: `controller`

The boundaries are not strict. Some application actions still contain domain details, controllers know network details directly, and the client coordinator still bridges presentation and transport choices.

### State

Strongly present in the controller flow:

- `ControllerState`
- `SetConnectionState`
- `FetchState`
- `BeginRoundState`
- `TurnState`
- `EndRoundState`
- `EndGameState`
- `ResetGameState`

This is one of the strongest design choices in the project and should be preserved.

### Strategy

Strongly present in card behavior:

- `ToolCard` with concrete tool-card implementations
- `PublicCard` with concrete public-card implementations

This should also be preserved. It maps well to Sagrada's card-based rules.

### Factory

Present in:

- `FactoryToolCard`
- `FactoryPublicCard`

These factories translate card identifiers from resource files into concrete strategy classes.

### DTO

Present in:

- `shared.dto.*`

These wrappers are client-facing transfer objects.

### Proxy / Remote Facade

Present in the RMI and socket communication classes:

- `ControllerStubRMI`
- `ClientStubRMI`
- socket handlers and stubs

The concept is useful, but the naming is inconsistent and the transport layer is tightly coupled to controller state.

## Main Architectural Issues

1. Some `application.actions` classes still mix domain rules with application orchestration.
2. Controller states handle game flow and network delivery at the same time.
3. Client-side UI rendering and transport are now split, but `GameView` still coordinates presentation, transport, and client-side state updates.
4. Static mutable state in `BeginRound`, `BeginTurn`, and `TurnState` makes testing and multiple game sessions fragile.
5. String protocols are parsed in several places with positional assumptions.
6. DTO classes are separated from transport contracts, but they are still mutable client snapshots that could be refined over time.
7. Some classes expose internal mutable state or rely on shallow copies.
8. Error handling is integer/string-code based and spread across model, controller, client, and presentation code.

## Target Package Structure

A possible long-term structure:

```text
repolezanettiperuzzi
  domain
    board
    dice
    player
    cards
      publiccards
      toolcards
    scoring
    rules
  application
    setup
    turn
    actions
    state
  infrastructure
    persistence
    rmi
    socket
  presentation
    cli
    gui
  shared
    dto
    errors
    config
```

This structure separates:

- domain rules from application flow
- application flow from network transport
- network transport from UI rendering
- shared DTOs/config/errors from implementation-specific packages

## Refactor Principles

1. Keep behavior stable with tests before moving code.
2. Prefer small package moves over rewrites.
3. Rename only when the new name clarifies responsibility.
4. Preserve the original game quirks unless they block maintainability.
5. Keep public card and tool card Strategy designs.
6. Keep the controller State design, but reduce its dependency on socket/RMI details over time.
7. Add tests around behavior before changing classes with static state or network flow.

## Suggested Refactor Order

### Phase 1: Documentation And Baseline

- Keep the modern Maven build and CI green.
- Add this architecture review.
- Add a short README section explaining that this is the preserved bachelor project plus modernization work.

### Phase 2: Naming And Package Clarity

Completed:

- `common.modelwrapper` -> `shared.dto`
- `model.actions` -> `application.actions`
- `model.publiccards` -> `domain.cards.publiccards`
- `model.toolcards` -> `domain.cards.toolcards`

Do one move per commit and run the full test suite after each move.

### Phase 3: Domain Cleanup

Completed:

- replace raw string restrictions such as `"both"`, `"none"`, `"value"`, `"colour"` with `BoxRestriction`
- make `Window`, `Box`, and `Die` copies independent from their mutable source objects
- copy `GameBoard` collection inputs and outputs for draft dice, window pool, player list, and window choices
- add `ActionResult` to name action and tool-card validation codes while keeping legacy integer bridges
- migrate shared `ToolCard` validation helpers to `ActionResult`
- migrate concrete tool-card checks to `ActionResult`
- use `ActionResult` across insert-die and tool-card controller flows
- extract shared action-error response handling in `TurnState`
- extract shared parameter-request and not-your-turn response handling in `TurnState`
- extract shared turn-notification and view-update delivery helpers in `TurnState`
- extract single-player end-game notification handling in `TurnState`
- add named `Player` queries for connection and UI checks used by `TurnState`
- use named `Player` connection queries across controller states
- introduce `PlayerConnection` and `PlayerInterface` enums behind the legacy player strings
- use `PlayerConnection` when branching on incoming connection requests


### Phase 4: Application Flow Cleanup

Completed:

- add explicit tests for the current forward-then-backward turn order before replacing static turn state
- introduce a non-static `TurnTracker` with matching turn-order tests before wiring it into `BeginTurn`
- make `BeginTurn` delegate its static turn counters to `TurnTracker` while preserving the existing API
- introduce a non-static `RoundTracker` with tests before wiring it into `BeginRound`
- make `BeginRound` delegate its static round counters to `RoundTracker` while preserving the existing API
- introduce a `TurnStateTracker` with tests before wiring it into `TurnState`
- make `TurnState` delegate its static flags to `TurnStateTracker` while preserving turn flow
- introduce a `GameSession` object to hold per-game round, turn, and turn-state trackers
- give `Controller` an explicit `GameSession` before moving flow state into it
- bind `TurnState` to the controller-owned turn-state tracker
- add a session-backed `BeginTurn` instance path while preserving the legacy static API
- add a session-backed `BeginRound` instance path while preserving the legacy static API
- let `GameSession` create session-backed round and turn flow actions
- add a session-backed `EndRound` path and expose it through `GameSession`
- expose session-backed flow actions and flow queries through `Controller`
- expose session-backed turn commands through `Controller`
- wire controller round and turn flow to session-backed state
- make default round and turn actions use instance trackers instead of shared static trackers
- migrate round and turn action tests away from legacy static flow state
- remove unused static round and turn flow API bridges
- simplify round and turn action method names after removing the legacy static APIs

Reduce static mutable state:

- move round and turn counters into a per-game state object
- remove static flags from `TurnState`
- make controller states operate on explicit game session state

### Phase 5: Transport Boundaries

Completed:

- introduce a tested `SocketClientMessage` parser before separating socket command dispatch
- introduce typed socket client actions while preserving the existing wire command strings
- extract a named request object for socket init parameters
- extract a named request object for socket insert-die parameters
- extract a named request object for socket tool-card response parameters
- extract a named request object for socket choose-card parameters
- extract a named request object for socket chosen-window parameters
- extract a named request object for socket exit parameters
- split socket message dispatch into focused private handler methods
- remove stale socket handler imports and fields
- extract a named request object for RMI init parameters
- extract a named request object for RMI insert-die parameters
- extract a named request object for RMI tool-card response parameters
- extract a named request object for RMI chosen-window parameters
- extract a named request object for RMI exit parameters
- extract a named request object for RMI choose-card parameters
- replace duplicated socket/RMI exit-scene enums with one shared transport value
- centralize hyphen-to-space decoding for socket/RMI text parameters
- centralize space-to-hyphen encoding for socket/RMI text parameters
- centralize payload extraction for command-prefixed transport messages
- simplify socket message parameter slicing with the standard library


Phase 5 result:

- socket and RMI command parameters are now represented by small request/value objects
- socket command dispatch is easier to scan because parsing and per-command handling are separated
- shared transport conventions, such as text encoding and exit-scene names, live in focused helpers
- the changes intentionally preserve the existing wire protocol and game flow

Separate game commands from socket/RMI parsing:

- define command/request objects for client actions
- parse socket messages into commands in the infrastructure layer
- let controller/application services consume commands independent of transport

### Phase 6: Presentation Cleanup

Completed client socket cleanup slice:

- introduce a tested `GameViewSocketMessage` parser before separating client socket message handling
- introduce typed client socket actions while preserving the existing wire message strings
- extract a named payload object for client socket updated-player messages
- extract a named payload object for client socket turn messages
- introduce named reasons for client socket not-registered messages
- introduce named destinations for client socket change-view messages
- centralize first-payload-token access for simple client socket messages
- extract client socket choose-window payload parsing
- extract client socket show-window payload parsing
- extract client socket board-update payload parsing
- centralize client socket outbound message sending
- introduce typed client socket commands while preserving outgoing wire strings
- extract client socket sub-dispatch handlers for registration and view-change messages
- extract tested builders for client socket outbound wire messages

Client socket cleanup result:

- client socket inbound messages are now represented by focused parser/value objects
- `GameViewSocket.handleMessage` coordinates view updates instead of parsing each payload inline
- outgoing client socket messages are built by tested helpers before being sent
- the existing socket wire protocol and game flow are intentionally unchanged

Started client communication boundary slice:

- moved outgoing client socket wire-message builders into `infrastructure.client.socket`
- kept `GameViewSocket` as the view-facing coordinator while it imports the infrastructure builder
- kept the outgoing socket command enum package-private inside the infrastructure package
- preserved every outgoing socket message string through focused tests
- moved inbound client socket message parsing into `infrastructure.client.socket`
- kept view payload objects responsible only for converting parsed socket tokens into view DTO updates
- preserved every covered inbound socket parser and payload conversion through focused tests
- moved the simple turn and updated-player socket payload converters into `infrastructure.client.socket`
- kept `GameViewSocket` responsible for calling the corresponding `GameView` update methods
- moved choose-window/show-window socket payload converters and shared window payload parsing into `infrastructure.client.socket`
- kept the window payload parser package-private because only socket payload converters need it
- moved the board-update socket payload converter into `infrastructure.client.socket`
- completed the move of client socket payload message converters out of the `view` package
- moved socket not-registered reasons and change-view destinations into `infrastructure.client.socket`
- left the old `view` package with only client/presentation coordinators at that point instead of socket wire-value helper types
- extracted one-shot client socket sending into `ClientSocketConnection`
- kept `GameViewSocket` responsible for choosing which outgoing message to send
- extracted client socket callback listening into `ClientSocketMessageServer`
- kept `GameViewSocket` as the `GameView`-facing coordinator for received messages
- introduced `ClientSocketView` so `GameViewSocket` depends on a callback contract instead of concrete `GameView`
- kept `GameView` implementing that callback contract without changing any callback behavior
- moved `GameViewSocket` itself into `infrastructure.client.socket`
- left `GameView` responsible for selecting socket transport and owning the view callbacks
- removed the empty unused `GameViewRMI` placeholder and its unused `GameView` field
- introduced `ClientRmiView` so `GameViewRMIServer` depends on an RMI callback/export contract instead of concrete `GameView`
- kept `GameView` implementing the RMI callback contract without changing RMI callback behavior
- moved `GameViewRMIServer` into `infrastructure.client.rmi`
- left `GameView` responsible for selecting RMI transport and owning the RMI callback implementation
- moved `CLITimer` into `presentation.cli`
- kept `GameViewCLI` responsible for scheduling and updating the CLI waiting-room timer
- moved `ConsoleInputReadTask` into `presentation.cli`
- kept `GameViewCLI` responsible for creating the timed CLI input task and handling its result
- moved `ShutdownConsole` into `presentation.cli`
- removed a stale controller import of the CLI shutdown helper
- moved `Coordinates` into `presentation.gui`
- kept `GameFXMLController` and `WindowGenerator` responsible for window-cell selection behavior
- moved `ErrorFactory` into the shared `presentation` package
- kept `GameView` responsible for routing resolved error messages to CLI or JavaFX
- moved `WindowGenerator` into `presentation.gui`
- kept JavaFX controllers responsible for choosing when to render generated window grids
- moved `GameViewCLI` into `presentation.cli`
- kept `GameView` responsible for choosing CLI vs JavaFX and delegating updates to the selected presentation
- moved `GameViewGUI` into `presentation.gui`
- kept `GameView` responsible for launching JavaFX and `DynamicPath` using the GUI class as its jar-path anchor
- moved the base `FXMLController` marker into `presentation.gui`
- kept concrete JavaFX controllers behavior unchanged while importing the new base type
- moved `WaitingRoomFXMLController` into `presentation.gui`
- kept waiting-room timer refresh and choose-window transition behavior unchanged
- moved `ChooseWindowFXMLController` into `presentation.gui`
- kept window-choice rendering and game-scene transition behavior unchanged
- moved `GameFXMLController` into `presentation.gui`
- kept game-scene rendering, turn notifications, and game-scene alerts unchanged
- moved `LoginFXMLController` into `presentation.gui`
- completed the move of JavaFX controllers and GUI helpers out of the old `view` package
- moved `GameView` into the new `client` package
- removed the last class from the old `view` package
- updated CLI and JavaFX imports to depend on `client.GameView`
- kept `GameView` responsible for selecting CLI/JavaFX and socket/RMI transports
- removed stale unused imports from `ShutdownRMIServer`
- removed the accidental server-controller source dependency on `client.GameView`
- removed empty unused `HandlerGameViewRMI` and `HandlerGameViewSocket` placeholders
- removed tracked macOS `.DS_Store` files now covered by `.gitignore`
- made deck card loading count only real `.txt` card definitions instead of hidden metadata files
- introduced `ClientViewActions` as the presentation-to-client coordinator contract
- typed CLI and JavaFX presentation classes against `ClientViewActions` instead of concrete `GameView`
- removed the empty old `view` directory from the workspace
- split JavaFX-specific coordinator registration into `ClientGuiActions`
- kept CLI presentation code depending only on the general `ClientViewActions` contract
- removed unused private client coordinator fields while keeping socket listener startup unchanged

Separate UI from networking:

- keep CLI rendering in `presentation.cli`
- keep JavaFX controllers in `presentation.gui`
- move client socket/RMI communication into `infrastructure`
- make both CLI and GUI consume the same client-side DTO/update model

## Next Concrete Refactor Candidates

Good candidates for the next Phase 6 slice:

1. Identify the smallest client-side communication class to move toward an `infrastructure` package.
2. Keep CLI and JavaFX behavior unchanged while moving one communication boundary at a time.
3. Preserve the existing socket/RMI wire protocol until the presentation split is complete.

Avoid starting with:

- rewriting networking
- replacing both CLI and GUI flows at once
- changing all packages in one commit
- changing game rules and structure in the same commit

## Notes

The `affo` scoring branch is an intentional easter egg from the original project and should be preserved unless the project goals change.
