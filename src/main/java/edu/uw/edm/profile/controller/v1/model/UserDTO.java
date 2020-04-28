package edu.uw.edm.profile.controller.v1.model;

import org.springframework.hateoas.RepresentationModel;

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
public class UserDTO extends RepresentationModel {

    private String userName;

    @Singular
    private List<String> userGroups;

}
