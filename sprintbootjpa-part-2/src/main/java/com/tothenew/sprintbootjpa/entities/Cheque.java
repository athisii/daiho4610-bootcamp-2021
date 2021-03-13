package com.tothenew.sprintbootjpa.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bank_cheque")
public class Cheque extends TablePerClassPayment {
    private String chequeNumber;

    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setCardNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }
}
