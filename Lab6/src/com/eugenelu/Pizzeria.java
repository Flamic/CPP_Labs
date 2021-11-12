package com.eugenelu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Pizzeria {
    public final static transient String FILE_PATH = "data\\Pizzeria.json";

    LinkedList<Pizza> pizzas = new LinkedList<>();
    LinkedList<Client> clients = new LinkedList<>();

    public void add(Pizza pizza) {
        pizzas.add(pizza);
    }

    public void add(Client client) {
        clients.add(client);
    }

    public void addPizza(Scanner sc) {
        Pizza p = new Pizza();
        System.out.print("Pizza name: ");
        p.setName(sc.nextLine());
        System.out.print("Pizza weight (g): ");
        p.setWeight(Integer.parseInt(sc.nextLine()));
        System.out.print("Pizza price (UAH): ");
        p.setPrice(Double.parseDouble(sc.nextLine()));
        System.out.print("Ingredients: ");
        p.addIngredients(sc.nextLine());
        pizzas.add(p);
    }

    public void addClient(Scanner sc) throws Exception {
        Client c = new Client();

        System.out.print("Client phone number: ");
        c.setPhoneNumber(sc.nextLine());
        System.out.print("Client address: ");
        c.setAddress(sc.nextLine());

        System.out.println("Orders:");
        while (true) {
            System.out.println("Add order? (y/n)");
            if (!sc.nextLine().equals("y")) break;

            Order o = new Order();

            System.out.print("Expected time: ");
            o.setExpectedTime(sc.nextLine());
            System.out.println("Menu:\n" + pizzas);
            while (true) {
                System.out.println("Add item? (y/n)");
                if (!sc.nextLine().equals("y")) break;
                Order.OrderItem item = new Order.OrderItem();
                System.out.print("Pizza name: ");
                while (true) {
                    String pizzaName = sc.nextLine();
                    if (pizzaName.equals("")) return;
                    if (pizzas.stream().anyMatch(p -> p.getName().equals(pizzaName))) {
                        item.setPizzaName(pizzaName);
                        break;
                    }
                        else System.out.println("Incorrect pizza name!");
                }
                System.out.print("Count: ");
                item.setCount(Integer.parseInt(sc.nextLine()));
                o.add(item);
            }
            c.addOrder(o);
        }
        clients.add(c);
    }

    public void save() throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Files.writeString(
                Path.of(FILE_PATH),
                gson.toJson(this),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE
        );
    }

    public void load() throws Exception {
        Gson gson = new Gson();
        Pizzeria p = gson.fromJson(Files.readString(Path.of(FILE_PATH), StandardCharsets.UTF_8), Pizzeria.class);
        this.pizzas = p.pizzas;
        this.clients = p.clients;
    }

    public String getOrdersSortedByExpectedTime() {
        return String.format("orders: %s",
                clients
                        .stream()
                        .map(entry -> new AbstractMap.SimpleEntry<>(
                                "\n" + entry.phoneNumber, entry.orders
                        ))
                        .flatMap(entry ->
                                entry.getValue()
                                    .stream()
                                    .map(order -> new AbstractMap.SimpleEntry<>(entry.getKey(), order)))
                        .sorted(Comparator.comparingLong(m -> m.getValue().expectedTime)).toList()
        );
    }

    public String getClientsThatOrderedMoreThan(int count) {
        return String.format("addresses: %s",
            clients
                    .stream()
                    .filter(
                            c -> c.orders.stream().anyMatch(
                                    o -> o.pizzas.stream().mapToInt(i -> i.count).sum() > count
                            )
                    )
                    .map(c -> c.address)
                    .toList()
        );
    }

    public String getClientsCountThatOrdered(String name) {
        return String.format("clients count: %d",
                clients
                        .stream()
                        .filter(c -> c.orders.stream().flatMap(
                                o -> o.pizzas.stream()
                            ).anyMatch(p -> p.pizzaName.equals(name)))
                        .count());
    }

    public String getMostOrderedPizza() {
        return clients
                    .stream()
                    .map(c -> new AbstractMap.SimpleEntry<>(
                            "\n[phone: " + c.phoneNumber + ", address: " + c.address + "]",
                            c
                                    .orders
                                    .stream()
                                    .flatMap(o -> o.pizzas.stream())
                                    .collect(
                                            Collectors.groupingBy(
                                                    Order.OrderItem::getPizzaName,
                                                    Collectors.summingInt(Order.OrderItem::getCount)))
                                    .entrySet()
                                    .stream()
                                    .max((e1, e2) -> {
                                        if (e1.getValue() < e2.getValue()) return -1;
                                        else if (e1.getValue().equals(e2.getValue())) return 0;
                                        else return 1;
                                    })
                                    .orElse(new AbstractMap.SimpleEntry<>("<none>", 0))
                                    .getKey()
                    ))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                .toString();
    }

    public String getPizzaAndTheirClients() {
        var tmp = clients
                .stream()
                .map(c -> new AbstractMap.SimpleEntry<>(
                        c.phoneNumber,
                        c.orders.stream().flatMap(o -> o.pizzas.stream()).toList()
                ))
                .toList();
        return pizzas
                .stream()
                .collect(Collectors.toMap(
                        Pizza::getName,
                        p -> tmp
                                .stream()
                                .filter(c -> c
                                        .getValue()
                                        .stream()
                                        .anyMatch(i -> i.pizzaName.equals(p.getName()))
                                )
                                .map(AbstractMap.SimpleEntry::getKey)
                                .toList()
                        )
                )
                .toString();
    }

    public String getOverdueOrders() {
        var now = new Date().getTime();
        return clients
                .stream()
                .collect(Collectors.toMap(
                        c -> "\n" + c.getPhoneNumber(),
                        c -> c
                                .orders
                                .stream()
                                .filter(
                                        o -> !o.completed && now > o.expectedTime
                                )
                                .map(o -> {
                                    var diff = now - o.expectedTime;
                                    return new AbstractMap.SimpleEntry<>(
                                            o,
                                            String.format(
                                                    "overdue time: %d:%02d:%02d\n",
                                                    TimeUnit.MILLISECONDS.toHours(diff),
                                                    TimeUnit.MILLISECONDS.toMinutes(diff) % 60,
                                                    TimeUnit.MILLISECONDS.toSeconds(diff) % 60
                                            )
                                    );
                                })
                                .toList()
                ))
                .entrySet()
                .stream()
                .filter(m -> !m.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                .toString();
    }

    public Client getClientByPhone(String phone) {
        try {
            return clients.stream().filter(c -> c.phoneNumber.equals(phone)).toList().get(0);
        }
        catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException(String.format("no client with phone '%s'", phone));
        }
    }

    @Override
    public String toString() {
        return String.format("""
                ====== Menu ======
                %s
                ==================

                ===== Clients =====
                %s
                ===================
                """, pizzas, clients);
    }
}
