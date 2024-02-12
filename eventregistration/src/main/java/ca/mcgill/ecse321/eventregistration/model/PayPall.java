package ca.mcgill.ecse321.sportscenter.model;

// line 67 "../../../../../../model.ump"
// line 182 "../../../../../../model.ump"
public class PayPall extends PaymentMethod
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PayPall Attributes
  private String email;
  private String password;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PayPall(int aId, String aName, Customer aCustomer, String aEmail, String aPassword)
  {
    super(aId, aName, aCustomer);
    email = aEmail;
    password = aPassword;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "]";
  }
}