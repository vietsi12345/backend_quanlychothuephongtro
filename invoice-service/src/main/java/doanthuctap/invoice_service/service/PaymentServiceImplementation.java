package doanthuctap.invoice_service.service;

import doanthuctap.invoice_service.model.Payment;
import doanthuctap.invoice_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImplementation implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAllPayment() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment updateStatus(Long id, String status) throws Exception {
        Payment payment = paymentRepository.findById(id).orElseThrow(()->new Exception("Không tìm thấy payment!!!")) ;
        payment.setStatus(status);
        payment.getInvoice().setStatus("Đã thanh toán");
        return paymentRepository.save(payment);
    }
}
