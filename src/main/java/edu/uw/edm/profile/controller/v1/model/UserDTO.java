package edu.uw.edm.profile.controller.v1.model;

import org.springframework.hateoas.ResourceSupport;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;

/**
 * @author Maxime Deravet Date: 7/27/18
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends ResourceSupport {

    private String userName;

    @Singular
    private List<String> userGroups;

}
