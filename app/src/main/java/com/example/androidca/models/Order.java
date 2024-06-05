package com.example.androidca.models;

public class Order {
    private int id;
    private int userId;
    private String orderDate;
    private String orderDetails;
    private double totalAmount;
    private String deliveryAddress;

    public Order() {}

    public Order(int id, int userId, String orderDate, String orderDetails, double totalAmount, String deliveryAddress) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.orderDetails = orderDetails;
        this.totalAmount = totalAmount;
        this.deliveryAddress = deliveryAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
