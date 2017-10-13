package edu.uw.edm.profile;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author Maxime Deravet Date: 10/13/17
 */
@SpringBootApplication
public class Application {


    public static void main(String[] args) throws Exception {


        new SpringApplicationBuilder(Application.class).run(args);

    }
}
