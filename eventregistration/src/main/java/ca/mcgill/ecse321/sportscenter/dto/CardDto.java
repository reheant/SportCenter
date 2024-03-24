package ca.mcgill.ecse321.sportscenter.dto;

import ca.mcgill.ecse321.sportscenter.model.Card.PaymentCardType;

public class CardDto {
    private String name;
    private PaymentCardType paymentCardType;
    private int number;
    private int expirationDate;
    private int ccv;

    private String customerAccountEmail;

    public CardDto() {
    }

    public CardDto(String name, PaymentCardType paymentCardType, int number,
      int expirationDate, int ccv, String customerAccountEmail){
        this.name = name;
        this.paymentCardType = paymentCardType;
        this.number = number;
        this.expirationDate = expirationDate;
        this.ccv = ccv;
        this.customerAccountEmail = customerAccountEmail;
      }
    

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setpaymentCardType(PaymentCardType paymentCardType){
        this.paymentCardType = paymentCardType;
    }

    public PaymentCardType getpaymentCardType(){
        return paymentCardType;
    }

    public void setNumber(int cardNumber){
        this.number = cardNumber;
    }

    public int getNumber(){
        return number;
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

    public String getCustomerAccountEmail(){
        return customerAccountEmail;
    }

    public void setCustomerAccountEmail(String customerAccountEmail){
        this.customerAccountEmail = customerAccountEmail;
    }

}

