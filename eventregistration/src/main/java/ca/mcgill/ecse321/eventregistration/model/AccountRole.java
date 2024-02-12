package ca.mcgill.ecse321.sportscenter.model;

// line 4 "../../../../../../model.ump"
// line 147 "../../../../../../model.ump"
public abstract class AccountRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AccountRole Attributes
  private int id;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public AccountRole(int aId)
  {
    id = aId;
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

  public int getId()
  {
    return id;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]";
  }
}