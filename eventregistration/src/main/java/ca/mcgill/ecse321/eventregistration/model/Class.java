package ca.mcgill.ecse321.sportscenter.model;

// line 96 "../../../../../../model.ump"
// line 202 "../../../../../../model.ump"
public class Class
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Class Attributes
  private int id;
  private String name;
  private String description;
  private boolean isApproved;
  private boolean requiresInstructor;
  private float defaultDuration;
  private float cost;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Class(int aId, String aName, String aDescription, boolean aIsApproved, boolean aRequiresInstructor, float aDefaultDuration, float aCost)
  {
    id = aId;
    name = aName;
    description = aDescription;
    isApproved = aIsApproved;
    requiresInstructor = aRequiresInstructor;
    defaultDuration = aDefaultDuration;
    cost = aCost;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsApproved(boolean aIsApproved)
  {
    boolean wasSet = false;
    isApproved = aIsApproved;
    wasSet = true;
    return wasSet;
  }

  public boolean setRequiresInstructor(boolean aRequiresInstructor)
  {
    boolean wasSet = false;
    requiresInstructor = aRequiresInstructor;
    wasSet = true;
    return wasSet;
  }

  public boolean setDefaultDuration(float aDefaultDuration)
  {
    boolean wasSet = false;
    defaultDuration = aDefaultDuration;
    wasSet = true;
    return wasSet;
  }

  public boolean setCost(float aCost)
  {
    boolean wasSet = false;
    cost = aCost;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public String getDescription()
  {
    return description;
  }

  public boolean getIsApproved()
  {
    return isApproved;
  }

  public boolean getRequiresInstructor()
  {
    return requiresInstructor;
  }

  public float getDefaultDuration()
  {
    return defaultDuration;
  }

  public float getCost()
  {
    return cost;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsApproved()
  {
    return isApproved;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isRequiresInstructor()
  {
    return requiresInstructor;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "name" + ":" + getName()+ "," +
            "description" + ":" + getDescription()+ "," +
            "isApproved" + ":" + getIsApproved()+ "," +
            "requiresInstructor" + ":" + getRequiresInstructor()+ "," +
            "defaultDuration" + ":" + getDefaultDuration()+ "," +
            "cost" + ":" + getCost()+ "]";
  }
}