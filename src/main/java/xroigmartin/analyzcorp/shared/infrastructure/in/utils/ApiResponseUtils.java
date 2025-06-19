package xroigmartin.analyzcorp.shared.infrastructure.in.utils;

import lombok.experimental.UtilityClass;
import xroigmartin.analyzcorp.shared.infrastructure.in.dto.ApiError;
import xroigmartin.analyzcorp.shared.infrastructure.in.dto.ApiResponse;

import java.time.Instant;

@UtilityClass
public class ApiResponseUtils<T> {

    public static <T> ApiResponse<T> generateApiResponse(T data, ApiError apiError) {
        return ApiResponse.<T>builder()
                .error(apiError)
                .data(data)
                .timestamp(Instant.now().toString())
                .build();
    }

}
