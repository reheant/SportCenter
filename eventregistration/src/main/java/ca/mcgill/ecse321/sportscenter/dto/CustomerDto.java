package ca.mcgill.ecse321.sportscenter.dto;

public class CustomerDto {
    private String accountEmail;
    private Boolean wantsEmailConfirmation;
    private String firstName;
    private String lastName;
    private String password;

    public CustomerDto() {
    }

    public CustomerDto(String accountEmail, Boolean wantsEmailConfirmation) {
        this.accountEmail = accountEmail;
        this.wantsEmailConfirmation = wantsEmailConfirmation;
    }

    public CustomerDto(String accountEmail, Boolean wantsEmailConfirmation, String firstName, String lastName,
            String password) {
        this.accountEmail = accountEmail;
        this.wantsEmailConfirmation = wantsEmailConfirmation;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public Boolean getWantsEmailConfirmation() {
        return wantsEmailConfirmation;
    }

    public void setWantsEmailConfirmation(Boolean wantsEmailConfirmation) {
        this.wantsEmailConfirmation = wantsEmailConfirmation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
