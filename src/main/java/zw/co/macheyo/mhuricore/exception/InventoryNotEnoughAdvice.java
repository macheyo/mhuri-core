package zw.co.macheyo.mhuricore.exception;


import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InventoryNotEnoughAdvice {
    @ResponseBody
    @ExceptionHandler(InventoryNotEnoughException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<?> InventoryNotEnoughHandler(InventoryNotEnoughException ex) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail(ex.getMessage()));
    }
}
