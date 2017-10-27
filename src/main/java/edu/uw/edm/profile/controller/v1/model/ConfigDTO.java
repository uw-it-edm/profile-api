package edu.uw.edm.profile.controller.v1.model;

import org.springframework.hateoas.ResourceSupport;

import lombok.Data;

/**
 * @author Maxime Deravet Date: 10/20/17
 */
@Data
public class ConfigDTO extends ResourceSupport {

    public ConfigDTO() {
    }

    private String name;
}
