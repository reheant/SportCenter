package ca.mcgill.ecse321.sportscenter.model;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;

  @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
  private List<AccountRole> roles;

  public Account(int aId, String aFirstName, String aLastName, String aEmail, String aPassword) {
    id = aId;
    firstName = aFirstName;
    lastName = aLastName;
    email = aEmail;
    password = aPassword;
  }

  public List<AccountRole> getRoles() {
    return roles;
  }

  public void addRole(AccountRole r) {
    roles.add(r);
  }

  public boolean removeRole(AccountRole r) {
    return roles.remove(r);
  }

  public boolean setId(int aId) {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setFirstName(String aFirstName) {
    boolean wasSet = false;
    firstName = aFirstName;
    wasSet = true;
    return wasSet;
  }

  public boolean setLastName(String aLastName) {
    boolean wasSet = false;
    lastName = aLastName;
    wasSet = true;
    return wasSet;
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

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public void delete() {}

}
