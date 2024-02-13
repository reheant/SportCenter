package ca.mcgill.ecse321.eventregistration.model;

public class Registration {

  private int id;

  private Customer customer;
  private Session session;

  public Registration(int aId, Customer aCustomer, Session aSession) {
    id = aId;
    if (!setCustomer(aCustomer)) {
      throw new RuntimeException(
          "Unable to create Registration due to aCustomer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setSession(aSession)) {
      throw new RuntimeException(
          "Unable to create Registration due to aSession. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public boolean setId(int aId) {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public int getId() {
    return id;
  }

  public Customer getCustomer() {
    return customer;
  }

  public Session getSession() {
    return session;
  }

  public boolean setCustomer(Customer aNewCustomer) {
    boolean wasSet = false;
    if (aNewCustomer != null) {
      customer = aNewCustomer;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setSession(Session aNewSession) {
    boolean wasSet = false;
    if (aNewSession != null) {
      session = aNewSession;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete() {
    customer = null;
    session = null;
  }

}
