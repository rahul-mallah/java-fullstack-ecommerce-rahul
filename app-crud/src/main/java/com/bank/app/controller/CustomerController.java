package com.bank.app.controller;

import com.bank.app.model.Customer;
import com.bank.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
        customerService.createCustomer(customer);
        return new ResponseEntity<>("Customer Added Successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{Id}",  method = RequestMethod.GET)
    public ResponseEntity<Customer> getCustomer(@PathVariable Long Id) {
        Customer customer = customerService.getCustomerById(Id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @RequestMapping(value = "/{Id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer, @PathVariable Long Id) {
        try {
            Customer updatedCustomer = customerService.updateCustomer(customer, Id);
            return new ResponseEntity<>("Update Customer with Id : " + Id, HttpStatus.OK);
        }
        catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }

    @RequestMapping(value = "/{Id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCustomer(@PathVariable Long Id) {
        try {
            String message = customerService.deleteCustomer(Id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }
}
