# Sandbox Server

[![Build Status](https://img.shields.io/github/workflow/status/knightlia/sandbox-server/Tests/master?label=Tests&logo=github)](https://github.com/Knightlia/Sandbox-Server/actions/workflows/tests.yml?query=branch%3Amaster+workflow%3ATests)
[![Build Status](https://img.shields.io/github/workflow/status/knightlia/sandbox-server/Deploy/master?label=Deploy&logo=github)](https://github.com/Knightlia/Sandbox-Server/actions/workflows/deploy.yml?query=branch%3Amaster+workflow%3ADeploy)

This is the API server that handles the Sandbox mode of the Particle Chat application. This includes the REST API and the WebSocket API.

## Setup Environment
Uses Maven 3+ and Java 15+.

## Quick Start
To run the server locally in a terminal or command prompt, call the **start** script.
e.g on a unix system:
```shell
./start.sh
```

> **NOTE**: You can also run the application directly from the IDE. You will need to set the active Spring profile to dev.

## Tests
To run the tests:
```shell
mvn -e clean test
```
