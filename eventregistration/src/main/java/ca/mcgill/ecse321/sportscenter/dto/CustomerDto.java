package ca.mcgill.ecse321.sportscenter.dto;

public class CustomerDto {
    private String accountEmail;
    private Boolean wantsEmailConfirmation ;

    public CustomerDto() {
    }

    public CustomerDto(String accountEmail, Boolean wantsEmailConfirmation){
        this.accountEmail = accountEmail;
        this.wantsEmailConfirmation = wantsEmailConfirmation;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public Boolean getWantsEmailConfirmation(){
        return wantsEmailConfirmation;
    }

    public void setWantsEmailConfirmation(Boolean wantsEmailConfirmation){
        this.wantsEmailConfirmation = wantsEmailConfirmation;
    }


}
