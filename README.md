# Profile API
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/ed0a1d83ed7742f982da897d0657f2d9)](https://app.codacy.com/app/uw-it-edm/profile-api?utm_source=github.com&utm_medium=referral&utm_content=uw-it-edm/profile-api&utm_campaign=Badge_Grade_Settings)
Develop: [![Build Status](https://travis-ci.org/uw-it-edm/profile-api.svg?branch=develop)](https://travis-ci.org/uw-it-edm/profile-api) [![Coverage Status](https://coveralls.io/repos/github/uw-it-edm/profile-api/badge.svg?branch=feature%2FaddCoveralls)](https://coveralls.io/github/uw-it-edm/profile-api?branch=develop)

## Instructions

To run locally, you'll probably need a property file with these values set : 

```
cloud.aws.credentials.instanceProfile= false
cloud.aws.region.auto= false
cloud.aws.region.static= eu-west-1

uw.profile.app.configRootFolderResource = {s3://,file://}/location

uw.profile.app.permissionsFileName = {permission-file.json}
uw.profile.environment = {env}

uw.profile.security.keystore-location = /Keystore-location
uw.profile.security.keystore-password= keystore-pwd
uw.profile.security.authentication-header-name = authentication-header

```

run with : 

```
SPRING_CONFIG_ADDITIONAL_LOCATION=./config/ SPRING_PROFILES_ACTIVE=myprofile ./gradlew bootRun
```



To Run a mock version of this api, see [mock-app-container](mockAppContainer/README.md)
