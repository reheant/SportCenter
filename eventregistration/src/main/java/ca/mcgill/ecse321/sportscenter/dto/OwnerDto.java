package ca.mcgill.ecse321.sportscenter.dto;

public class OwnerDto {
    private AccountDto account;

    public OwnerDto(AccountDto account){
        this.account = account;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }

}

