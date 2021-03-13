package com.tothenew.sprintbootjpa.services;

import com.tothenew.sprintbootjpa.entities.JoinedCard;
import com.tothenew.sprintbootjpa.entities.JoinedCheque;
import com.tothenew.sprintbootjpa.repos.JoinedPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JoinedPaymentService {
    @Autowired
    private JoinedPaymentRepository joinedPaymentRepository;

    public void saveJoinedPayment(PaymentWrapper payment) {
        if (payment.getCardNumber() != null) {
            JoinedCard joinedCard = new JoinedCard();
            joinedCard.setAmount(payment.getAmount());
            joinedCard.setCardNumber(payment.getCardNumber());
            joinedPaymentRepository.save(joinedCard);
        } else {
            JoinedCheque joinedCheque = new JoinedCheque();
            joinedCheque.setAmount(payment.getAmount());
            joinedCheque.setChequeNumber(payment.getChequeNumber());
            joinedPaymentRepository.save(joinedCheque);
        }
    }
}
