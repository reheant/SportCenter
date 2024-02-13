package ca.mcgill.ecse321.eventregistration.model;

public abstract class AccountRole {

  private int id;

  public AccountRole(int aId) {
    id = aId;
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

  public void delete() {}

}
