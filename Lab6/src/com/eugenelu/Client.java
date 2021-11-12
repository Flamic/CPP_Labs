package com.eugenelu;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Client {
    String phoneNumber;
    String address;
    LinkedList<Order> orders = new LinkedList<>();

    public Client() { }

    public Client(String phoneNumber, String address) {
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Client(String phoneNumber, String address, LinkedList<Order> orders) {
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.orders = orders;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LinkedList<Order> getOrders() {
        return orders;
    }

    public void setOrders(LinkedList<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void completeOrder(int index) {
        try {
            orders.get(index - 1).complete();
        }
        catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException(String.format("no order with index '%d'", index));
        }
    }

    @Override
    public String toString() {
        return String.format("\nphone number: %s\naddress: %s\norders:\n%s\n",
                phoneNumber, address, orders);
    }
}
