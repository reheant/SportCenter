package ca.mcgill.ecse321.sportscenter.dto;

import ca.mcgill.ecse321.sportscenter.model.Card.PaymentCardType;

public class CardDto {



    private String accountName;
    private String customerEmail;
    private PaymentCardType paymentCardType;
    private int cardNumber;
    private int expirationDate;
    private int ccv;

    public CardDto(String accountName, String customerEmail, PaymentCardType paymentCardType, int cardNumber,
      int expirationDate, int ccv){
        this.accountName = accountName;
        this.customerEmail = customerEmail;
        this.paymentCardType = paymentCardType;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.ccv = ccv;

      }
    

    public void setAccountName(String accountName){
        this.accountName = accountName;
    }

    public String getAccountName(){
        return accountName;
    }


    public void setCustomerEmail(String customerEmail){
        this.customerEmail = customerEmail;
    }

    public String getcustomerEmail(){
        return customerEmail;
    }

    public void setpaymentCardType(PaymentCardType paymentCardType){
        this.paymentCardType = paymentCardType;
    }

    public PaymentCardType getpaymentCardType(){
        return paymentCardType;
    }

    public void setCardNumber(int cardNumber){
        this.cardNumber = cardNumber;
    }

    public int getCardNumber(){
        return cardNumber;
    }

    public void setexpirationDate(int expirationDate){
        this.expirationDate = expirationDate;
    }

    public int getexpirationDater(){
        return expirationDate;
    }

    public void setCcv(int ccv){
        this.ccv = ccv;
    }

    public int getCcv(){
        return ccv;
    }
}

