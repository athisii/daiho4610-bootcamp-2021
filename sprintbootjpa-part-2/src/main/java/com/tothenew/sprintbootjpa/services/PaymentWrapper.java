package com.tothenew.sprintbootjpa.services;

public class PaymentWrapper {
    private Double amount;
    private String cardNumber;
    private String chequeNumber;

    public Double getAmount() {
        return amount;
    }

    public PaymentWrapper setAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public PaymentWrapper setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public String getChequeNumber() {
        return chequeNumber;
    }

    public PaymentWrapper setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
        return this;
    }
}
