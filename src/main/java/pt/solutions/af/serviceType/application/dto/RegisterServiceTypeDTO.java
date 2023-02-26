package pt.solutions.af.serviceType.application.dto;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@ApiModel(description = "Register a new service type with name and the effort of this service expressed in seconds")
public class RegisterServiceTypeDTO {

    @NotBlank(message = "{RegisterServiceTypeDTO.name.NotBlank}")
    private String name;

    @NotNull(message = "{RegisterServiceTypeDTO.serviceEffort.NotNull}")
    private int serviceEffort;

}