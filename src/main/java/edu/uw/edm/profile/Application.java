package edu.uw.edm.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.hateoas.config.EnableHypermediaSupport;

/**
 * @author Maxime Deravet Date: 10/13/17
 */
@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class Application {


    public static void main(String[] args) {


        SpringApplication app = new SpringApplication(Application.class);
        app.addListeners(new ApplicationPidFileWriter());

        app.run(args);

    }
}
