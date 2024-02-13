package ca.mcgill.ecse321.eventregistration.model;

public abstract class PaymentMethod {

  private int id;
  private String name;

  private Customer customer;

  public PaymentMethod(int aId, String aName, Customer aCustomer) {
    id = aId;
    name = aName;
    if (!setCustomer(aCustomer)) {
      throw new RuntimeException(
          "Unable to create PaymentMethod due to aCustomer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public boolean setId(int aId) {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName) {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Customer getCustomer() {
    return customer;
  }

  public boolean setCustomer(Customer aNewCustomer) {
    boolean wasSet = false;
    if (aNewCustomer != null) {
      customer = aNewCustomer;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete() {
    customer = null;
  }

}
