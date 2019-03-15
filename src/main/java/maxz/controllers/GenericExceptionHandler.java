package maxz.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestController
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleSomeException(Exception ex, WebRequest request) {
        Map errorDetails = new HashMap() {
            {
                put(new Date(), "");
                put("ErrorMessage", ex.getMessage());
                put("Description", request.getDescription(true));
        }};
        return new ResponseEntity<Object>(errorDetails, HttpStatus.NOT_FOUND);
    }
}
