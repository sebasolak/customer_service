package com.example.online_shop.service;

import com.example.online_shop.dao.CustomerDao;
import com.example.online_shop.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerService(@Qualifier("fakeDataDao") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers(Optional<String> name, Optional<String> surname) {
        if (name.isPresent() && surname.isPresent()) {
            return customerDao.selectAllCustomers().stream()
                    .filter(customer -> customer.getName().equalsIgnoreCase(name.get()) &&
                            customer.getSurname().equalsIgnoreCase(surname.get()))
                    .collect(Collectors.toList());
        } else if (name.isPresent()) {
            return customerDao.selectAllCustomers().stream()
                    .filter(customer -> customer.getName().equalsIgnoreCase(name.get()))
                    .collect(Collectors.toList());
        } else if (surname.isPresent()) {
            return customerDao.selectAllCustomers().stream()
                    .filter(customer -> customer.getSurname().equalsIgnoreCase(surname.get()))
                    .collect(Collectors.toList());
        }
        return customerDao.selectAllCustomers();
    }

    public Optional<Customer> getCustomer(UUID customerId) {
        return customerDao.selectCustomer(customerId);
    }

    public int addNewCustomer(Customer newCustomer) {
        UUID customerId = newCustomer.getCustomerId() == null ?
                UUID.randomUUID() : newCustomer.getCustomerId();

        return customerDao.insertNewCustomer(customerId,
                Customer.newCustomer(customerId, newCustomer));
    }

    public int updateCustomer(Customer customer) {
        Optional<Customer> customerOptional
                = getCustomer(customer.getCustomerId());
        if (customerOptional.isPresent()) {
            return customerDao.updateCustomer(customer);
        }
        throw new NoSuchElementException(
                String.format("Customer %s not found", customer.getCustomerId()));
    }

    public int removeCustomer(UUID customerId) {
        Optional<Customer> customerOptional
                = getCustomer(customerId);
        if (customerOptional.isPresent()) {
            return customerDao.deleteCustomer(customerId);
        }
        throw new NoSuchElementException(
                String.format("Customer %s not found", customerId));
    }

}























