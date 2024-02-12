package ca.mcgill.ecse321.sportscenter.model;

// line 24 "../../../../../../model.ump"
// line 162 "../../../../../../model.ump"
public class Instructor extends AccountRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Instructor Associations
  private Account account;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Instructor(int aId, Account aAccount)
  {
    super(aId);
    if (!setAccount(aAccount))
    {
      throw new RuntimeException("Unable to create Instructor due to aAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Account getAccount()
  {
    return account;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setAccount(Account aNewAccount)
  {
    boolean wasSet = false;
    if (aNewAccount != null)
    {
      account = aNewAccount;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    account = null;
    super.delete();
  }

}