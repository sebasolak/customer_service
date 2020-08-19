package com.example.online_shop.dao;

import com.example.online_shop.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerDao {
    List<Customer> selectAllCustomers();

    Optional<Customer> selectCustomer(UUID customerId);

    int insertNewCustomer(UUID customerId, Customer newCustomer);

    int updateCustomer(Customer customer);

    int deleteCustomer(UUID customerId);

}
