package ca.mcgill.ecse321.eventregistration.model;

public class Card extends PaymentMethod {

  public enum PaymentCardType {
    CreditCard, DebitCard
  }

  // Card Attributes
  private PaymentCardType paymentCardType;
  private int number;
  private int expirationDate;
  private int ccv;

  public Card(int aId, String aName, Customer aCustomer, PaymentCardType aPaymentCardType,
      int aNumber, int aExpirationDate, int aCcv) {
    super(aId, aName, aCustomer);
    paymentCardType = aPaymentCardType;
    number = aNumber;
    expirationDate = aExpirationDate;
    ccv = aCcv;
  }

  public boolean setPaymentCardType(PaymentCardType aPaymentCardType) {
    boolean wasSet = false;
    paymentCardType = aPaymentCardType;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumber(int aNumber) {
    boolean wasSet = false;
    number = aNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setExpirationDate(int aExpirationDate) {
    boolean wasSet = false;
    expirationDate = aExpirationDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setCcv(int aCcv) {
    boolean wasSet = false;
    ccv = aCcv;
    wasSet = true;
    return wasSet;
  }

  public PaymentCardType getPaymentCardType() {
    return paymentCardType;
  }

  public int getNumber() {
    return number;
  }

  public int getExpirationDate() {
    return expirationDate;
  }

  public int getCcv() {
    return ccv;
  }

  public void delete() {
    super.delete();
  }

}
