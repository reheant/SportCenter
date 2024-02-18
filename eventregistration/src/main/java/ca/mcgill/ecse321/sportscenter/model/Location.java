package ca.mcgill.ecse321.sportscenter.model;


import java.sql.Time;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Location {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int id;
  private String name;
  private int capacity;
  private Time openingTime;
  private Time closingTime;

  public Location() {};

  public Location(String aName, int aCapacity, Time aOpeningTime, Time aClosingTime) {
    name = aName;
    capacity = aCapacity;
    openingTime = aOpeningTime;
    closingTime = aClosingTime;
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

  public boolean setCapacity(int aCapacity) {
    boolean wasSet = false;
    capacity = aCapacity;
    wasSet = true;
    return wasSet;
  }

  public boolean setOpeningTime(Time aOpeningTime) {
    boolean wasSet = false;
    openingTime = aOpeningTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setClosingTime(Time aClosingTime) {
    boolean wasSet = false;
    closingTime = aClosingTime;
    wasSet = true;
    return wasSet;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getCapacity() {
    return capacity;
  }

  public Time getOpeningTime() {
    return openingTime;
  }

  public Time getClosingTime() {
    return closingTime;
  }

  public void delete() {}

}
