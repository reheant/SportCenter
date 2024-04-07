package ca.mcgill.ecse321.sportscenter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass()
public abstract class AccountRole {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int id;

  public AccountRole() {
  }

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

  public void delete() {
  }

}
