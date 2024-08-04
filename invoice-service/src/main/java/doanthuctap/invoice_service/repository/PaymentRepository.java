package doanthuctap.invoice_service.repository;

import doanthuctap.invoice_service.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
