package ca.mcgill.ecse321.sportscenter.model;

import java.util.List;
import java.sql.Time;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Session {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int id;
  private Time startTime;
  private Time endTime;

  @ManyToOne()
  @JoinColumn(name = "course_id")
  private Course course;

  @OneToMany(mappedBy = "session", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  private List<Registration> registrations;

  @OneToMany(mappedBy = "session", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  private List<InstructorAssignment> instructorAssignments;

  @ManyToOne
  @JoinColumn(name = "location_id")
  private Location location;

  public void addRegistration(Registration r) {
    registrations.add(r);
  }

  public boolean removeRegistiation(Registration r) {
    return registrations.remove(r);
  }

  public List<?> getRegitrations() {
    return registrations;
  }

  public void addInstructorAssignments(InstructorAssignment i) {
    instructorAssignments.add(i);
  }

  public boolean removeInstructorAssignment(InstructorAssignment i) {
    return instructorAssignments.remove(i);
  }

  public List<?> getInstructorAssignments() {
    return instructorAssignments;
  }

  public Session(int aId, Time aStartTime, Time aEndTime, Course aCourse, Location aLocation) {
    id = aId;
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

  public boolean setStartTime(Time aStartTime) {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime) {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public int getId() {
    return id;
  }

  public Time getStartTime() {
    return startTime;
  }

  public Time getEndTime() {
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
