# Sandbox Server

![Tests](https://github.com/Knightlia/Sandbox-Server/workflows/Tests/badge.svg)
![Deploy](https://github.com/Knightlia/Sandbox-Server/workflows/Deploy/badge.svg)

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
