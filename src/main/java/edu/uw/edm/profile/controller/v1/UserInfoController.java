package edu.uw.edm.profile.controller.v1;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.uw.edm.profile.controller.v1.model.UserDTO;
import edu.uw.edm.profile.security.User;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Maxime Deravet Date: 10/13/17
 */
@RestController
@RequestMapping("/v1/user")
public class UserInfoController {

    @GetMapping(value = "")
    public UserDTO getUserInfo(@AuthenticationPrincipal User user) {


        UserDTO userDTO = UserDTO.builder()
                .userName(user.getUsername())
                .userGroups(user.getGroupsMembership())
                .build();


        userDTO.add(linkTo(methodOn(UserInfoController.class).getUserInfo(user)).withSelfRel());
        return userDTO;

    }
}
