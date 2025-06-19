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
public class ApiError implements Serializable {

    @Serial
    private static final long serialVersionUID = -1257222738380732558L;

    private String code;
    private String message;
}
