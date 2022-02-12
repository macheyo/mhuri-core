package zw.co.macheyo.mhuricore.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T body;

    public ApiResponse(boolean success,String message){
        this.success=success;
        this.message=message;
    }

}
