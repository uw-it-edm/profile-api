package edu.uw.edm.profile.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Maxime Deravet Date: 10/20/17
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {

    public NotFoundException(String s) {
        super(s);
    }
}
