package edu.uw.edm.profile.repository;

import java.util.List;

import edu.uw.edm.profile.exceptions.NotFoundException;
import edu.uw.edm.profile.security.User;

/**
 * @author Maxime Deravet Date: 10/20/17
 */
public interface ConfigPermissionsRepository {

    List<String> getConfigsForAppAndUser(String appName, User user) throws NotFoundException;
}
