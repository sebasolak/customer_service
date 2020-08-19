package com.example.online_shop.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {

    private String itemName;
    private int amount;
    private int price;


    public Item(@JsonProperty String itemName,
                @JsonProperty int amount,
                @JsonProperty int price) {
        this.itemName = itemName;
        this.amount = amount;
        this.price = price;
    }

    public Item() {
    }

    public String getItemName() {
        return itemName;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }
}
