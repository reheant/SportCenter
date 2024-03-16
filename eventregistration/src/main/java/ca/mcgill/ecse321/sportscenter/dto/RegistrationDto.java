package ca.mcgill.ecse321.sportscenter.dto;

public class RegistrationDto {
    private CustomerDto customer;
    private SessionDto session;    

    public RegistrationDto() {
    }

    public RegistrationDto(CustomerDto customer, SessionDto session) {
        this.customer = customer;
        this.session = session;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public SessionDto getSession() {
        return session;
    }

    public void setSession(SessionDto session) {
        this.session = session;
    }
}
