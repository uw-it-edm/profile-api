package edu.uw.edm.profile.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author Maxime Deravet Date: 10/23/17
 */
@Component
@ConfigurationProperties(prefix = "profile")
@Validated
@Data
public class ProfileProperties {

    @NotNull
    private String environment;

    private App app = new App();

    @Data
    public class App {
        @NotNull
        /**
         *  Need to be readable by org.springframework.core.io.ResourceLoader.getResource()
         */
        private String configRootFolderResource;

        @NotNull
        private String permissionsFileName;
    }


}