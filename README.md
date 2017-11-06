
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
SPRING_CONFIG_LOCATION=./config/ SPRING_PROFILES_ACTIVE=myprofile gradle bootRun
```



To Run a mock version of this api, see [mock-app-container](mockAppContainer/README.md)
