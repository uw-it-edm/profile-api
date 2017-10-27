package edu.uw.edm.profile.repository;

import edu.uw.edm.profile.controller.v1.model.ConfigDTO;
import edu.uw.edm.profile.exceptions.ForbiddenException;
import edu.uw.edm.profile.exceptions.NotFoundException;
import edu.uw.edm.profile.security.User;

/**
 * @author Maxime Deravet Date: 10/20/17
 */
public interface ConfigRepository {

    ConfigDTO getConfigForAppNameConfigNameAndUser(String appName, String configName, User user) throws ForbiddenException, NotFoundException;
}
