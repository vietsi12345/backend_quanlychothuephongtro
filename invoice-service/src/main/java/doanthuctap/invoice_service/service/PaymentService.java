package doanthuctap.invoice_service.service;

import doanthuctap.invoice_service.model.Payment;

import java.util.List;

public interface PaymentService {
    Payment createPayment (Payment payment);

    List<Payment> getAllPayment ();

    Payment updateStatus (Long id, String status) throws Exception;
}
