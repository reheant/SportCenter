package ca.mcgill.ecse321.sportscenter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorDto {
    @JsonProperty
    private String error;

    public ErrorDto(Exception e) {
        this.error = e.getMessage();
    }
}
