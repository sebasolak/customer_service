package com.example.online_shop.dao;

import com.example.online_shop.model.Customer;
import com.example.online_shop.model.Item;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("fakeDataDao")
public class FakeDataDao implements CustomerDao {

    private final Map<UUID, Customer> database;

    public FakeDataDao() {
        database = new HashMap<>();
        Item iphone = new Item("Iphone", 3, 15);
        UUID joeUid = UUID.randomUUID();
        Customer joe = new Customer(joeUid, "Joe", "Jones", iphone);
        database.put(joeUid, joe);

        Item macbook = new Item("MacBook", 2, 25);
        UUID annaUid = UUID.randomUUID();
        Customer anna = new Customer(annaUid, "Anna", "Smith", macbook);
        database.put(annaUid, anna);
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Optional<Customer> selectCustomer(UUID customerId) {
        return Optional.ofNullable(database.get(customerId));
    }

    @Override
    public int insertNewCustomer(UUID customerId, Customer newCustomer) {
        database.put(customerId, newCustomer);
        return 1;
    }

    @Override
    public int updateCustomer(Customer customer) {
        database.put(customer.getCustomerId(), customer);
        return 1;

    }

    @Override
    public int deleteCustomer(UUID customerId) {
        database.remove(customerId);
        return 1;
    }


}
