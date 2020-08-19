package com.example.online_shop.dao;

import com.example.online_shop.model.Customer;
import com.example.online_shop.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FakeDataDaoTest {

    private FakeDataDao fakeDataDao;

    @BeforeEach
    void setUp() {
        fakeDataDao = new FakeDataDao();
    }

    @Test
    @DisplayName("should select all customer")
    void shouldSelectAllCustomers() {
        List<Customer> allCustomers = fakeDataDao.selectAllCustomers();

        assertThat(allCustomers).hasSize(2);

        Customer customer = allCustomers.get(0);

        assertThat(customer.getName()).isIn("Joe", "Anna");
        assertThat(customer.getSurname()).isIn("Jones", "Smith");
        assertThat(customer.getItem().length).isEqualTo(1);
        assertThat(customer.getCustomerId()).isInstanceOf(UUID.class);

    }

    @Test
    @DisplayName("should select customer by random uid")
    void shouldSelectCustomerByUid() {
        UUID lisaUid = UUID.randomUUID();
        Customer lisa = new Customer(lisaUid, "Lisa", "Taylor", null);

        fakeDataDao.insertNewCustomer(lisaUid, lisa);

        Optional<Customer> customerOptional = fakeDataDao.selectCustomer(lisaUid);

        assertThat(customerOptional).isPresent();

        assertThat(lisa).isEqualToComparingFieldByField(customerOptional.get());
    }

    @Test
    @DisplayName("should not select customer by random uid")
    void shouldNotSelectCustomerByRandomUid() {
        Optional<Customer> optionalCustomer = fakeDataDao.selectCustomer(UUID.randomUUID());
        assertThat(optionalCustomer.isPresent()).isFalse();
    }

    @Test
    @DisplayName("should insert new customer")
    void shouldInsertNewCustomer() {
        UUID lisaUid = UUID.randomUUID();
        Customer lisa = new Customer(lisaUid, "Lisa", "Taylor", null);

        fakeDataDao.insertNewCustomer(lisaUid, lisa);

        List<Customer> allCustomers = fakeDataDao.selectAllCustomers();

        assertThat(allCustomers).hasSize(3);
        assertThat(fakeDataDao.selectCustomer(lisaUid).get()).isEqualToComparingFieldByField(lisa);
    }

    @Test
    @DisplayName("should update customer")
    void shouldUpdateCustomer() {
        UUID lisaUid = UUID.randomUUID();
        Customer lisa = new Customer(lisaUid, "Lisa", "Taylor", null);

        fakeDataDao.insertNewCustomer(lisaUid, lisa);

        Customer customer = fakeDataDao.selectCustomer(lisaUid).get();

        assertThat(customer).isEqualToComparingFieldByField(lisa);

        Customer kurt = new Customer(lisaUid, "Kurt", "Brown", null);
        fakeDataDao.updateCustomer(kurt);

        customer = fakeDataDao.selectCustomer(lisaUid).get();
        assertThat(customer).isEqualToComparingFieldByField(kurt);
    }

    @Test
    @DisplayName("should delete customer")
    void shouldDeleteCustomer() {

        UUID customerId
                = fakeDataDao.selectAllCustomers().get(0).getCustomerId();

        assertThat(fakeDataDao.selectCustomer(customerId).isPresent()).isTrue();

        fakeDataDao.deleteCustomer(customerId);

        assertThat(fakeDataDao.selectCustomer(customerId).isPresent()).isFalse();
    }
}