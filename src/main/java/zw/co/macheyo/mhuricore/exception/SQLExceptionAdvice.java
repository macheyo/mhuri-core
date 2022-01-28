package zw.co.macheyo.mhuricore.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import zw.co.macheyo.mhuricore.common.ApiResponse;

@ControllerAdvice
public class SQLExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ApiResponse<?> SQLExceptionHandler(SQLException ex) {
        ApiResponse<?> response = new ApiResponse();
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.CONFLICT.value());
        return response;
    }
}
