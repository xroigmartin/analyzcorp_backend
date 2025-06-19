package xroigmartin.analyzcorp.shared.infrastructure.in.utils;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import xroigmartin.analyzcorp.shared.infrastructure.in.dto.ApiResponse;

@UtilityClass
public class ResponseEntityUtils<T> {

    public static <T> ResponseEntity<ApiResponse<T>> generateResponseEntity(ApiResponse<T> apiResponse, HttpStatus status){
        return ResponseEntity
                .status(status)
                .body(apiResponse);
    }
}
