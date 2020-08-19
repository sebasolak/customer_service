package com.example.online_shop.resource;

import com.example.online_shop.model.Customer;
import com.example.online_shop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

//@RestController
//@RequestMapping("api/v1/customers")
public class CustomerResourceMVC {

    private final CustomerService customerService;

    @Autowired
    public CustomerResourceMVC(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Customer> getAllCustomers(@QueryParam("name") String name,
                                          @QueryParam("surname") String surname) {
        return customerService.getAllCustomers(Optional.ofNullable(name), Optional.ofNullable(surname));
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "{customerId}"
    )
    public Customer getCustomer(@PathVariable("customerId") UUID customerId) {
        return customerService.getCustomer(customerId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Customer %s not found", customerId)));
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void addNewCustomer(@RequestBody Customer newCustomer) {
        customerService.addNewCustomer(newCustomer);
    }

    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void updateCustomer(@RequestBody Customer customer) {
        customerService.updateCustomer(customer);
    }

    @DeleteMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "{customerId}"
    )
    public void removeCustomer(@PathVariable("customerId") UUID customerId) {
        customerService.removeCustomer(customerId);
    }
}
