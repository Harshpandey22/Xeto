package com.harsh.crm.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name="customer_data")
public class Customer {
    @Id
    @Column(name="customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    @Column(name="first_name")
    @JsonProperty("first_name") // This will map the JSON field 'first_name' to 'firstName' in Java
    private String firstName;

    @Column(name="last_name")
    @JsonProperty("last_name") // This will map the JSON field 'last_name' to 'lastName' in Java
    private String lastName;

    @Column(name="email_id")
    @JsonProperty("email_id") // This will map the JSON field 'email_id' to 'emailId' in Java
    private String emailId;

    @Column(name="phone_number")
    @JsonProperty("phone_number") // This will map the JSON field 'phone_number' to 'phoneNumber' in Java
    private String phoneNumber;


    @Column(name="customer_visits")
    @JsonProperty("customer_visits")
    private Integer customerVisits;

    @Column(name="delivery_receipt")
    @JsonProperty("delivery_receipt")
    private String deliveryReceipt;

    public String getDeliveryReceipt() {
        return deliveryReceipt;
    }

    public void setDeliveryReceipt(String deliveryReceipt) {
        this.deliveryReceipt = deliveryReceipt;
    }

    public Integer getCustomerVisits() {
        return customerVisits;
    }

    public void setCustomerVisits(Integer customerVisits) {
        this.customerVisits = customerVisits;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Customer() {
    }
}
