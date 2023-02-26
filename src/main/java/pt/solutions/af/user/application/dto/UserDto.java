package pt.solutions.af.user.application.dto;


import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@ApiModel(description = "Basic information of the user")
public class UserDto {

    private String name;
    private String email;
    private String phoneNumber;
    private boolean provider;

}
