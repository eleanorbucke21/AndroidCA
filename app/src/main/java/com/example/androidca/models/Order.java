package com.example.androidca.models;

public class Order {
    private int id;
    private int userId;
    private String orderDate;
    private String orderDetails;
    private double totalAmount;
    private String deliveryAddress; // Add this line

    // Getters and setters
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

    public String getDeliveryAddress() { // Add this getter
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) { // Add this setter
        this.deliveryAddress = deliveryAddress;
    }
}
