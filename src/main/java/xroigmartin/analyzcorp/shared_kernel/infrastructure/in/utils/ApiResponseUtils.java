package xroigmartin.analyzcorp.shared_kernel.infrastructure.in.utils;

import java.time.Instant;

import lombok.experimental.UtilityClass;
import xroigmartin.analyzcorp.shared_kernel.infrastructure.in.dto.AnalyzCorpApiError;
import xroigmartin.analyzcorp.shared_kernel.infrastructure.in.dto.AnalyzCorpApiResponse;

@UtilityClass
public class ApiResponseUtils<T> {

    public static <T> AnalyzCorpApiResponse<T> generateApiResponse(T data, AnalyzCorpApiError analyzCorpApiError) {
        return AnalyzCorpApiResponse.<T>builder()
                .error(analyzCorpApiError)
                .data(data)
                .timestamp(Instant.now().toString())
                .build();
    }

}
