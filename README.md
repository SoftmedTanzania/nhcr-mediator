# National Health Client Registry Mediator

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a1052124d49b41ce82d9530f68593ada)](https://app.codacy.com/gh/SoftmedTanzania/nhcr-mediator?utm_source=github.com&utm_medium=referral&utm_content=SoftmedTanzania/nhcr-mediator&utm_campaign=Badge_Grade)
[![Java CI Badge](https://github.com/SoftmedTanzania/nhcr-mediator/workflows/Java%20CI%20with%20Maven/badge.svg)](https://github.com/SoftmedTanzania/nhcr-mediator/actions?query=workflow%3A%22Java+CI+with+Maven%22)
[![Coverage Status](https://coveralls.io/repos/github/SoftmedTanzania/nhcr-mediator/badge.svg?branch=development)](https://coveralls.io/github/SoftmedTanzania/nhcr-mediator?branch=development)

An [OpenHIM](http://openhim.org/) mediator for handling system integration with National Health Client Registry.

## 1. Dev Requirements

1. Java 1.8
2. IntelliJ or Visual Studio Code
3. Maven 3.6.3

## 2. Mediator Configuration

This mediator is designed to work with multiple systems that send JSON Payloads while communicating with the NHCR via the HIM and the HIM transforms the 
messages into HL7v2 messages before forwarding the requests to the NHCR

### 3 Configuration Parameters

The configuration parameters specific to the mediator and destination system can be found at

`src/main/resources/mediator.properties`

```
    mediator.name=NHCR-Mediator
    mediator.host=localhost
    mediator.port=3014
    mediator.timeout=60000
    mediator.heartbeats=true
    
    core.scheme=openhim-scheme
    core.host=openhim-address
    core.api.port=8080
    core.api.user=root@openhim.org
    core.api.password=openhim-password
    
    destination.host=destination-system-address
    destination.api.port=destination-system-address-port
    destination.api.path=/destination-system-path
    destination.scheme=destination-system-scheme
```

The configuration parameters specific to the mediator and the mediator's metadata can be found at

`src/main/resources/mediator-registration-info.json`

```
    {
      "urn": "urn:uuid:83c54769-5622-4cec-9f58-ccfc0ad24382",
      "version": "0.1.0",
      "name": "NHCR Mediator",
      "description": "Description",
      "endpoints": [
        {
          "name": "NHCR Mediator Route",
          "host": "localhost",
          "port": "3014",
          "path": "/nhcr",
          "type": "http"
        }
      ],
      "defaultChannelConfig": [
        {
          "name": "NHCR Mediator",
          "urlPattern": "^/nhcr",
          "type": "http",
          "allow": [
            "nhcr-mediator"
          ],
          "routes": [
            {
              "name": "NHCR Mediator Route",
              "host": "localhost",
              "port": "3014",
              "path": "/nhcr",
              "type": "http",
              "primary": "true"
            }
          ]
        }
      ]
    }
```

## 4. Deployment

To build and run the mediator after performing the above configurations, run the following

```
  mvn clean package -DskipTests=true -e source:jar javadoc:jar
  java -jar target/nhcr-mediator-<version>-jar-with-dependencies.jar
```
