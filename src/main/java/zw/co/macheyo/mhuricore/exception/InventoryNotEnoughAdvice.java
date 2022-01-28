package zw.co.macheyo.mhuricore.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import zw.co.macheyo.mhuricore.common.ApiResponse;

@ControllerAdvice
public class InventoryNotEnoughAdvice {
    @ResponseBody
    @ExceptionHandler(InventoryNotEnoughException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiResponse<?> InventoryNotEnoughHandler(InventoryNotEnoughException ex) {
        ApiResponse<?> response = new ApiResponse();
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return response;
    }
}
