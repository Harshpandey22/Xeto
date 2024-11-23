package com.harsh.crm.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsh.crm.dto.CommunicationLogs;
import com.harsh.crm.dto.Customer;
import com.harsh.crm.dto.CustomerSegments;
import com.harsh.crm.dto.order;
import com.harsh.crm.repository.jpa.CommunicationsLogRepo;
import com.harsh.crm.repository.jpa.Customer_Segments_Repo;
import com.harsh.crm.repository.mongo.Order_Details_Repo;
import com.harsh.crm.repository.jpa.User_Details_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class WebController {

    private final User_Details_Repo userDetailsRepo;
    private final Order_Details_Repo orderDetailsRepo;
    private final Customer_Segments_Repo customerSegmentsRepo;
    private final CommunicationsLogRepo communicationsLogRepo;

    @Autowired
    public WebController(User_Details_Repo userDetailsRepo, Order_Details_Repo orderDetailsRepo, Customer_Segments_Repo customerSegmentsRepo, CommunicationsLogRepo communicationsLogRepo) {
        this.userDetailsRepo = userDetailsRepo;
        this.orderDetailsRepo = orderDetailsRepo;
        this.customerSegmentsRepo = customerSegmentsRepo;
        this.communicationsLogRepo = communicationsLogRepo;
    }

    @PostMapping("/store_customer")
    public ResponseEntity<String> storeData(@RequestBody Customer data) {
        if (data == null) {
            return new ResponseEntity<>("Invalid customer data", HttpStatus.BAD_REQUEST);
        }
        userDetailsRepo.save(data);
        return new ResponseEntity<>("Customer Data stored", HttpStatus.CREATED);
    }

    @GetMapping("/get_customer")
    public ResponseEntity<List<Customer>> getData() {
        List<Customer> Customers = userDetailsRepo.findAll();
        if (Customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(Customers, HttpStatus.OK);
    }

    @DeleteMapping("/delete_single_customer/{id}")
    public ResponseEntity<String> deleteData(@PathVariable int id) {
        Optional<Customer> existingCustomer = userDetailsRepo.findById(id);
        if (existingCustomer.isEmpty()) {
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }

        userDetailsRepo.deleteById(id);
        return new ResponseEntity<>("User Deleted", HttpStatus.OK);
    }

    @DeleteMapping("/delete_all_customer")
    public ResponseEntity<String> deleteAllCustomer() {
        userDetailsRepo.deleteAll();
        userDetailsRepo.resetAutoIncrement();
        return ResponseEntity.ok("All users deleted");
    }

    @PutMapping("/put_customer_data/{id}")
    public ResponseEntity<String> putData(@RequestBody Customer data, @PathVariable int id) {
        Optional<Customer> existingCustomer = userDetailsRepo.findById(id);
        if (existingCustomer.isEmpty()) {
            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
        }

        Customer cust = existingCustomer.get();
        cust.setPhoneNumber(data.getPhoneNumber());
        cust.setEmailId(data.getEmailId());
        cust.setFirstName(data.getFirstName());
        cust.setLastName(data.getLastName());
        cust.setCustomerVisits(data.getCustomerVisits());
        userDetailsRepo.save(cust);
        return new ResponseEntity<>("Details Successfully Updated", HttpStatus.OK);
    }

    @PostMapping("/store_order")
    public ResponseEntity<String> storeOrder(@RequestBody order data) {
        if (data == null) {
            return new ResponseEntity<>("Invalid order data", HttpStatus.BAD_REQUEST);
        }
        int custId = data.getCustId();
        Optional<Customer> existingCustomer = userDetailsRepo.findById(custId);
        if(existingCustomer.isPresent()){
            existingCustomer.get().setCustomerVisits(existingCustomer.get().getCustomerVisits()+1);
            userDetailsRepo.save(existingCustomer.get());
        }
        orderDetailsRepo.save(data);
        return new ResponseEntity<>("Order Data stored", HttpStatus.CREATED);
    }

    @GetMapping("/get_orders")
    public ResponseEntity<List<order>> getOrders() {
        List<order> orders = orderDetailsRepo.findAll();
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/get_single_order/{orderId}")
    public ResponseEntity<order> getSingleOrder(@PathVariable String orderId) {
        Optional<order> orders = orderDetailsRepo.findByOrderId(orderId);
        if (orders.isPresent()) {
            return new ResponseEntity<>(orders.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/get_order_cust")
    public ResponseEntity<List<order>> getOrdersCustId(@RequestParam int custId) {
        Optional<List<order>> orders = orderDetailsRepo.findByCustId(custId);
        if (orders.isPresent()) {
            return new ResponseEntity<>(orders.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete_order_data/{id}")
    public ResponseEntity<String> deleteOrderData(@PathVariable String id) {
        orderDetailsRepo.deleteByOrderId(id);
        return new ResponseEntity<>("Order Deleted", HttpStatus.OK);
    }

    @DeleteMapping("/delete_all_orders")
    public ResponseEntity<String> deleteAllData() {
        orderDetailsRepo.deleteAll();
        return new ResponseEntity<>("All Orders Deleted", HttpStatus.OK);
    }

    @DeleteMapping("/delete_order_custId")
    public ResponseEntity<String> deleteOrderCustId(@RequestParam int custId){
        Optional<List<order>> orders = orderDetailsRepo.findByCustId(custId);
        if(orders.isPresent()){
            for(order t1:orders.get()){
                ResponseEntity<String> out = deleteOrderData(t1.getOrderId());
            }
        }
        return new ResponseEntity<>("Order Deleted",HttpStatus.OK);
    }

    @PutMapping("/put_order_data/{id}")
    public ResponseEntity<String> putOrderData(@RequestBody order data, @PathVariable String id) {
        Optional<order> existingOrder = orderDetailsRepo.findByOrderId(id);
        if (existingOrder.isEmpty()) {
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }

        order or = existingOrder.get();
        or.setOrderDate(data.getOrderDate());
        or.setOrderPrice(data.getOrderPrice());
        or.setCustId(data.getCustId());
        or.setProductName(data.getProductName());
        orderDetailsRepo.save(or);
        return new ResponseEntity<>("Details Successfully Updated", HttpStatus.OK);
    }

    @GetMapping("/customer_segment")
    public ResponseEntity<List<Customer>> customerSegment(
            @RequestParam(required = false) Integer totalSpent,
            @RequestParam(required = false) Integer numVisits,
            @RequestParam(required = false) Integer lastVisited,
            @RequestParam(required = false) String productName,
            @RequestParam(defaultValue = "AND") String totalSpentLogic,
            @RequestParam(defaultValue = "AND") String numVisitsLogic,
            @RequestParam(defaultValue = "AND") String lastVisitedLogic,
            @RequestParam(defaultValue = "AND") String productNameLogic,
            @RequestParam(defaultValue = "Segment1") String segmentName
    ) {
        Set<Integer> custIds = customerSegmentIds(totalSpent, numVisits, lastVisited, productName,
                totalSpentLogic, numVisitsLogic, lastVisitedLogic, productNameLogic);

        List<Customer> customersGive = new ArrayList<>();
        List<Customer> Customers = getData().getBody();

        for (Customer temp : Customers) {
            if (custIds.contains(temp.getCustomerId())) {
                customersGive.add(temp);
            }
        }

        CustomerSegments segment = new CustomerSegments();
        segment.setSegmentName(segmentName);
        segment.setTotalSpent(totalSpent);
        segment.setNumVisits(numVisits);
        segment.setLastVisited(lastVisited);
        segment.setProductName(productName);
        segment.setTotalSpentLogic(totalSpentLogic);
        segment.setNumVisitsLogic(numVisitsLogic);
        segment.setLastVisitedLogic(lastVisitedLogic);
        segment.setProductNameLogic(productNameLogic);
        segment.setCustomerIds(custIds.toString());
        segment.setCreatedAt(LocalDateTime.now());
        customerSegmentsRepo.save(segment);

        return new ResponseEntity<>(customersGive, HttpStatus.OK);
    }

    public Set<Integer> customerSegmentIds(Integer totalSpent, Integer numVisits, Integer lastVisited, String productName,
                                           String totalSpentLogic, String numVisitsLogic, String lastVisitedLogic, String productNameLogic) {

        Set<Integer> totalSpentCustomers = new HashSet<>();
        Set<Integer> numVisitsCustomers = new HashSet<>();
        Set<Integer> lastVisitedCustomers = new HashSet<>();
        Set<Integer> productNameCustomers = new HashSet<>();

        List<Customer> Customers = getData().getBody();

        // Total Spent Criterion
        if (totalSpent != null) {
            for (Customer temp : Customers) {
                int custId = temp.getCustomerId();
                int totalSpentAmount = getOrdersCustId(custId).getBody().stream()
                        .mapToInt(order -> Integer.parseInt(order.getOrderPrice()))
                        .sum();
                if (totalSpentAmount >= totalSpent) {
                    totalSpentCustomers.add(custId);
                }
            }
        }

        // Number of Visits Criterion
        if (numVisits != null) {
            for (Customer temp : Customers) {
                if (temp.getCustomerVisits() >= numVisits) {
                    numVisitsCustomers.add(temp.getCustomerId());
                }
            }
        }

        // Last Visited Criterion
//        if (lastVisited != null) {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//            LocalDate currentDate = LocalDate.now();
//            for (Customer temp : Customers) {
//                int custId = temp.getCustomerId();
//                boolean meetsLastVisited = getOrdersCustId(custId).getBody().stream()
//                        .anyMatch(order -> ChronoUnit.MONTHS.between(
//                                LocalDate.parse(order.getOrderDate(), formatter), currentDate) > lastVisited);
//                if (meetsLastVisited) {
//                    lastVisitedCustomers.add(custId);
//                }
//            }
//        }

        // Product Name Criterion
        if (productName != null) {
            for (Customer temp : Customers) {
                int custId = temp.getCustomerId();
                boolean hasProduct = getOrdersCustId(custId).getBody().stream()
                        .anyMatch(order -> Objects.equals(order.getProductName(), productName));
                if (hasProduct) {
                    productNameCustomers.add(custId);
                }
            }
        }

        // Combine results with AND/OR logic based on each criterion's logic parameter
        Set<Integer> combinedResult = new HashSet<>(Customers.stream().map(Customer::getCustomerId).collect(Collectors.toSet()));

        if (!totalSpentCustomers.isEmpty()) {
            if ("AND".equalsIgnoreCase(totalSpentLogic)) {
                combinedResult.retainAll(totalSpentCustomers);
            } else if ("OR".equalsIgnoreCase(totalSpentLogic)) {
                combinedResult.addAll(totalSpentCustomers);
            }
        }

        if (!numVisitsCustomers.isEmpty()) {
            if ("AND".equalsIgnoreCase(numVisitsLogic)) {
                combinedResult.retainAll(numVisitsCustomers);
            } else if ("OR".equalsIgnoreCase(numVisitsLogic)) {
                combinedResult.addAll(numVisitsCustomers);
            }
        }

        if (!lastVisitedCustomers.isEmpty()) {
            if ("AND".equalsIgnoreCase(lastVisitedLogic)) {
                combinedResult.retainAll(lastVisitedCustomers);
            } else if ("OR".equalsIgnoreCase(lastVisitedLogic)) {
                combinedResult.addAll(lastVisitedCustomers);
            }
        }

        if (!productNameCustomers.isEmpty()) {
            if ("AND".equalsIgnoreCase(productNameLogic)) {
                combinedResult.retainAll(productNameCustomers);
            } else if ("OR".equalsIgnoreCase(productNameLogic)) {
                combinedResult.addAll(productNameCustomers);
            }
        }

        return combinedResult;
    }

        @GetMapping("/get_customerSegment_customers")
    public ResponseEntity<List<Customer>> getCustomerIdsBySegment(@RequestParam String segmentName) {
        Optional<String> customerIdsStrOpt = customerSegmentsRepo.findCustomerIdsBySegmentName(segmentName);

        if (customerIdsStrOpt.isPresent()) {
            String customerIdsStr = customerIdsStrOpt.get();

            // Parse the JSON string into a Set of integers using ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // Remove brackets and parse into a Set<Integer>
                Set<Integer> customerIds = objectMapper.readValue(customerIdsStr, Set.class);
                List<Customer> customers = new ArrayList<>();
                for(int t1:customerIds){
                    Optional<Customer> existingCustomer = userDetailsRepo.findById(t1);
                    if(existingCustomer.isPresent()){
                        customers.add(existingCustomer.get());
                    }
                }
                return new ResponseEntity<>(customers, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get_segments")
    public List<CustomerSegments> getSegments(){
        List<CustomerSegments> customerSegments = customerSegmentsRepo.findAll();
        return customerSegments;
    }

    @GetMapping("/get_communication_logs")
    public List<CommunicationLogs> getCommunicationLogs(){
        List<CommunicationLogs> communicationLogs = communicationsLogRepo.findAll();
        return communicationLogs;
    }

    @PostMapping("/send_personalized_message")
    public List<String> sendMessage(@RequestBody CommunicationLogs data){
        communicationsLogRepo.save(data);
        List<String> result = sendPersonalMessage(data.getMessage(),data.getSegmentName());
        return result;
    }

    public List<String> sendPersonalMessage(String message, String segmentName) {
        List<Customer> customers = getCustomerIdsBySegment(segmentName).getBody();
        String temp = "Hi %s," + message;
        Random random = new Random();
        int successCount = 0;
        int failureCount = 0;
        List<String> messageSent = new ArrayList<>();
        for (Customer cust : customers) {
            boolean isSuccess = random.nextInt(10) < 9;

            if (isSuccess) {
                System.out.println(String.format(temp, cust.getFirstName() + " " + cust.getLastName()));
                messageSent.add(String.format(temp, cust.getFirstName() + " " + cust.getLastName()));
                cust.setDeliveryReceipt("Success");
                successCount++;
            } else {
                System.out.println("Message failed to send to " + cust.getFirstName() + " " + cust.getLastName());
                messageSent.add("Message failed to send to " + cust.getFirstName() + " " + cust.getLastName());
                cust.setDeliveryReceipt("Failed");
                failureCount++;
            }

            putData(cust,cust.getCustomerId());
        }

        messageSent.add(String.format("Messages sent to %s: %d successful, %d failed", segmentName, successCount, failureCount));
        return messageSent;
    }

    @GetMapping("/get_customer_count")
    public int getCustomerCount(){
        List<Customer> customers =  userDetailsRepo.findAll();
        return customers.size();
    }

    @GetMapping("/get_order_count")
    public int getOrderCount(){
        List<order> orders =  orderDetailsRepo.findAll();
        return orders.size();
    }

    @GetMapping("/get_total_revenue")
    public int getTotalRevenue(){
        List<order> orders =  orderDetailsRepo.findAll();
        int sum = 0;
        for(order t1:orders){
            sum+=Integer.parseInt(t1.getOrderPrice());
        }
        return sum;
    }

    @GetMapping("/get_customer_order_rel")
    public Map<String,Integer> getCustomerOrderRel(){
        List<Customer> customers = userDetailsRepo.findAll();
        Map<String,Integer> result = new HashMap<>();
        for(Customer t1:customers){
            List<order> t2 = getOrdersCustId(t1.getCustomerId()).getBody();
            result.put(t1.getFirstName(),t2.size());
        }
        return result;
    }

    @GetMapping("/get_orders_product_rel")
    public Map<String,Integer> getOrderProductRel(){
        List<order> orders = orderDetailsRepo.findAll();
        Map<String,Integer> result = new HashMap<>();
        for(order t1:orders){
            if(result.containsKey(t1.getProductName())){
                result.put(t1.getProductName(),result.get(t1.getProductName())+1);
            }
            else{
                result.put(t1.getProductName(),1);
            }
        }
        return result;
    }
}