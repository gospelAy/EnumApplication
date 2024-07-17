package Enum.Application.Enum.App.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<String> handleInvalidFormatException(InvalidFormatException ex) {
        ex.printStackTrace();
        return ResponseEntity.badRequest().body("Invalid date format. Please use 'dd MMM yyyy'. Detailed error: " + ex.getMessage());
    }

}
