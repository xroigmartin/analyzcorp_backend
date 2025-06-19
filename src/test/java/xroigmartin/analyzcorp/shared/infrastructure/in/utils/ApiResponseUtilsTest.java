package xroigmartin.analyzcorp.shared.infrastructure.in.utils;

import org.junit.jupiter.api.Test;
import shared.domain.BaseTest;
import xroigmartin.analyzcorp.shared.infrastructure.in.dto.ApiError;
import xroigmartin.analyzcorp.shared.infrastructure.in.dto.ApiResponse;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class ApiResponseUtilsTest extends BaseTest {

    @Test
    void generateApiResponse_withDataAndNoError_shouldPopulateDataAndTimestamp() {
        // Given
        String payload = "payload-data";

        // When
        ApiResponse<String> result = ApiResponseUtils.generateApiResponse(payload, null);

        // Then
        assertThat(result.getData()).isEqualTo(payload);
        assertThat(result.getError()).isNull();
        String timestamp = result.getTimestamp();
        assertThat(timestamp).isNotNull();
        assertThat(timestamp).matches(
                Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+Z?"));
    }

    @Test
    void generateApiResponse_withErrorAndNoData_shouldPopulateErrorAndTimestamp() {
        // Given
        ApiError error = new ApiError("ERR_CODE", "Something went wrong");

        // When
        ApiResponse<Void> result = ApiResponseUtils.generateApiResponse(null, error);

        // Then
        assertThat(result.getData()).isNull();
        assertThat(result.getError()).isSameAs(error);
        assertThat(result.getError().getCode()).isEqualTo("ERR_CODE");
        assertThat(result.getError().getMessage()).isEqualTo("Something went wrong");
        String timestamp = result.getTimestamp();
        assertThat(timestamp).isNotNull();
        assertThat(timestamp).matches(
                Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+Z?"));
    }

    @Test
    void generateApiResponse_multipleInvocations_shouldCreateDistinctTimestamps() throws InterruptedException {
        // Given
        ApiError error = new ApiError("ERR", "Msg");

        // When
        ApiResponse<String> first = ApiResponseUtils.generateApiResponse("x", error);
        Thread.sleep(1);
        ApiResponse<String> second = ApiResponseUtils.generateApiResponse("x", error);

        // Then
        assertThat(first.getTimestamp()).isNotEqualTo(second.getTimestamp());
    }
}
