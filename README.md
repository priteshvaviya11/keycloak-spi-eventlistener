# Keycloak SPI Event Listener

This repository contains a custom Keycloak SPI (Service Provider Interface) implementation for an event listener that sends events to an external HTTP endpoint.

## Features

- Listens to Keycloak events (e.g., login, register, update, etc.)
- Sends event payloads to a configured HTTP server endpoint

---

## Getting Started

### Clone the Repository

```bash
git clone -b dev https://github.com/priteshvaviya11/keycloak-spi-eventlistener.git
cd keycloak-spi-eventlistener
```

## Build the Project
Ensure you have Maven installed, then run:

`mvn clean package`

## Add JARs to Keycloak
Copy the generated JARs into your Keycloak runtime providers/ directory

## Required Environment Variables
Add the following environment variables when starting your Keycloak container:

```yaml
KC_SPI_EVENTS_LISTENER_HTTP: http
KC_SPI_EVENTS_LISTENER_HTTP_ENABLED: true
KC_SPI_EVENTS_LISTENER_HTTP_SERVER_URI: https://your-api-endpoint/events
```

# Reference
https://medium.com/@jawadrashid/implementing-keycloak-event-listener-spi-service-provider-interfaces-1f01ae819e8d
