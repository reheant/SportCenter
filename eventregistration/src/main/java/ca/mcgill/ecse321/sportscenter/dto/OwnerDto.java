package ca.mcgill.ecse321.sportscenter.dto;

public class OwnerDto {
    private String accountEmail;

    public OwnerDto(String accountEmail){
        this.accountEmail = accountEmail;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

}

