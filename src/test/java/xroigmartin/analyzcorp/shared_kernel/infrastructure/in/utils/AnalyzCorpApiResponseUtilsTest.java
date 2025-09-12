package xroigmartin.analyzcorp.shared_kernel.infrastructure.in.utils;

import org.junit.jupiter.api.Test;
import shared.domain.BaseTest;
import xroigmartin.analyzcorp.shared_kernel.infrastructure.in.dto.AnalyzCorpApiError;
import xroigmartin.analyzcorp.shared_kernel.infrastructure.in.dto.AnalyzCorpApiResponse;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class AnalyzCorpApiResponseUtilsTest extends BaseTest {

    @Test
    void generateApiResponse_withDataAndNoError_shouldPopulateDataAndTimestamp() {
        // Given
        String payload = "payload-data";

        // When
        AnalyzCorpApiResponse<String> result = ApiResponseUtils.generateApiResponse(payload, null);

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
        AnalyzCorpApiError error = new AnalyzCorpApiError("ERR_CODE", "Something went wrong");

        // When
        AnalyzCorpApiResponse<Void> result = ApiResponseUtils.generateApiResponse(null, error);

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
        AnalyzCorpApiError error = new AnalyzCorpApiError("ERR", "Msg");

        // When
        AnalyzCorpApiResponse<String> first = ApiResponseUtils.generateApiResponse("x", error);
        Thread.sleep(1);
        AnalyzCorpApiResponse<String> second = ApiResponseUtils.generateApiResponse("x", error);

        // Then
        assertThat(first.getTimestamp()).isNotEqualTo(second.getTimestamp());
    }
}
