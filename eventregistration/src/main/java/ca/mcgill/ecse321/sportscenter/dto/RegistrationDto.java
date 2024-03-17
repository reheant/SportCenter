package ca.mcgill.ecse321.sportscenter.dto;

public class RegistrationDto {
    private String customerAccountEmail;
    private Integer sessionId;    

    public RegistrationDto() {
    }

    public RegistrationDto(String customerAccountEmail, Integer sessionId) {
        this.customerAccountEmail = customerAccountEmail;
        this.sessionId = sessionId;
    }

    public String getCustomerAccountEmail() {
        return customerAccountEmail;
    }

    public void setCustomer(String customerAccountEmail) {
        this.customerAccountEmail = customerAccountEmail;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSession(Integer sessionId) {
        this.sessionId = sessionId;
    }
}
