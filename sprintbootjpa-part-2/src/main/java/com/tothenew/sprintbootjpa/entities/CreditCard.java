package com.tothenew.sprintbootjpa.entities;

import javax.persistence.Entity;

@Entity
public class CreditCard extends TablePerClassPayment {
    private String cardNumber;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
