package doanthuctap.invoice_service.service;

import doanthuctap.invoice_service.model.InvoiceDetail;
import doanthuctap.invoice_service.repository.InvoiceDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceDetailServiceImplementation implements InvoiceDetailService{
    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @Override
    public InvoiceDetail createInvoiceDetail(InvoiceDetail invoiceDetail) {
        return invoiceDetailRepository.save(invoiceDetail);
    }

    @Override
    public List<InvoiceDetail> getInvoiceDetailByInvoiceId(Long invoiceId) {
        return invoiceDetailRepository.findByInvoiceId(invoiceId);
    }
}
