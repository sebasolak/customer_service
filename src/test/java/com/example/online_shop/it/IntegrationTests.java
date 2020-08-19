package com.example.online_shop.it;

import com.example.online_shop.clientproxy.CustomerResourceV1;
import com.example.online_shop.model.Customer;
import com.example.online_shop.model.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.ws.rs.NotFoundException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class IntegrationTests {

    @Autowired
    private CustomerResourceV1 customerResourceV1;

    @Test
    @DisplayName("should add new customer")
    void shouldAddNewCustomer() {
        //given
        Item iphone = new Item("Iphone", 3, 15);
        UUID joeUid = UUID.randomUUID();
        Customer joe = new Customer(joeUid, "Joe", "Jones", iphone);

        //when
        customerResourceV1.addNewCustomer(joe);

        //then
        Customer customer = customerResourceV1.getCustomer(joeUid);
        assertThat(customer).isEqualToIgnoringGivenFields(joe, "item");
    }

    @Test
    @DisplayName("should get customer by customer uid")
    void shouldUpdateCustomer() {
        //given
        Item iphone = new Item("Iphone", 3, 15);
        UUID joeUid = UUID.randomUUID();
        Customer joe = new Customer(joeUid, "Joe", "Jones", iphone);

        //when
        customerResourceV1.addNewCustomer(joe);
        Customer updateCustomer = new Customer(joeUid, "Anna", "Smith", iphone);
        customerResourceV1.updateCustomer(updateCustomer);

        //then
        Customer customer = customerResourceV1.getCustomer(joeUid);
        assertThat(customer).isEqualToIgnoringGivenFields(updateCustomer, "item");
    }

    @Test
    @DisplayName("should remove customer")
    void shouldRemoveCustomer() {
        //given
        Item iphone = new Item("Iphone", 3, 15);
        UUID joeUid = UUID.randomUUID();
        Customer joe = new Customer(joeUid, "Joe", "Jones", iphone);

        //when
        customerResourceV1.addNewCustomer(joe);

        //then
        Customer customer = customerResourceV1.getCustomer(joeUid);
        assertThat(customer).isEqualToIgnoringGivenFields(joe, "item");

        //when
        customerResourceV1.removeCustomer(joeUid);

        //then
        assertThat(customerResourceV1.getAllCustomers(null, null)).doesNotContain(joe);
        assertThatThrownBy(() -> customerResourceV1.getCustomer(joeUid))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("should get customer by query param")
    void shouldGetCustomerBySurname() {
        //given
        UUID taylerUid = UUID.randomUUID();
        Item iphone = new Item("Iphone", 3, 15);
        Customer tayler = new Customer(taylerUid, "Tayler", "White", iphone);

        //when
        customerResourceV1.addNewCustomer(tayler);

        //then
        assertThat(customerResourceV1.getAllCustomers("tayler", "white").get(0))
                .isEqualToIgnoringGivenFields(tayler, "item");
        assertThat(customerResourceV1.getAllCustomers("tayler", null).get(0))
                .isEqualToIgnoringGivenFields(tayler, "item");
        assertThat(customerResourceV1.getAllCustomers(null, "white").get(0))
                .isEqualToIgnoringGivenFields(tayler, "item");


    }
}
