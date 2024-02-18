package ca.mcgill.ecse321.sportscenter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class InstructorAssignment {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int id;

  @ManyToOne
  @JoinColumn(name = "instructor_id")
  private Instructor instructor;

  @ManyToOne
  @JoinColumn(name = "session_id")
  private Session session;

  public InstructorAssignment() {}

  public InstructorAssignment(Instructor aInstructor, Session aSession) {
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
