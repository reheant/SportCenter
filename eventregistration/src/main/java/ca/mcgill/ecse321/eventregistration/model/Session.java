package ca.mcgill.ecse321.sportscenter.model;
import java.sql.Time;


public class Session
{
  private int id;
  private Time startTime;
  private Time endTime;
  private Class class;
  private Location location;

  public Session(int aId, Time aStartTime, Time aEndTime, Class aClass, Location aLocation)
  {
    id = aId;
    startTime = aStartTime;
    endTime = aEndTime;
    if (!setClass(aClass))
    {
      throw new RuntimeException("Unable to create Session due to aClass. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setLocation(aLocation))
    {
      throw new RuntimeException("Unable to create Session due to aLocation. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public Class getClass()
  {
    return class;
  }

  public Location getLocation()
  {
    return location;
  }

  public boolean setClass(Class aNewClass)
  {
    boolean wasSet = false;
    if (aNewClass != null)
    {
      class = aNewClass;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setLocation(Location aNewLocation)
  {
    boolean wasSet = false;
    if (aNewLocation != null)
    {
      location = aNewLocation;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    class = null;
    location = null;
  }
}