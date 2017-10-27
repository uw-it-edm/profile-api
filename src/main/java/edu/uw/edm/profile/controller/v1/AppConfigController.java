package edu.uw.edm.profile.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uw.edm.profile.controller.v1.model.ConfigDTO;
import edu.uw.edm.profile.controller.v1.model.ConfigsDTO;
import edu.uw.edm.profile.exceptions.ForbiddenException;
import edu.uw.edm.profile.exceptions.NotFoundException;
import edu.uw.edm.profile.repository.ConfigPermissionsRepository;
import edu.uw.edm.profile.repository.ConfigRepository;
import edu.uw.edm.profile.security.User;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Maxime Deravet Date: 10/13/17
 */
@RestController
@RequestMapping("/v1/app")
public class AppConfigController {

    private ConfigPermissionsRepository configPermissionsRepository;
    private ConfigRepository configRepository;

    @Autowired
    public AppConfigController(ConfigPermissionsRepository configPermissionsRepository, ConfigRepository configRepository) {
        this.configPermissionsRepository = configPermissionsRepository;
        this.configRepository = configRepository;
    }

    @RequestMapping("/{appName}")
    public ConfigsDTO list(@PathVariable("appName") String appName, @AuthenticationPrincipal User user) throws ForbiddenException, NotFoundException {
        ConfigsDTO configsDTO = new ConfigsDTO();

        for (String config : configPermissionsRepository.getConfigsForAppAndUser(appName, user)) {
            configsDTO.add(linkTo(methodOn(AppConfigController.class).getConfig(appName, config, user)).withRel(config));
        }

        return configsDTO;
    }


    @RequestMapping(value = "/{appName}/{configName}", method = RequestMethod.GET)
    public ConfigDTO getConfig(@PathVariable("appName") String appName, @PathVariable("configName") String configName, @AuthenticationPrincipal User user) throws ForbiddenException, NotFoundException {
        ConfigDTO configDTO =
                configRepository.getConfigForAppNameConfigNameAndUser(appName, configName, user);

        configDTO.add(linkTo(methodOn(AppConfigController.class).getConfig(appName, configName, user)).withSelfRel());
        return configDTO;
    }
}
