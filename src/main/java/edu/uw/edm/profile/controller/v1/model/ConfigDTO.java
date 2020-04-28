package edu.uw.edm.profile.controller.v1.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import org.springframework.hateoas.RepresentationModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Maxime Deravet Date: 10/20/17
 */
public class ConfigDTO extends RepresentationModel {

    public ConfigDTO() {
    }

    private Map<String, Object> properties = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        return properties;
    }

    @JsonAnySetter
    public void set(String name, Object value) {
        properties.put(name, value);
    }
}
