package ca.mcgill.ecse321.sportscenter.dto;

public class PaypalDto {
    private String name;
    private String email;
    private String password;
    private CustomerDto customer;

    public PaypalDto(String name, String email, String password, CustomerDto customer){
        this.name = name;
        this.email = email;
        this.password = password;
        this.customer = customer;
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

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

}

