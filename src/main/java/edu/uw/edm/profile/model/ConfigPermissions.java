package edu.uw.edm.profile.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Maxime Deravet Date: 10/20/17
 */

@ToString
@EqualsAndHashCode
public class ConfigPermissions {

    private Map<String, ConfigPermission> permissions = new HashMap<>();

    @JsonAnyGetter
    public Map<String, ConfigPermission> getPermissions() {
        return permissions;
    }

    @JsonAnySetter
    public void set(String name, ConfigPermission value) {
        permissions.put(name, value);
    }

}
