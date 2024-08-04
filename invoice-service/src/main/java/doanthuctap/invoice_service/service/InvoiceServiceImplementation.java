package doanthuctap.invoice_service.service;

import doanthuctap.invoice_service.model.Invoice;
import doanthuctap.invoice_service.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImplementation implements InvoiceService{
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public Invoice createInvoice(Invoice invoice) throws Exception {
        return invoiceRepository.save(invoice);
    }

    @Override
    public List<Invoice> getAllInvoice() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice getInvoiceById(Long id) throws Exception {
        return invoiceRepository.findById(id).orElseThrow(()-> new Exception("Khonog tim thay hoa don nay!"));
    }

    @Override
    public List<Invoice> getInvoiceByContractId(Long contractId) {
        return invoiceRepository.findByContractIdOrderByCreatedAtDesc(contractId);
    }

    @Override
    public Invoice updateInvoice(Long id, LocalDate dueDate) throws Exception {
        Invoice invoice = getInvoiceById(id);
        if (dueDate != null ) {
            invoice.setDueDate(dueDate);
        }
        return invoiceRepository.save(invoice);
    }

    @Override
    public Map<YearMonth, BigDecimal> getMonthlyRevenue(int year) {
        return invoiceRepository.findByYear(year)
                .stream()
                .collect(Collectors.groupingBy(
                        Invoice::getBillMonth,
                        Collectors.reducing(BigDecimal.ZERO, Invoice::getTotalAmount, BigDecimal::add)
                ));
    }


    @Override
    public BigDecimal getTotalRevenueForYear(int year) {
        return getMonthlyRevenue(year).values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
