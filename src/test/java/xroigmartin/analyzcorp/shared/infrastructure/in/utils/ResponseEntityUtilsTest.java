package xroigmartin.analyzcorp.shared.infrastructure.in.utils;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import shared.domain.BaseTest;
import xroigmartin.analyzcorp.shared.infrastructure.in.dto.ApiError;
import xroigmartin.analyzcorp.shared.infrastructure.in.dto.ApiResponse;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseEntityUtilsTest extends BaseTest {

    @Test
    void generateSuccessfulResponse_shouldReturnResponseEntityWithGivenStatusAndBody() {

        String timestamp = Instant.now().toString();
        String data = faker.name().fullName();
        // Given
        ApiResponse<String> apiResponse = new ApiResponse<>(
                data,
                null,
                timestamp
        );
        HttpStatus status = HttpStatus.CREATED;

        // When
        ResponseEntity<ApiResponse<String>> response =
                ResponseEntityUtils.generateResponseEntity(apiResponse, status);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(status);
        assertThat(response.getBody()).isSameAs(apiResponse);
        assertThat(response.getBody().getData()).isEqualTo(data);
        assertThat(response.getBody().getError()).isNull();
        assertThat(response.getBody().getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    void generateSuccessfulResponse_withErrorResponse_shouldIncludeErrorInBody() {
        // Given
        String timestamp = Instant.now().toString();
        ApiError error = new ApiError("ERROR_CODE", "Error occurred");
        ApiResponse<Void> apiResponse = new ApiResponse<>(
                null,
                error,
                timestamp
        );
        HttpStatus status = HttpStatus.BAD_REQUEST;

        // When
        ResponseEntity<ApiResponse<Void>> response =
                ResponseEntityUtils.generateResponseEntity(apiResponse, status);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(status);
        assertThat(response.getBody().getData()).isNull();
        assertThat(response.getBody().getError()).isSameAs(error);
        assertThat(response.getBody().getError().getCode()).isEqualTo("ERROR_CODE");
        assertThat(response.getBody().getError().getMessage()).isEqualTo("Error occurred");
        assertThat(response.getBody().getTimestamp()).isEqualTo(timestamp);
    }

}
