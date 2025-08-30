package xroigmartin.analyzcorp.finance.account.infrastructure.in.utils;

import java.time.Instant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xroigmartin.analyzcorp.finance.account.domain.exception.AccountNameAlreadyExistsException;
import xroigmartin.analyzcorp.finance.account.domain.exception.AccountNotFoundByIdException;
import xroigmartin.analyzcorp.shared.infrastructure.in.dto.AnalyzCorpApiError;
import xroigmartin.analyzcorp.shared.infrastructure.in.dto.AnalyzCorpApiResponse;

@RestControllerAdvice
@Slf4j
public class AccountControllerAdvice  {

    @ExceptionHandler(AccountNotFoundByIdException.class)
    public ResponseEntity<AnalyzCorpApiResponse<Void>> handleNotFound(AccountNotFoundByIdException ex) {
        log.warn(ex.getMessage(), ex);

        var error = AnalyzCorpApiError.builder()
                .code("ACCOUNT_NOT_FOUND")
                .message(ex.getMessage())
                .build();

        var apiResponse = AnalyzCorpApiResponse.<Void>builder()
                .data(null)
                .error(error)
                .timestamp(Instant.now().toString())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(apiResponse);
    }

    @ExceptionHandler(AccountNameAlreadyExistsException.class)
    public ResponseEntity<AnalyzCorpApiResponse<Void>> handleAccountNameAlreadyExistsException(AccountNameAlreadyExistsException ex) {
        log.warn(ex.getMessage(), ex);

        var error = AnalyzCorpApiError.builder()
                .code("ACCOUNT_NAME_ALREADY_EXISTS")
                .message(ex.getMessage())
                .build();

        var apiResponse = AnalyzCorpApiResponse.<Void>builder()
                .data(null)
                .error(error)
                .timestamp(Instant.now().toString())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(apiResponse);
    }

}
