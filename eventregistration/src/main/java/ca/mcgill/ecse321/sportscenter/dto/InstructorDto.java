package ca.mcgill.ecse321.sportscenter.dto;

public class InstructorDto {
    private AccountDto account;

    public InstructorDto(AccountDto account) {
        this.account = account;
    }

    public AccountDto getAccount() {
        return this.account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }



}
