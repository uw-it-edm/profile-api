package edu.uw.edm.profile.repository.impl;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.uw.edm.profile.model.ConfigPermission;
import edu.uw.edm.profile.model.ConfigPermissions;
import edu.uw.edm.profile.properties.ProfileProperties;
import edu.uw.edm.profile.repository.ConfigPermissionsRepository;
import edu.uw.edm.profile.security.User;
import edu.uw.edm.profile.service.ObjectDownloader;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Maxime Deravet Date: 10/20/17
 */
@Slf4j
@Service
public class ConfigPermissionsRepositoryImpl implements ConfigPermissionsRepository {


    private ObjectDownloader objectDownloader;
    private ProfileProperties profileProperties;

    public ConfigPermissionsRepositoryImpl(ObjectDownloader objectDownloader, ProfileProperties profileProperties) {
        this.objectDownloader = objectDownloader;
        this.profileProperties = profileProperties;
    }

    @Override
    public List<String> getConfigsForAppAndUser(String appName, User user) {
        try {
            ConfigPermissions configPermissions = downloadPermissionsForApp(appName);
            log.debug(configPermissions.toString());


            return configPermissions.getPermissions().entrySet()
                    .stream()
                    .filter(entry -> isUserAllowed(entry.getValue(), user))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());


        } catch (IOException e) {
            log.error("Cannot download permissions", e);
            return Collections.emptyList();
        }

    }

    private boolean isUserAllowed(ConfigPermission configPermission, User user) {
        for (String group : configPermission.getAuthorizedUWGroups()) {
            if (user.getGroupsMembership().contains(group)) {
                return true;
            }
        }
        return false;
    }


    private ConfigPermissions downloadPermissionsForApp(String appName) throws IOException {

        String permissionFileLocation = getPermissionFileLocation(appName);

        return objectDownloader.getObjectForURL(permissionFileLocation, ConfigPermissions.class);
    }

    private String getPermissionFileLocation(String appName) {
        return profileProperties.getApp().getConfigRootFolderResource() + profileProperties.getEnvironment() + "/" + appName + "." + profileProperties.getApp().getPermissionsFileName();
    }
}
