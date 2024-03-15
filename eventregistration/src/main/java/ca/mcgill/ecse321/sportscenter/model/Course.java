package ca.mcgill.ecse321.sportscenter.model;


import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Course {

  public enum CourseStatus {
    Refused, Approved, Pending
  }

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int id;
  private String name;
  private String description;
  private CourseStatus courseStatus;
  private boolean requiresInstructor;
  private float defaultDuration;
  private float cost;

  public Course() {};

  public Course(String aName, String aDescription, CourseStatus aCourseStatus, boolean aRequiresInstructor,
      float aDefaultDuration, float aCost) {
    name = aName;
    description = aDescription;
    courseStatus = aCourseStatus;
    requiresInstructor = aRequiresInstructor;
    defaultDuration = aDefaultDuration;
    cost = aCost;
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

  public boolean setCourseStatus(CourseStatus aCourseStatus) {
    boolean wasSet = false;
    courseStatus = aCourseStatus;
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

  public CourseStatus getCourseStatus() {
    return courseStatus;
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

  public boolean isCourseStatusApproved() {
    return this.getCourseStatus().equals(CourseStatus.Approved);
  }

  public boolean isRequiresInstructor() {
    return requiresInstructor;
  }

  public void delete() {}

}
