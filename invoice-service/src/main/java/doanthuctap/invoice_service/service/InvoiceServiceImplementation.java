package doanthuctap.invoice_service.service;

import doanthuctap.invoice_service.model.Invoice;
import doanthuctap.invoice_service.model.InvoiceDetail;
import doanthuctap.invoice_service.model.InvoiceResponse;
import doanthuctap.invoice_service.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImplementation implements InvoiceService{
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private ContractService contractService;
    @Autowired
    private InvoiceDetailService invoiceDetailService;


    @Override
    public InvoiceResponse createInvoice(Invoice invoice) throws Exception {
         invoiceRepository.save(invoice);
         InvoiceResponse invoiceResponse = convertInvoiceToInvoiceResponse(invoice);
         return invoiceResponse;
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
    public List<InvoiceResponse> getInvoiceByContractId(Long contractId) throws Exception {
        List<Invoice> invoices=  invoiceRepository.findByContractIdOrderByCreatedAtDesc(contractId);
        List <InvoiceResponse> invoiceResponses= new ArrayList<>();
        for (Invoice invoice: invoices){
            InvoiceResponse invoiceResponse = convertInvoiceToInvoiceResponse(invoice);
            invoiceResponses.add(invoiceResponse);
        }
        return  invoiceResponses;
    }

    @Override
    public InvoiceResponse updateInvoice(Long id, LocalDate dueDate) throws Exception {
        Invoice invoice = getInvoiceById(id);
        if (dueDate != null ) {
            invoice.setDueDate(dueDate);
        }
        invoiceRepository.save(invoice);
        InvoiceResponse invoiceResponse = convertInvoiceToInvoiceResponse(invoice);
        return invoiceResponse;
    }

    @Override
    public Map<YearMonth, BigDecimal> getMonthlyRevenue(int year) {

//        return invoiceRepository.findByYear(year)
//                .stream()
//                .collect(Collectors.groupingBy(
//                        Invoice::getBillMonth,
//                        Collectors.reducing(BigDecimal.ZERO, Invoice::getTotalAmount, BigDecimal::add)
//                ));
        return null;
    }


    @Override
    public BigDecimal getTotalRevenueForYear(int year) {
        return getMonthlyRevenue(year).values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public InvoiceResponse convertInvoiceToInvoiceResponse(Invoice invoice) throws Exception {
        BigDecimal roomPrice = contractService.getContractById(invoice.getContractId()).getPriceRoom();

        InvoiceResponse invoiceResponse = new InvoiceResponse();

        invoiceResponse.setId(invoice.getId());
        invoiceResponse.setBillMonth(invoice.getBillMonth());
        invoiceResponse.setStatus(invoice.getStatus());
        invoiceResponse.setCreatedAt(invoice.getCreatedAt());
        invoiceResponse.setDueDate(invoice.getDueDate());
        invoiceResponse.setPriceRoom(roomPrice);
        invoiceResponse.setContractId(invoice.getContractId());
        // lấy tổng tiền dịch vụ
        List<InvoiceDetail> invoiceDetails = invoiceDetailService.getInvoiceDetailByInvoiceId(invoice.getId());
        BigDecimal totalServiceAmount = BigDecimal.ZERO;
        for (InvoiceDetail detail : invoiceDetails) {
            totalServiceAmount = totalServiceAmount.add(detail.getUnitPrice().multiply(new BigDecimal(detail.getQuantity())));
        }
        invoiceResponse.setPriceService(totalServiceAmount);

        return invoiceResponse;
    }

}
