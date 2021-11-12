package com.eugenelu;

import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.eugenelu.StringTools.colorize;
import static com.eugenelu.StringTools.TEXT_COLOR;

public class Main {
    public final static int COMMANDS_COUNT = 14;

    public final static Object[] COMMANDS_NUM = Stream.concat(
        Stream.of(
            colorize("Commands", TEXT_COLOR.YELLOW)),
            IntStream
                .range(0, COMMANDS_COUNT)
                .mapToObj(i -> colorize(String.valueOf(i), TEXT_COLOR.YELLOW))
        ).toArray();

    public final static String HELP = String.format("""
                ----- %s -----
                %s\t- close
                %s\t- sort orders by date
                %s\t- address list of customers which ordered 3+ pizzas
                %s\t- count of customers which ordered some pizza
                %s\t- maximum count of pizzas in order
                %s\t- pizzas and their customers
                %s\t- get list of uncompleted orders including timeout
                %s\t- open
                %s\t- save
                %s\t- add pizza to menu
                %s\t- add new client
                %s\t- show pizzeria menu and clients
                %s\t- complete order
                %s\t- help (show all commands)
                --------------------
                """, COMMANDS_NUM);

    public static void main(String[] args) {
        Pizzeria p = new Pizzeria();
        Scanner sc = new Scanner(System.in);

        System.out.println(HELP);
        while (true) {
            System.out.print(colorize("> ", TEXT_COLOR.BLUE));
            String com = sc.nextLine();
            if (com.toLowerCase().equals("help")) {
                System.out.println(HELP);
                continue;
            }
            try {
                switch (Integer.parseInt(com)) {
                    case 0:
                        System.out.println(colorize("Goodbye ^w^", TEXT_COLOR.YELLOW));
                        return;
                    case 1:
                        System.out.println(p.getOrdersSortedByExpectedTime());
                        break;
                    case 2:
                        System.out.println(p.getClientsThatOrderedMoreThan(2));
                        break;
                    case 3:
                        System.out.print("Enter pizza name to filter: ");
                        System.out.println(p.getClientsCountThatOrdered(sc.nextLine()));
                        break;
                    case 4:
                        System.out.println(p.getMostOrderedPizza());
                        break;
                    case 5:
                        System.out.println(p.getPizzaAndTheirClients());
                        break;
                    case 6:
                        System.out.println(colorize(p.getOverdueOrders(), TEXT_COLOR.RED));
                        break;
                    case 7:
                        p.load();
                        System.out.println(colorize("Data was successfully loaded!", TEXT_COLOR.GREEN));
                        break;
                    case 8:
                        p.save();
                        System.out.println(colorize("Data was successfully saved!", TEXT_COLOR.GREEN));
                        break;
                    case 9:
                        p.addPizza(sc);
                        break;
                    case 10:
                        p.addClient(sc);
                        break;
                    case 11:
                        System.out.println(p);
                        break;
                    case 12:
                        System.out.print("Client's phone: ");
                        var client = p.getClientByPhone(sc.nextLine());
                        System.out.print("Order index (1..n): ");
                        client.completeOrder(Integer.parseInt(sc.nextLine()));
                        System.out.println(colorize("Order was marked as completed", TEXT_COLOR.GREEN));
                        break;
                    case 13:
                        System.out.println(HELP);
                        break;
                    default:
                        System.out.println(colorize("Unknown command", TEXT_COLOR.RED));
                }
            }
            catch (NumberFormatException e) {
                System.out.println(colorize("Invalid number value", TEXT_COLOR.RED));
            }
            catch (Exception e) {
                System.out.println(colorize(e.toString(), TEXT_COLOR.RED));
            }
        }
    }
}
