package com.example.RecipeHub.client.dtos.request;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public class PaymentRequest {
    @Value("${some.key:RecipeHub premium}")
    private String itemName;
    private int time;
    private String timeUnit;
    private BigDecimal price;

    public PaymentRequest(String name, int time, String timeUnit, BigDecimal price) {
        this.itemName = name;
        this.time = time;
        this.timeUnit = timeUnit;
        this.price = price;
    }

    public String getItemName(){
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
