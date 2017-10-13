package edu.uw.edm.profile.controller.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Maxime Deravet Date: 10/13/17
 */
@RestController
@RequestMapping("/v1/config")
public class ConfigController {

    @RequestMapping("")
    @ResponseBody
    public String list() {
        return "Hello";
    }
}
