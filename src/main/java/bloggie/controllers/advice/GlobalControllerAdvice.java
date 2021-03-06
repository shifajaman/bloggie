package bloggie.controllers.advice;

import bloggie.contracts.response.UserCreatedResponse;
import bloggie.errors.BloggieError;
import bloggie.errors.ErrorCodes;
import bloggie.errors.InternalServerException;
import bloggie.errors.InvalidDataException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<UserCreatedResponse> validation(MethodArgumentNotValidException exception) {
        var msg = exception.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .filter(x -> !x.isBlank()).collect(Collectors.joining(", "));
        var invalidUserErr = new BloggieError(msg, ErrorCodes.INVALID_USER_NAME.name());
        var res = new UserCreatedResponse(null, Collections.singletonList(invalidUserErr));
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidDataException.class)
    public ResponseEntity<UserCreatedResponse> validation(InvalidDataException exception) {
        var msg = exception.getMessage();
        var invalidUserErr = new BloggieError(msg, ErrorCodes.INVALID_USER_NAME.name());
        var res = new UserCreatedResponse(null, Collections.singletonList(invalidUserErr));
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InternalServerException.class)
    public ResponseEntity<UserCreatedResponse> validation(InternalServerException exception) {
        var msg = exception.getMessage();
        var invalidUserErr = new BloggieError(msg, ErrorCodes.INTERNAL_SERVER_ERROR.name());
        var res = new UserCreatedResponse(null, Collections.singletonList(invalidUserErr));
        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}

