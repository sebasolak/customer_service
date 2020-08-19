package com.example.online_shop.clientproxy;

import com.example.online_shop.model.Customer;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

public interface CustomerResourceV1 {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Customer> getAllCustomers(@QueryParam("name") String name,
                                   @QueryParam("surname") String surname);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{customerId}")
    Customer getCustomer(@PathParam("customerId") UUID customerId);


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void addNewCustomer(Customer newCustomer);

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void updateCustomer(@RequestBody Customer customer);

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{customerId}")
    void removeCustomer(@PathParam("customerId") UUID customerId);
}
