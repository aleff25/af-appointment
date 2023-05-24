package pt.solutions.af.configurations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pt.solutions.af.commons.entity.ErrorResponse;
import pt.solutions.af.commons.entity.ValidationError;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.CONFLICT.name())
                .message(ex.getMessage())
                .build();

        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        List<ValidationError> errors = new ArrayList<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            String code = fieldError.getCode();
            String message = fieldError.getDefaultMessage();
            String detailedMessage = fieldError.toString();
            ValidationError error = new ValidationError(code, message, detailedMessage);
            errors.add(error);
        }

        for (ObjectError objectError : ex.getBindingResult().getGlobalErrors()) {
            String code = objectError.getCode();
            String message = objectError.getDefaultMessage();
            String detailedMessage = objectError.toString();
            ValidationError error = new ValidationError(code, message, detailedMessage);
            errors.add(error);
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.name())
                .message("Validation failed")
                .details(errors)
                .build();

        log.error("Error: {}", errorResponse);
        return ResponseEntity.badRequest().body(errorResponse);

    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        log.error(ex.getMessage());
        return new ResponseEntity<Object>(
                "Access denied.", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleOthersExceptions(
            RuntimeException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .message(ex.getMessage())
                .build();

        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
}
