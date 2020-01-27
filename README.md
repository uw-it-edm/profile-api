# Profile API

Develop: [![Build Status](https://travis-ci.org/uw-it-edm/profile-api.svg?branch=develop)](https://travis-ci.org/uw-it-edm/profile-api) [![Coverage Status](https://coveralls.io/repos/github/uw-it-edm/profile-api/badge.svg?branch=feature%2FaddCoveralls)](https://coveralls.io/github/uw-it-edm/profile-api?branch=develop)

## Overview
University of Washington application that is used to retrieve user groups for users as well as configuration files for the front-end [content-services-ui](https://github.com/uw-it-edm/content-services-ui) application .

It exposes three primary REST API's:
- `/v1/user`. Used to request the group membership of a particular user (sent via an http header).
- `/v1/app`. Used to request what EDM application's a particular user has accesss to.
- `/v1/app/{profile-name}`. Used to request UI configuration file for an application (including what strings to show, buttons, pages, search fields, etc).

## How it works

It uses a client cerificate to be able to communicate with UW's group membership service to retrieve user groups for a particular user. This is a trusted relationship and the profile-api server needs to be configured with a .jks file where the certificate is found.

It uses a .json file to map which 'applications' can be accessed by which UW groups. This file can be stored either on the local server or on a AWS S3 bucket.

It uses a .json file for each 'application' that has UI configuration settings for the front end UI ([content-services-ui application](https://github.com/uw-it-edm/content-services-ui)).

# Setup for Development

## Build the project
```
./gradlew build -x test
```

## Run unit tests
```
./gradlew test
```

## Run server with local resources

- Create a `.properties` files anywhere in your file system or under the `./config` folder. For example:
```
/config/application-local.properties
```
- Add the following settings:

```
uw.profile.app.configRootFolderResource = file:///<your-config-folder>/
uw.profile.app.permissionsFileName = permissions.json
uw.profile.environment = dev

uw.profile.security.keystore-location = <path/to/keystore/location.jks>
uw.profile.security.keystore-password = <keystore-pwd>
uw.profile.security.authentication-header-name = auth-header

cloud.aws.credentials.instanceProfile = false
cloud.aws.region.auto = false
cloud.aws.region.static = us-west-2
cloud.aws.stack.auto = false
```
Note: if you ran the [workstation-setup script](https://github.com/uw-it-edm/workstation-setup/blob/master/configuration/edm-team), then under your `~/edm-data` folder, you can find the `content-services-ui-config` that you can use for the `configRootFolderResource` setting. Similarly, you can locate the .jks file that contains the certificate to communicate with UW's servers. The team's shared LastPass should have the password.

- Run the project specifying the location of your local `.properties` file. For example:
```
SPRING_CONFIG_LOCATION=./config/application-local.properties ./gradlew bootRun
```

## Send Request to Server
Once you have the server running locally, you can issue requests to http://localhost:8080, passing a request header with the name you specified for the `uw.profile.security.authentication-header-name` property set to your netId.

### Request the UW groups for a user:
```
curl -H "auth-header: <netId>" http://localhost:8080/v1/user | json_pp
```

### Request the EDM applications for a user:
```
curl -H "auth-header: <netId>" http://localhost:8080/v1/app/content-services-ui | json_pp
```

### Request the UUI profile for the 'demo' application
```
curl -H "auth-header: <netId>" http://localhost:8080/v1/app/content-services-ui/demo | json_pp
```

# Setup as a Mocked Resource

To Run a mock version of this api, see [mock-app-container](mockAppContainer/README.md)
