package ca.mcgill.ecse321.sportscenter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponseDto {
    @JsonProperty("id")
    private int id;

    public LoginResponseDto() {
    }

    public LoginResponseDto(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
