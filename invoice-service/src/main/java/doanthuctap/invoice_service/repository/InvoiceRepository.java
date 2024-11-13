package doanthuctap.invoice_service.repository;

import doanthuctap.invoice_service.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.YearMonth;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    List<Invoice> findByContractIdOrderByCreatedAtDesc (long contractId);


    @Query("SELECT i FROM Invoice i WHERE YEAR(i.createdAt) = :year ORDER BY MONTH(i.createdAt) ASC")
    List<Invoice> findByYear(@Param("year") int year);

}
