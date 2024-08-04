package doanthuctap.invoice_service.repository;

import doanthuctap.invoice_service.model.InvoiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail,Long> {
    List<InvoiceDetail> findByInvoiceId (Long invoiceId);
}
