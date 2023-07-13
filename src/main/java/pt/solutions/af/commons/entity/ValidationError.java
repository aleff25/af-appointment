package pt.solutions.af.commons.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationError {

    private String code;
    private String message;
    private String detailedMessage;

    public ValidationError(String detailedMessage) {
        this.detailedMessage = detailedMessage;
    }
}
