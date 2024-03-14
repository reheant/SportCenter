package ca.mcgill.ecse321.sportscenter.model;


import java.sql.Time;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Session {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int id;
  private LocalDateTime startTime;
  private LocalDateTime endTime;

  @ManyToOne()
  @JoinColumn(name = "course_id")
  private Course course;

  @ManyToOne
  @JoinColumn(name = "location_id")
  private Location location;

  @ManyToOne
  @JoinColumn(name = "instructor_assignment_id")
  private InstructorAssignment instructorAssignment;

  public Session() {}

  public Session(LocalDateTime aStartTime, LocalDateTime aEndTime, Course aCourse, Location aLocation) {
    startTime = aStartTime;
    endTime = aEndTime;
    if (!setCourse(aCourse)) {
      throw new RuntimeException(
          "Unable to create Session due to aCourse. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setLocation(aLocation)) {
      throw new RuntimeException(
          "Unable to create Session due to aLocation. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }


  public boolean setId(int aId) {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartTime(LocalDateTime aStartTime) {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(LocalDateTime aEndTime) {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public int getId() {
    return id;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public Course getCourse() {
    return course;
  }

  public Location getLocation() {
    return location;
  }

  public boolean setCourse(Course aNewCourse) {
    boolean wasSet = false;
    if (aNewCourse != null) {
      course = aNewCourse;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setLocation(Location aNewLocation) {
    boolean wasSet = false;
    if (aNewLocation != null) {
      location = aNewLocation;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete() {
    course = null;
    location = null;
  }

}
