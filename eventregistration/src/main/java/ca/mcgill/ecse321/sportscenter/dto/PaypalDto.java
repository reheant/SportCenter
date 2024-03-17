package ca.mcgill.ecse321.sportscenter.dto;

public class PaypalDto {
    private String name;
    private String email;
    private String password;
    private String customerAccountEmail;

    public PaypalDto(String name, String email, String password, String customerAccountEmail){
        this.name = name;
        this.email = email;
        this.password = password;
        this.customerAccountEmail = customerAccountEmail;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCustomerAccountEmail() {
        return customerAccountEmail;
    }

    public void setCustomerAccountEmail(String customerAccountEmail) {
        this.customerAccountEmail = customerAccountEmail;
    }

}

