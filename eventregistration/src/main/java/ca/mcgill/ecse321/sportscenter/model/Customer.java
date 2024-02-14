package ca.mcgill.ecse321.sportscenter.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Customer extends AccountRole {

  private boolean wantsEmailConfirmation;

  @OneToMany(mappedBy = "customer")
  private List<Registration> registrations;

  public Customer(int aId, boolean aWantsEmailConfirmation, Account aAccount) {
    super(aId);
    wantsEmailConfirmation = aWantsEmailConfirmation;
    if (!setAccount(aAccount)) {
      throw new RuntimeException(
          "Unable to create Customer due to aAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public void addRegistration(Registration r) {
    registrations.add(r);
  }

  public boolean removeRegistiation(Registration r) {
    return registrations.remove(r);
  }

  public List<?> getRegistrations() {
    return registrations;
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

  public void delete() {
    super.delete();
  }

}
