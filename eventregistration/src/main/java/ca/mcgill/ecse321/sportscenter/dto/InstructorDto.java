package ca.mcgill.ecse321.sportscenter.dto;

public class InstructorDto {
    private String accountEmail;

    public InstructorDto(){
    }

    public InstructorDto(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }


}

