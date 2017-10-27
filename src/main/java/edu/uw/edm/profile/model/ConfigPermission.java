package edu.uw.edm.profile.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Maxime Deravet Date: 10/20/17
 */
@Getter
@Setter
@ToString
public class ConfigPermission {
    List<String> authorizedUWGroups = new ArrayList<>();
}
