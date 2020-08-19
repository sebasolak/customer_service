package com.example.online_shop.service;

import com.example.online_shop.dao.FakeDataDao;
import com.example.online_shop.model.Customer;
import com.example.online_shop.model.Item;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class CustomerServiceTest {

    @Mock
    private FakeDataDao fakeDataDao;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerService(fakeDataDao);
    }

    @Test
    @DisplayName("should get all customer")
    void shouldGetAllCustomers() {
        Item iphone = new Item("Iphone", 3, 15);
        UUID joeUid = UUID.randomUUID();
        Customer joe = new Customer(joeUid, "Joe", "Jones", iphone);

        List<Customer> customers = Collections.singletonList(joe);

        given(fakeDataDao.selectAllCustomers()).willReturn(customers);

        List<Customer> allCustomers = customerService.getAllCustomers(Optional.empty(), Optional.empty());
        assertThat(allCustomers).hasSize(1);

        assertThat(allCustomers.get(0)).isEqualToComparingFieldByField(joe);
    }

    @Test
    @DisplayName("should get customer by name and surname")
    void shouldGetCustomerByNameAndSurname() {
        UUID joeUid = UUID.randomUUID();
        Customer joe = new Customer(joeUid, "Joe", "Jones", null);

        UUID annaUid = UUID.randomUUID();
        Customer anna = new Customer(annaUid, "Anna", "Jones", null);

        List<Customer> customers = ImmutableList.of(joe, anna);

        given(fakeDataDao.selectAllCustomers()).willReturn(customers);

        List<Customer> customerByNameAndSurname
                = customerService.getAllCustomers(Optional.of("joe"), Optional.of("jones"));
        assertThat(customerByNameAndSurname).hasSize(1);

        Customer customer = customerByNameAndSurname.get(0);
        assertThat(customer).isEqualToComparingFieldByField(joe);
    }

    @Test
    @DisplayName("should get customer by name")
    void shouldGetCustomerByName() {
        UUID joeUid = UUID.randomUUID();
        Customer joe = new Customer(joeUid, "Joe", "Jones", null);

        UUID annaUid = UUID.randomUUID();
        Customer anna = new Customer(annaUid, "Anna", "Jones", null);

        List<Customer> customers = ImmutableList.of(joe, anna);

        given(fakeDataDao.selectAllCustomers()).willReturn(customers);

        List<Customer> customerByNameAndSurname
                = customerService.getAllCustomers(Optional.of("joe"), Optional.empty());
        assertThat(customerByNameAndSurname).hasSize(1);

        Customer customer = customerByNameAndSurname.get(0);
        assertThat(customer).isEqualToComparingFieldByField(joe);

    }

    @Test
    @DisplayName("should get customer by surname")
    void shouldGetCustomerBySurname() {
        UUID joeUid = UUID.randomUUID();
        Customer joe = new Customer(joeUid, "Joe", "Jones", null);

        UUID annaUid = UUID.randomUUID();
        Customer anna = new Customer(annaUid, "Anna", "Jones", null);

        List<Customer> customers = ImmutableList.of(joe, anna);

        given(fakeDataDao.selectAllCustomers()).willReturn(customers);

        List<Customer> customerByNameAndSurname
                = customerService.getAllCustomers(Optional.empty(), Optional.of("jones"));
        assertThat(customerByNameAndSurname).hasSize(2);

        Customer customer = customerByNameAndSurname.get(0);
        assertThat(customer).isIn(joe, anna);
    }

    @Test
    @DisplayName("should get customer by customer uid")
    void shouldGetCustomerByCustomerUid() {
        UUID joeUid = UUID.randomUUID();
        Customer joe = new Customer(joeUid, "Joe", "Jones", null);

        given(fakeDataDao.selectCustomer(joeUid)).willReturn(Optional.of(joe));

        Optional<Customer> customerOptional = customerService.getCustomer(joeUid);
        assertThat(customerOptional.isPresent()).isTrue();
        assertThat(customerOptional.get()).isEqualToComparingFieldByField(joe);
    }

    @Test
    @DisplayName("should add new customer")
    void shouldAddNewCustomer() {
        UUID joeUid = UUID.randomUUID();
        Customer joe = new Customer(joeUid, "Joe", "Jones", null);

        given(fakeDataDao.insertNewCustomer(any(UUID.class), any(Customer.class)))
                .willReturn(1);

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);

        int addResult = customerService.addNewCustomer(joe);

        verify(fakeDataDao).insertNewCustomer(eq(joeUid), captor.capture());

        Customer customer = captor.getValue();

        assertThat(customer).isEqualToComparingFieldByField(joe);

        assertThat(addResult).isOne();
    }

    @Test
    @DisplayName("should update customer")
    void shouldUpdateCustomer() {
        UUID joeUid = UUID.randomUUID();
        Customer joe = new Customer(joeUid, "Joe", "Jones", null);

        given(fakeDataDao.selectCustomer(joeUid))
                .willReturn(Optional.of(joe));
        given(fakeDataDao.updateCustomer(joe)).willReturn(1);

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);

        int updateResult = customerService.updateCustomer(joe);

        verify(fakeDataDao).selectCustomer(joeUid);
        verify(fakeDataDao).updateCustomer(captor.capture());

        Customer customer = captor.getValue();
        assertThat(customer).isEqualToComparingFieldByField(joe);
        assertThat(updateResult).isOne();
    }

    @Test
    @DisplayName("should remove customer")
    void shouldRemoveCustomer() {
        UUID joeUid = UUID.randomUUID();
        Customer joe = new Customer(joeUid, "Joe", "Jones", null);

        given(fakeDataDao.selectCustomer(joeUid))
                .willReturn(Optional.of(joe));
        given(fakeDataDao.deleteCustomer(joeUid))
                .willReturn(1);

        int removeResult = customerService.removeCustomer(joeUid);

        verify(fakeDataDao).selectCustomer(joeUid);
        verify(fakeDataDao).deleteCustomer(joeUid);

        assertThat(removeResult).isOne();
    }
}