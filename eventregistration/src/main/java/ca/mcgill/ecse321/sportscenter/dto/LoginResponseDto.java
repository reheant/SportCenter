package ca.mcgill.ecse321.sportscenter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import ca.mcgill.ecse321.sportscenter.dto.utilities.UserType;

public class LoginResponseDto {
    @JsonProperty("id")
    private int id;

    @JsonProperty("role")
    private UserType role;

    public LoginResponseDto() {
    }

    public LoginResponseDto(int id, UserType role) {
        this.id = id;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType r) {
        this.role = r;
    }
}
