package zw.co.macheyo.mhuricore.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(HttpStatus.CONFLICT)
public class SQLException extends RuntimeException {
    private final String message;

    public SQLException(String message) {
        super(String.format("SQL Entry failed with error message : '%s'", message));
        this.message = message;
    }

}
