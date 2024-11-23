package com.harsh.crm.repository.jpa;
import com.harsh.crm.dto.CustomerSegments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface Customer_Segments_Repo extends JpaRepository<CustomerSegments,Long> {
    @Query("SELECT c.customerIds FROM CustomerSegments c WHERE c.segmentName = :segmentName")
    Optional<String> findCustomerIdsBySegmentName(String segmentName);
    Optional<CustomerSegments> findById(Long id);
}

