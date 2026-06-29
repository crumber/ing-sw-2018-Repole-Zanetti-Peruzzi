# Architecture Review

This document records the current architecture and a proposed refactoring direction for the project. The goal is to make the repository easier to understand and evolve while preserving the original academic project and its working behavior.

## Current Package Map

The project currently contains 101 production Java classes and 46 test classes under one root package.

```text
repolezanettiperuzzi
  application
    actions
  common
  controller
  domain
    cards
      publiccards
      toolcards
  model
  shared
    dto
  view
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

### `view`

Client-side presentation:

- CLI: `GameViewCLI`, `ConsoleInputReadTask`, `CLITimer`
- JavaFX GUI: `GameViewGUI`, `FXMLController`, `*FXMLController`, `WindowGenerator`
- client networking: `GameView`, `GameViewSocket`, `GameViewRMI`, `GameViewRMIServer`
- presentation helpers: `ErrorFactory`, `Coordinates`

The package mixes UI rendering, client state, user input, networking, and JavaFX controller logic.

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
- View: `view`
- Controller: `controller`

The boundaries are not strict. Some application actions still contain domain details, controllers know network details directly, and views contain networking and state-update logic.

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
3. The view package mixes UI rendering, client networking, and client-side state updates.
4. Static mutable state in `BeginRound`, `BeginTurn`, and `TurnState` makes testing and multiple game sessions fragile.
5. String protocols are parsed in several places with positional assumptions.
6. DTO classes are separated from transport contracts, but they are still mutable client snapshots that could be refined over time.
7. Some classes expose internal mutable state or rely on shallow copies.
8. Error handling is integer/string-code based and spread across model, controller, and view.

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

In progress:

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

Reduce static mutable state:

- move round and turn counters into a per-game state object
- remove static flags from `TurnState`
- make controller states operate on explicit game session state

### Phase 5: Transport Boundaries

Separate game commands from socket/RMI parsing:

- define command/request objects for client actions
- parse socket messages into commands in the infrastructure layer
- let controller/application services consume commands independent of transport

### Phase 6: Presentation Cleanup

Separate UI from networking:

- keep CLI rendering in `presentation.cli`
- keep JavaFX controllers in `presentation.gui`
- move client socket/RMI communication into `infrastructure`
- make both CLI and GUI consume the same client-side DTO/update model

## First Concrete Refactor Candidates

Good candidates because they are useful and relatively contained:

1. Replace static turn/round state with a `GameSession` or `TurnTracker`.
2. Move socket message parsing out of `HandlerControllerSocket`.
3. Continue replacing legacy integer bridges at application/controller boundaries.

Avoid starting with:

- rewriting networking
- replacing both CLI and GUI flows at once
- changing all packages in one commit
- changing game rules and structure in the same commit

## Notes

The `affo` scoring branch is an intentional easter egg from the original project and should be preserved unless the project goals change.
