package pt.solutions.af.user.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserView {

    private String name;
    private String email;
    private String phoneNumber;
    private boolean provider;

}