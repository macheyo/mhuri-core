package zw.co.macheyo.mhuricore.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class MethodNotAllowedException extends RuntimeException {
    private final String resourceName;
    private final String operation;
    private final Object status;

    public MethodNotAllowedException(String resourceName, String operation, Object status) {
        super(String.format("You cannot %s %s that is in the '%s' state", operation, resourceName, status));
        this.resourceName = resourceName;
        this.operation = operation;
        this.status = status;
    }

}
