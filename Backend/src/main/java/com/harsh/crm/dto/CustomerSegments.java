package com.harsh.crm.dto;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "customer_segments")
public class CustomerSegments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "segment_name", nullable = false)
    private String segmentName;

    @Column(name = "total_spent")
    private Integer totalSpent;

    @Column(name = "num_visits")
    private Integer numVisits;

    @Column(name = "last_visited")
    private Integer lastVisited;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "total_spent_logic")
    private String totalSpentLogic;

    @Column(name = "num_visits_logic")
    private String numVisitsLogic;

    @Column(name = "last_visited_logic")
    private String lastVisitedLogic;

    @Column(name = "product_name_logic")
    private String productNameLogic;

    // Use String or List<Integer> with JSON
    @Column(name = "customer_ids", columnDefinition = "json")
    private String customerIds;  // Use String to store JSON text directly

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSegmentName() {
        return segmentName;
    }

    public void setSegmentName(String segmentName) {
        this.segmentName = segmentName;
    }

    public Integer getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Integer totalSpent) {
        this.totalSpent = totalSpent;
    }

    public Integer getNumVisits() {
        return numVisits;
    }

    public void setNumVisits(Integer numVisits) {
        this.numVisits = numVisits;
    }

    public Integer getLastVisited() {
        return lastVisited;
    }

    public void setLastVisited(Integer lastVisited) {
        this.lastVisited = lastVisited;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTotalSpentLogic() {
        return totalSpentLogic;
    }

    public void setTotalSpentLogic(String totalSpentLogic) {
        this.totalSpentLogic = totalSpentLogic;
    }

    public String getNumVisitsLogic() {
        return numVisitsLogic;
    }

    public void setNumVisitsLogic(String numVisitsLogic) {
        this.numVisitsLogic = numVisitsLogic;
    }

    public String getLastVisitedLogic() {
        return lastVisitedLogic;
    }

    public void setLastVisitedLogic(String lastVisitedLogic) {
        this.lastVisitedLogic = lastVisitedLogic;
    }

    public String getProductNameLogic() {
        return productNameLogic;
    }

    public void setProductNameLogic(String productNameLogic) {
        this.productNameLogic = productNameLogic;
    }

    public String getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(String customerIds) {
        this.customerIds = customerIds;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
