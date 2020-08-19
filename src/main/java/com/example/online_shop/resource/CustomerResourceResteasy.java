package com.example.online_shop.resource;

import com.example.online_shop.model.Customer;
import com.example.online_shop.service.CustomerService;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
@Validated
@Component
@Path("api/v1/customers")
public class CustomerResourceResteasy {

    private final CustomerService customerService;

    public CustomerResourceResteasy(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getAllCustomers(@QueryParam("name") String name,
                                          @QueryParam("surname") String surname) {
        return customerService.getAllCustomers(Optional.ofNullable(name), Optional.ofNullable(surname));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{customerId}")
    public Customer getCustomer(@PathParam("customerId") UUID customerId) {
        return customerService.getCustomer(customerId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Customer %s not found", customerId)));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewCustomer(@Valid Customer newCustomer) {
        customerService.addNewCustomer(newCustomer);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateCustomer(@RequestBody Customer customer) {
        customerService.updateCustomer(customer);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{customerId}")
    public void removeCustomer(@PathParam("customerId") UUID customerId) {
        customerService.removeCustomer(customerId);
    }


}
