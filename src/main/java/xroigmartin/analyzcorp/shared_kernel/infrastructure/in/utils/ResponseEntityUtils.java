package xroigmartin.analyzcorp.shared_kernel.infrastructure.in.utils;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import xroigmartin.analyzcorp.shared_kernel.infrastructure.in.dto.AnalyzCorpApiResponse;

@UtilityClass
public class ResponseEntityUtils<T> {

    public static <T> ResponseEntity<AnalyzCorpApiResponse<T>> generateResponseEntity(AnalyzCorpApiResponse<T> analyzCorpApiResponse, HttpStatus status){
        return ResponseEntity
                .status(status)
                .body(analyzCorpApiResponse);
    }
}
