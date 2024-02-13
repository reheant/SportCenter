package ca.mcgill.ecse321.sportscenter.model;

public class Customer extends AccountRole {

  private boolean wantsEmailConfirmation;

  private Account account;

  public Customer(int aId, boolean aWantsEmailConfirmation, Account aAccount) {
    super(aId);
    wantsEmailConfirmation = aWantsEmailConfirmation;
    if (!setAccount(aAccount)) {
      throw new RuntimeException(
          "Unable to create Customer due to aAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public boolean setWantsEmailConfirmation(boolean aWantsEmailConfirmation) {
    boolean wasSet = false;
    wantsEmailConfirmation = aWantsEmailConfirmation;
    wasSet = true;
    return wasSet;
  }

  public boolean getWantsEmailConfirmation() {
    return wantsEmailConfirmation;
  }

  public boolean isWantsEmailConfirmation() {
    return wantsEmailConfirmation;
  }

  public Account getAccount() {
    return account;
  }

  public boolean setAccount(Account aNewAccount) {
    boolean wasSet = false;
    if (aNewAccount != null) {
      account = aNewAccount;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete() {
    account = null;
    super.delete();
  }

}
