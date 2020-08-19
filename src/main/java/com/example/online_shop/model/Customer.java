package com.example.online_shop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {

    private UUID customerId;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    private Item[] item;
    private Map<String, Double> itemsMap;

    public Customer(@JsonProperty UUID customerUid,
                    @JsonProperty String name,
                    @JsonProperty String surname,
                    @JsonProperty Item... item) {
        this.customerId = customerUid;
        this.name = name;
        this.surname = surname;
        this.item = item;
        itemsMap = new HashMap<>();
    }

    public Customer() {
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Item[] getItem() {
        return item;
    }

    public Map<String, Double> getItemsMap() {
        for (Item item1 : item) {
            itemsMap.put(item1.getItemName(), item1.getPrice() * item1.getAmount());
        }
        return itemsMap;
    }

    public double getTotalPrice() {
        return getItemsMap().values().stream()
                .mapToDouble(Double::intValue)
                .sum();
    }

    public static Customer newCustomer(UUID customerId, Customer customer) {
        return new Customer(customerId, customer.name, customer.surname, customer.item);
    }
}
