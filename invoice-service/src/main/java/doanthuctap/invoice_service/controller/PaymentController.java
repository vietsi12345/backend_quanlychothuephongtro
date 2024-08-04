package doanthuctap.invoice_service.controller;

import doanthuctap.invoice_service.model.Invoice;
import doanthuctap.invoice_service.model.Payment;
import doanthuctap.invoice_service.model.PaymentRequest;
import doanthuctap.invoice_service.repository.InvoiceRepository;
import doanthuctap.invoice_service.service.InvoiceService;
import doanthuctap.invoice_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @PostMapping
    public ResponseEntity<?> createPayment (@RequestBody PaymentRequest paymentRequest) throws Exception {
        Invoice invoice =  invoiceService.getInvoiceById(paymentRequest.getInvoiceId());
        Payment payment = new Payment();
        payment.setStatus("Chờ xử lí");
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setAmount(paymentRequest.getAmount().toString());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setInvoice(invoice);
        return ResponseEntity.ok(paymentService.createPayment(payment));
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayment () {
        return  ResponseEntity.ok(paymentService.getAllPayment());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updateStatus (@PathVariable Long id,
            @RequestParam String status) throws Exception {
        return ResponseEntity.ok(paymentService.updateStatus(id,status));
    }

}
