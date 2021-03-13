package com.tothenew.sprintbootjpa.services;

import com.tothenew.sprintbootjpa.entities.Cheque1;
import com.tothenew.sprintbootjpa.entities.CreditCard1;
import com.tothenew.sprintbootjpa.entities.SingleTablePayment;
import com.tothenew.sprintbootjpa.repos.SingleTablePaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SingleTablePaymentService {
    @Autowired
    private SingleTablePaymentRepository singleTablePaymentRepository;


    public List<SingleTablePayment> getAllPayment() {
        return singleTablePaymentRepository.findAll();
    }

    public void addPayment(PaymentWrapper payment) {
        if (payment.getCardNumber() != null) {
            CreditCard1 card = new CreditCard1();
            card.setAmount(payment.getAmount());
            card.setCardNumber(payment.getCardNumber());
            singleTablePaymentRepository.save(card);
        } else {
            Cheque1 ch = new Cheque1();
            ch.setAmount(payment.getAmount());
            ch.setChequeNumber(payment.getChequeNumber());
            singleTablePaymentRepository.save(ch);
        }
    }
}
