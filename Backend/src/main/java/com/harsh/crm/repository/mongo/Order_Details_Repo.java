package com.harsh.crm.repository.mongo;

import com.harsh.crm.dto.order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface Order_Details_Repo extends MongoRepository<order, String> {
    // Method to find orders by orderId
    Optional<order> findByOrderId(String orderId);
    Optional<List<order>> findByCustId(int custId);
    void deleteByOrderId(String orderId);
}
