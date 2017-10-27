package edu.uw.edm.profile.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Maxime Deravet Date: 10/20/17
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends Throwable {
}
