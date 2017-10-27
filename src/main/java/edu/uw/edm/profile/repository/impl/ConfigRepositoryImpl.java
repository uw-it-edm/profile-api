package edu.uw.edm.profile.repository.impl;

import org.springframework.stereotype.Service;

import edu.uw.edm.profile.controller.v1.model.ConfigDTO;
import edu.uw.edm.profile.exceptions.ForbiddenException;
import edu.uw.edm.profile.repository.ConfigPermissionsRepository;
import edu.uw.edm.profile.repository.ConfigRepository;
import edu.uw.edm.profile.security.User;

/**
 * @author Maxime Deravet Date: 10/20/17
 */
@Service
public class ConfigRepositoryImpl implements ConfigRepository {
    private ConfigPermissionsRepository configPermissionsRepository;

    public ConfigRepositoryImpl(ConfigPermissionsRepository configPermissionsRepository) {
        this.configPermissionsRepository = configPermissionsRepository;
    }

    @Override
    public ConfigDTO getConfigForAppNameConfigNameAndUser(String appName, String configName, User user) throws ForbiddenException {

        if (!configPermissionsRepository.getConfigsForAppAndUser(appName, user).contains(configName)) {
            throw new ForbiddenException();
        }
        //TODO CAB-2609
        return null;
    }
}
