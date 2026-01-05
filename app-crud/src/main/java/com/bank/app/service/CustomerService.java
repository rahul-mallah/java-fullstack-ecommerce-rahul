package com.bank.app.service;

import com.bank.app.model.Customer;

import java.util.List;

public interface CustomerService {
    void createCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomerById(long id);
    Customer updateCustomer(Customer customer, long id);
    String deleteCustomer(long id);
}
