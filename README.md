# Sagrada

[![CI](https://github.com/crumber/ing-sw-2018-Repole-Zanetti-Peruzzi/actions/workflows/ci.yml/badge.svg)](https://github.com/crumber/ing-sw-2018-Repole-Zanetti-Peruzzi/actions/workflows/ci.yml)

Java implementation of the board game **Sagrada**, developed as a bachelor software engineering final project.

The original project implemented the complete game rules, CLI, JavaFX GUI, RMI communication, Socket communication, login/reconnection flow, and dynamic window-card loading.

This repository preserves the original academic project while progressively modernizing the build, architecture, tests, and documentation.

## Project Status

| Area | Status |
| --- | --- |
| Original game implementation | Preserved |
| Maven build | Modernized |
| Test suite | Passing |
| CI | Enabled |
| Architecture cleanup | In progress |
| Runtime/UI modernization | Planned |

## Features

- Complete Sagrada game rules
- CLI client
- JavaFX GUI client
- Socket-based communication
- RMI-based communication
- Login and waiting room flow
- Player reconnection handling
- Dynamic loading of window cards, public cards, and tool cards
- Public objective cards and private color scoring
- Tool card effects

## Architecture

The original code follows a broad MVC structure:

- `model`: game state, dice, players, windows, cards
- `domain.cards.publiccards`: public objective card strategies
- `domain.cards.toolcards`: tool card strategies
- `application.actions`: game actions and use cases
- `controller`: server flow, state machine, timers, networking handlers
- `view`: CLI, GUI, JavaFX controllers, client networking
- `common`: shared RMI contracts and path utilities
- `shared.dto`: client-facing transfer objects

Main patterns used:

- MVC
- State
- Strategy
- Factory
- DTO
- RMI/Socket proxy-style communication

See [Architecture Review](docs/architecture-review.md) for the current structure, architectural issues, and refactor roadmap.

## Requirements

- Java 17+
- Maven 3.9+

## Run Tests

```bash
mvn test
```

## Original Execution

The original project was designed to run from the packaged JAR.

Server:

```bash
java -cp Game.jar repolezanettiperuzzi.controller.MasterGame
```

Client:

```bash
java -cp Game.jar repolezanettiperuzzi.client.GameView
```

## Modernization Roadmap

1. Preserve the original behavior with a green build and CI.
2. Document the current architecture.
3. Clarify package boundaries.
4. Separate domain logic from application flow.
5. Isolate networking from controller state.
6. Improve DTOs, validation, and error handling.
7. Modernize runtime and UI setup.

## Original Authors

- Alessandro Peruzzi
- Giampiero Repole
- Andrea Zanetti

Bachelor Software Engineering final project, 2018.
