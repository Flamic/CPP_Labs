package com.eugenelu;

import java.util.Arrays;
import java.util.LinkedList;

public class Pizza {
    private String name;
    private int weight;
    private double price;
    private LinkedList<String> ingredients = new LinkedList<>();

    public Pizza() {
        this.name = "<unknown>";
    }

    public Pizza(String name, int weight, double price, LinkedList<String> ingredients) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.ingredients = ingredients;
    }

    public Pizza(String name, int weight, double price, String ingredients) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        addIngredients(ingredients);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LinkedList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(LinkedList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(String ingredient) {
        ingredients.add(ingredient);
    }

    public void addIngredients(String ingredients) {
        this.ingredients.addAll(Arrays.stream(ingredients.split(", ")).toList());
    }

    @Override
    public String toString() {
        return String.format("\npizza name: %s\nweight: %d\nprice: %f\ningredients: %s\n",
                name, weight, price, ingredients);
    }
}
