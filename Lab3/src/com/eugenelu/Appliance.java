package com.eugenelu;

import java.util.Scanner;

public class Appliance {
    String name;
    String model;
    String manufacturer;
    double price;
    double maxPower;

    public Appliance(String name, String model, String manufacturer, double price, double maxPower) {
        this.name = name;
        this.model = model;
        this.manufacturer = manufacturer;
        this.price = price;
        this.maxPower = maxPower;
    }

    public Appliance(Scanner scanner) {
        read(scanner);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(double maxPower) {
        this.maxPower = maxPower;
    }

    public void read(Scanner scanner) {
        var data = scanner.nextLine().split(",");
        if (data.length == 5) {
            this.name = data[0];
            this.model = data[1];
            this.manufacturer = data[2];
            this.price = Double.parseDouble(data[3]);
            this.maxPower = Double.parseDouble(data[4]);
        }
    }

    @Override
    public String toString() {
        return String.format("name: %s, model: %s, manufacturer: %s, price: %f, max power: %f\n",
                name, model, manufacturer, price, maxPower);
    }
}
