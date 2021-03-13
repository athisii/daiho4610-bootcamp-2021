package com.tothenew.sprintbootjpa.controllers;

import com.tothenew.sprintbootjpa.entities.SingleTablePayment;
import com.tothenew.sprintbootjpa.services.JoinedPaymentService;
import com.tothenew.sprintbootjpa.services.PaymentWrapper;
import com.tothenew.sprintbootjpa.services.SingleTablePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/im")
@RestController
public class PaymentController {

    @Autowired
    private SingleTablePaymentService singleTablePaymentService;

    @Autowired
    private JoinedPaymentService joinedPaymentService;

    @GetMapping("/stp")
    public List<SingleTablePayment> retrieveAllSingleTablePayment() {
        return singleTablePaymentService.getAllPayment();
    }

    @PostMapping("/stp")
    public void createSingleTablePayment(@RequestBody PaymentWrapper payment) {
        singleTablePaymentService.addPayment(payment);
    }

    @PostMapping("/joined")
    public void createJoinedPayment(@RequestBody PaymentWrapper payment) {
        joinedPaymentService.saveJoinedPayment(payment);
    }


}
