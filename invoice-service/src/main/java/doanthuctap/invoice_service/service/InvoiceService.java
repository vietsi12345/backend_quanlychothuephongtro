package doanthuctap.invoice_service.service;

import doanthuctap.invoice_service.model.Invoice;
import doanthuctap.invoice_service.model.InvoiceResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public interface InvoiceService {
    InvoiceResponse createInvoice  (Invoice invoice) throws Exception;
    List<Invoice> getAllInvoice ();
    Invoice getInvoiceById (Long id) throws Exception;
    List<InvoiceResponse> getInvoiceByContractId (Long contractId) throws Exception;

    InvoiceResponse updateInvoice (Long id, LocalDate dueDate) throws Exception;

    //Thống kê
    Map<YearMonth, BigDecimal> getMonthlyRevenue(int year);

    BigDecimal getTotalRevenueForYear(int year);

    ///
    InvoiceResponse convertInvoiceToInvoiceResponse (Invoice invoice) throws Exception;
}
