package bloggie.advice;

import bloggie.domain.Blog;
import bloggie.error.BloggieError;
import bloggie.error.ErrorCodes;
import bloggie.response.BlogCreatedResponse;
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
    public ResponseEntity<BlogCreatedResponse> validation(MethodArgumentNotValidException exception){
        var msg = exception.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .filter(x->!x.isBlank()).collect(Collectors.joining(", "));

        var invalidBlogErr = new BloggieError(msg, ErrorCodes.INVALID_BLOG_INPUT.name());
        var res =  new BlogCreatedResponse(null, Collections.singletonList(invalidBlogErr));
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
}
