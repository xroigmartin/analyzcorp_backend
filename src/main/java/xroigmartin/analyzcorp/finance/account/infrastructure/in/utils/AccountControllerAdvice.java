package xroigmartin.analyzcorp.finance.account.infrastructure.in.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xroigmartin.analyzcorp.finance.account.domain.exception.AccountNotFoundByIdException;
import xroigmartin.analyzcorp.shared.infrastructure.in.dto.ApiError;
import xroigmartin.analyzcorp.shared.infrastructure.in.dto.ApiResponse;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class AccountControllerAdvice  {

    @ExceptionHandler(AccountNotFoundByIdException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(AccountNotFoundByIdException ex) {
        log.error(ex.getMessage(), ex);

        var error = ApiError.builder()
                .code("ACCOUNT_NOT_FOUND")
                .message(ex.getMessage())
                .build();

        var apiResponse = ApiResponse.<Void>builder()
                .data(null)
                .error(error)
                .timestamp(Instant.now().toString())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(apiResponse);
    }

}
