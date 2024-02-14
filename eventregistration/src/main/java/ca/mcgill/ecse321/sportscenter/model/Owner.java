package ca.mcgill.ecse321.sportscenter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;

@Entity
public class Owner extends AccountRole {

  @OneToOne()
  @JoinColumn(name = "account_id")
  private Account account;

  public Owner(int aId, Account aAccount) {
    super(aId);
    if (!setAccount(aAccount)) {
      throw new RuntimeException(
          "Unable to create Owner due to aAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
