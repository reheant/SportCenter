package ca.mcgill.ecse321.sportscenter.model;

// line 19 "../../../../../../model.ump"
// line 157 "../../../../../../model.ump"
public class Owner extends AccountRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Owner Associations
  private Account account;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(int aId, Account aAccount)
  {
    super(aId);
    if (!setAccount(aAccount))
    {
      throw new RuntimeException("Unable to create Owner due to aAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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