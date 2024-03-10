package ca.mcgill.ecse321.sportscenter.dto;

import ca.mcgill.ecse321.sportscenter.dto.utilities.UserType;

public class LoginDto {
    public String email;
    public String password;
    public UserType userType;

    public LoginDto(String email, String password, UserType userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
    }
}
