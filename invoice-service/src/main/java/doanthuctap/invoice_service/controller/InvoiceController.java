package doanthuctap.invoice_service.controller;

import com.netflix.discovery.converters.Auto;
import doanthuctap.invoice_service.model.ContractDto;
import doanthuctap.invoice_service.model.Invoice;
import doanthuctap.invoice_service.model.InvoiceResponse;
import doanthuctap.invoice_service.model.RoomDto;
import doanthuctap.invoice_service.service.ContractService;
import doanthuctap.invoice_service.service.InvoiceService;
import doanthuctap.invoice_service.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<?> createInvoice (@RequestBody Invoice invoice) throws  Exception{
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setStatus("Chưa thanh toán");
        // lấy giá phòng
        ContractDto contractDto = contractService.getContractById(invoice.getContractId());
        if("Hết hợp đồng".equals(contractDto.getStatus())){
            return ResponseEntity.status(226).body("Hợp đồng này đã kết thúc rồi!!!");
        } else{
            System.out.println("contract: "+contractDto);
            RoomDto roomDto = roomService.getRoomById(contractDto.getBooking().getRoomId());
            //
//            invoice.setRoomPrice(roomDto.getPrice());
//            invoice.setTotalService(BigDecimal.valueOf(0));
//            invoice.setTotalAmount(roomDto.getPrice());
            InvoiceResponse createInvoice = invoiceService.createInvoice(invoice);
            return ResponseEntity.ok(createInvoice);
        }
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices (){
        return  ResponseEntity.ok(invoiceService.getAllInvoice());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById (@PathVariable Long id) throws Exception {
        return  ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @GetMapping("/by-contract/{id}")
    public ResponseEntity<List<InvoiceResponse>> getInvoiceForContract (@PathVariable Long id) throws Exception {
        return  ResponseEntity.ok(invoiceService.getInvoiceByContractId(id));
    }

    @PutMapping ("/{id}")
    public ResponseEntity <InvoiceResponse> updateInvoice (@PathVariable Long id,
                                                   @RequestParam (required = false)LocalDate dueDate) throws Exception {
        return ResponseEntity.ok(invoiceService.updateInvoice(id,dueDate));
    }

    @GetMapping("/revenue/monthly")
    public ResponseEntity<Map<YearMonth, BigDecimal>> getMonthlyRevenue(@RequestParam int year) {
        return ResponseEntity.ok(invoiceService.getMonthlyRevenue(year));
    }

}
