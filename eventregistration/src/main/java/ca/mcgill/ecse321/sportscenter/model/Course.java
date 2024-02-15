package ca.mcgill.ecse321.sportscenter.model;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int id;
  private String name;
  private String description;
  private boolean isApproved;
  private boolean requiresInstructor;
  private float defaultDuration;
  private float cost;

  @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
  private List<Session> sessions;

  public Course() {};

  public Course(String aName, String aDescription, boolean aIsApproved, boolean aRequiresInstructor,
      float aDefaultDuration, float aCost) {
    name = aName;
    description = aDescription;
    isApproved = aIsApproved;
    requiresInstructor = aRequiresInstructor;
    defaultDuration = aDefaultDuration;
    cost = aCost;
  }

  public void addSession(Session s) {
    sessions.add(s);
  }

  public boolean removeSession(Session s) {
    return sessions.remove(s);
  }

  public List<Session> getSessions() {
    return sessions;
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

  public boolean setDescription(String aDescription) {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsApproved(boolean aIsApproved) {
    boolean wasSet = false;
    isApproved = aIsApproved;
    wasSet = true;
    return wasSet;
  }

  public boolean setRequiresInstructor(boolean aRequiresInstructor) {
    boolean wasSet = false;
    requiresInstructor = aRequiresInstructor;
    wasSet = true;
    return wasSet;
  }

  public boolean setDefaultDuration(float aDefaultDuration) {
    boolean wasSet = false;
    defaultDuration = aDefaultDuration;
    wasSet = true;
    return wasSet;
  }

  public boolean setCost(float aCost) {
    boolean wasSet = false;
    cost = aCost;
    wasSet = true;
    return wasSet;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public boolean getIsApproved() {
    return isApproved;
  }

  public boolean getRequiresInstructor() {
    return requiresInstructor;
  }

  public float getDefaultDuration() {
    return defaultDuration;
  }

  public float getCost() {
    return cost;
  }

  public boolean isIsApproved() {
    return isApproved;
  }

  public boolean isRequiresInstructor() {
    return requiresInstructor;
  }

  public void delete() {}

}
