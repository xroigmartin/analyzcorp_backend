package xroigmartin.analyzcorp.shared.infrastructure.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ApiResponse<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -502208910028423569L;

    private T data;
    private ApiError error;
    private String timestamp;
}
