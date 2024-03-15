package ca.mcgill.ecse321.sportscenter.dto;

public class CustomerDto {
    private AccountDto account;
    private Boolean wantsEmailConfirmation ;

    public CustomerDto(AccountDto account, Boolean wantsEmailConfirmation){
        this.account = account;
        this.wantsEmailConfirmation = wantsEmailConfirmation;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }

    public Boolean getWantsEmailConfirmation(){
        return wantsEmailConfirmation;
    }

    public void setWantsEmailConfirmation(Boolean wantsEmailConfirmation){
        this.wantsEmailConfirmation = wantsEmailConfirmation;
    }


}
