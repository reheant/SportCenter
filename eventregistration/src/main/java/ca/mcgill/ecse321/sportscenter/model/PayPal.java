package ca.mcgill.ecse321.sportscenter.model;

public class PayPal extends PaymentMethod {

  private String email;
  private String password;

  public PayPal() {}

  public PayPal(String aName, Customer aCustomer, String aEmail, String aPassword) {
    super(aName, aCustomer);
    email = aEmail;
    password = aPassword;
  }

  public boolean setEmail(String aEmail) {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword) {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public void delete() {
    super.delete();
  }

}
