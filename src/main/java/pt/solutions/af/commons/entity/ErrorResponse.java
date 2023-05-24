package pt.solutions.af.commons.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ErrorResponse {

    private String code;
    private String message;
    private List<ValidationError> details;


    @Builder
    public ErrorResponse(String code, String message, List<ValidationError> details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }
}
