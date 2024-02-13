package ca.mcgill.ecse321.eventregistration.model;

public class InstructorAssignment {

  private int id;

  private Instructor instructor;
  private Session session;

  public InstructorAssignment(int aId, Instructor aInstructor, Session aSession) {
    id = aId;
    if (!setInstructor(aInstructor)) {
      throw new RuntimeException(
          "Unable to create InstructorAssignment due to aInstructor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setSession(aSession)) {
      throw new RuntimeException(
          "Unable to create InstructorAssignment due to aSession. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public Instructor getInstructor() {
    return instructor;
  }

  public Session getSession() {
    return session;
  }

  public boolean setInstructor(Instructor aNewInstructor) {
    boolean wasSet = false;
    if (aNewInstructor != null) {
      instructor = aNewInstructor;
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
    instructor = null;
    session = null;
  }

}
