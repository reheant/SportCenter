package ca.mcgill.ecse321.sportscenter.model;

// line 29 "../../../../../../model.ump"
// line 167 "../../../../../../model.ump"
public class Customer extends AccountRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private boolean wantsEmailConfirmation;

  //Customer Associations
  private Account account;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(int aId, boolean aWantsEmailConfirmation, Account aAccount)
  {
    super(aId);
    wantsEmailConfirmation = aWantsEmailConfirmation;
    if (!setAccount(aAccount))
    {
      throw new RuntimeException("Unable to create Customer due to aAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setWantsEmailConfirmation(boolean aWantsEmailConfirmation)
  {
    boolean wasSet = false;
    wantsEmailConfirmation = aWantsEmailConfirmation;
    wasSet = true;
    return wasSet;
  }

  public boolean getWantsEmailConfirmation()
  {
    return wantsEmailConfirmation;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isWantsEmailConfirmation()
  {
    return wantsEmailConfirmation;
  }
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


  public String toString()
  {
    return super.toString() + "["+
            "wantsEmailConfirmation" + ":" + getWantsEmailConfirmation()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "account = "+(getAccount()!=null?Integer.toHexString(System.identityHashCode(getAccount())):"null");
  }
}