package com.eugenelu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class Order {
    public static class OrderItem {
        String pizzaName;
        int count;

        public OrderItem() { }

        public OrderItem(String pizzaName, int count) {
            this.pizzaName = pizzaName;
            this.count = count;
        }

        public String getPizzaName() {
            return pizzaName;
        }

        public void setPizzaName(String pizzaName) {
            this.pizzaName = pizzaName;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return String.format("\n pizza: %s\n count: %d\n", pizzaName, count);
        }
    }

    final transient SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    LinkedList<OrderItem> pizzas = new LinkedList<>();
    long expectedTime;
    boolean completed = false;

    public Order() { }

    public Order(LinkedList<OrderItem> pizzas) {
        this.pizzas = pizzas;
    }

    public LinkedList<OrderItem> getPizzas() {
        return pizzas;
    }

    public void setPizzas(LinkedList<OrderItem> pizzas) {
        this.pizzas = pizzas;
    }

    public long getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(long expectedTime) {
        this.expectedTime = expectedTime;
    }

    public void setExpectedTime(String expectedTime) throws Exception {
        try {
            this.expectedTime = dateFormatter.parse(expectedTime).getTime();
        }
        catch (Exception e) {
            throw new Exception("Wrong date format");
        }
    }

    public void clear() {
        pizzas.clear();
    }

    public void add(OrderItem item) {
        pizzas.add(item);
    }

    public void add(String pizzaName, int count) {
        pizzas.add(new OrderItem(pizzaName, count));
    }

    public boolean isDelayed() {
        return !completed && System.currentTimeMillis() - expectedTime > 0;
    }

    public String getDelay() {
        return dateFormatter.format(new Date(System.currentTimeMillis() - expectedTime));
    }

    public void complete() {
        completed = true;
    }

    @Override
    public String toString() {
        return String.format("\nexpected time: %s\ncompleted: %s\npizzas:\n%s\n",
                dateFormatter.format(new Date(expectedTime)), completed ? "yes" : "no", pizzas);
    }
}
