package com.harsh.crm.dto;

import jakarta.persistence.*;

@Entity
@Table(name="communication_logs")
public class CommunicationLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name="segment_name")
    private String segmentName;

    @Column(name="message")
    private String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
