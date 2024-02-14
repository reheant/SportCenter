package ca.mcgill.ecse321.sportscenter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Registration {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int id;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "session_id")
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
