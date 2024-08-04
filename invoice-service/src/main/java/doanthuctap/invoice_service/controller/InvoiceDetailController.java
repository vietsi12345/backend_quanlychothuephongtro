package doanthuctap.invoice_service.controller;

import doanthuctap.invoice_service.model.Invoice;
import doanthuctap.invoice_service.model.InvoiceDetail;
import doanthuctap.invoice_service.model.InvoiceDetailResponse;
import doanthuctap.invoice_service.model.ServiceDto;
import doanthuctap.invoice_service.repository.InvoiceRepository;
import doanthuctap.invoice_service.service.InvoiceDetailService;
import doanthuctap.invoice_service.service.InvoiceService;
import doanthuctap.invoice_service.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/invoice-details")
public class InvoiceDetailController {
    @Autowired
    private InvoiceDetailService invoiceDetailService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @PostMapping
    public ResponseEntity<InvoiceDetailResponse> createInvoiceDetail (@RequestBody InvoiceDetail invoiceDetail) throws Exception {
        ServiceDto serviceDto = serviceService.getServiceById(invoiceDetail.getServiceId());
        invoiceDetail.setUnitPrice(serviceDto.getUnitPrice());
        invoiceDetail.setAmount(serviceDto.getUnitPrice().multiply(BigDecimal.valueOf(invoiceDetail.getQuantity())));
        // cập nhật lại tổng tiền cho bên invoice
        Invoice invoice = invoiceService.getInvoiceById(invoiceDetail.getInvoiceId());
        invoice.setTotalService(invoiceDetail.getAmount().add(invoice.getTotalService()));
        invoice.setTotalAmount(invoiceDetail.getAmount().add(invoice.getTotalAmount()));
        // lưu invoice lại vào cơ sở dữ liệu
        invoiceRepository.save(invoice);
        //
        InvoiceDetail createInvoiceDetail =  invoiceDetailService.createInvoiceDetail(invoiceDetail);
        InvoiceDetailResponse invoiceDetailResponse = ConvertInvoiceDetailToInvoiceDetailResponse(createInvoiceDetail);
        return ResponseEntity.ok(invoiceDetailResponse);
    }

    @GetMapping("/by-invoice/{id}")
    public ResponseEntity<List<InvoiceDetailResponse>> getInvoiceDetailForDetail (@PathVariable Long id) throws Exception {
        List<InvoiceDetail> invoiceDetails = invoiceDetailService.getInvoiceDetailByInvoiceId(id);
        List <InvoiceDetailResponse> invoiceDetailResponses = new ArrayList<>();
        for (InvoiceDetail invoiceDetail: invoiceDetails) {
            InvoiceDetailResponse invoiceDetailResponse = new InvoiceDetailResponse();
            invoiceDetailResponse = ConvertInvoiceDetailToInvoiceDetailResponse(invoiceDetail);
            invoiceDetailResponses.add(invoiceDetailResponse);
        }

        return ResponseEntity.ok(invoiceDetailResponses);
    }



    public InvoiceDetailResponse ConvertInvoiceDetailToInvoiceDetailResponse (InvoiceDetail invoiceDetail) throws Exception {
        InvoiceDetailResponse invoiceDetailResponse = new InvoiceDetailResponse();
        invoiceDetailResponse.setInvoiceId(invoiceDetail.getInvoiceId());
        invoiceDetailResponse.setId(invoiceDetail.getId());
        invoiceDetailResponse.setAmount(invoiceDetail.getAmount());
        invoiceDetailResponse.setQuantity(invoiceDetail.getQuantity());
        invoiceDetailResponse.setUnitPrice(invoiceDetail.getUnitPrice());
        ServiceDto serviceDto = serviceService.getServiceById(invoiceDetail.getServiceId());
        invoiceDetailResponse.setServiceName(serviceDto.getName());
        return invoiceDetailResponse;
    }
}
