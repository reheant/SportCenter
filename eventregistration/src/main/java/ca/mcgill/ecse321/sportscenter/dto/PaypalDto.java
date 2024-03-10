package ca.mcgill.ecse321.sportscenter.dto;

public class PaypalDto {

    private String accountName;
    private String customerEmail;
    private String paypalEmail;
    private String paypalPassword;

    public PaypalDto(String accountName, String customerEmail, String paypalEmail, String paypalPassword){
        this.accountName = accountName;
        this.customerEmail = customerEmail;
        this.paypalEmail = paypalEmail;
        this.paypalPassword = paypalPassword;
    }

    public void setAccountName(String accountName){
        this.accountName = accountName;
    }

    public String getAccountName(){
        return accountName;
    }


    public void setCustomerEmail(String customerEmail){
        this.customerEmail = customerEmail;
    }

    public String getcustomerEmail(){
        return customerEmail;
    }

    public void setpaypalEmail(String paypalEmail){
        this.paypalEmail = paypalEmail;
    }

    public String getpaypalEmail(){
        return paypalEmail;
    }

    public void setpaypalPassword(String paypalPassword){
        this.paypalPassword = paypalPassword;
    }

    public String getpaypalPassword(){
        return paypalPassword;
    }
}

