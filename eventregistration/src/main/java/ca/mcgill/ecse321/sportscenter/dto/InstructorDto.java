package ca.mcgill.ecse321.sportscenter.dto;

public class InstructorDto {
    private String accountEmail;
    private String firstName;
    private String lastName;

    public InstructorDto(){
    }

    public InstructorDto(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public InstructorDto(String accountEmail, String firstName, String lastName) {
        this.accountEmail = accountEmail;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
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

