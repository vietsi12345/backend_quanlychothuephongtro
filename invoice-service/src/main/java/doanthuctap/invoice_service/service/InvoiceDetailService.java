package doanthuctap.invoice_service.service;

import doanthuctap.invoice_service.model.InvoiceDetail;

import java.util.List;

public interface InvoiceDetailService {
    InvoiceDetail createInvoiceDetail (InvoiceDetail invoiceDetail) ;

    List<InvoiceDetail> getInvoiceDetailByInvoiceId (Long invoiceId);
}
