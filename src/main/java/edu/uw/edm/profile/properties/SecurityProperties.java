package edu.uw.edm.profile.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;


@Component
@ConfigurationProperties(prefix = "profile.security")
@Data
public class SecurityProperties {


    private String keystoreLocation;
    private String keystorePassword;
    private String authenticationHeaderName;

}
