package edu.uw.edm.profile.repository.impl;

import org.springframework.stereotype.Service;

import java.io.IOException;

import edu.uw.edm.profile.controller.v1.model.ConfigDTO;
import edu.uw.edm.profile.exceptions.ForbiddenException;
import edu.uw.edm.profile.exceptions.NotFoundException;
import edu.uw.edm.profile.properties.ProfileProperties;
import edu.uw.edm.profile.repository.ConfigPermissionsRepository;
import edu.uw.edm.profile.repository.ConfigRepository;
import edu.uw.edm.profile.security.User;
import edu.uw.edm.profile.service.ObjectDownloader;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Maxime Deravet Date: 10/20/17
 */
@Service
@Slf4j
public class ConfigRepositoryImpl implements ConfigRepository {
    private final ConfigPermissionsRepository configPermissionsRepository;

    private final ObjectDownloader objectDownloader;
    private final ProfileProperties profileProperties;


    public ConfigRepositoryImpl(ConfigPermissionsRepository configPermissionsRepository, ObjectDownloader objectDownloader, ProfileProperties profileProperties) {
        this.configPermissionsRepository = configPermissionsRepository;
        this.objectDownloader = objectDownloader;
        this.profileProperties = profileProperties;
    }

    @Override
    public ConfigDTO getConfigForAppNameConfigNameAndUser(String appName, String configName, User user) throws ForbiddenException, NotFoundException {

        if (!configPermissionsRepository.getConfigsForAppAndUser(appName, user).contains(configName)) {
            throw new ForbiddenException();
        }

        return downloadConfigFile(appName, configName);
    }

    private ConfigDTO downloadConfigFile(String appName, String configName) throws NotFoundException {

        String fileLocation = getConfigFileLocation(appName, configName);


        try {
            return objectDownloader.getObjectForURL(fileLocation, ConfigDTO.class);
        } catch (IOException e) {
            log.warn("cannot download file for " + appName + " and " + configName, e);
            throw new NotFoundException("No config for " + appName + "/" + configName);
        }
    }

    private String getConfigFileLocation(String appName, String configName) {
        return profileProperties.getApp().getConfigRootFolderResource() + profileProperties.getEnvironment() + "/" + appName + "/" + configName + ".json";
    }
}
