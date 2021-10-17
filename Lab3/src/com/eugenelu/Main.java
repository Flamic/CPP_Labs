package com.eugenelu;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    final static File file1 = new File("data\\data1.txt");
    final static File file2 = new File("data\\data2.txt");

    public static void main(String[] args) {
        LinkedList<Appliance> appliances1 = getAppliances(file1);
        LinkedList<Appliance> appliances2 = getAppliances(file2);

        if (appliances1 == null || appliances2 == null) {
            System.out.println("Files are corrupted. Program is terminated");
            return;
        }

        System.out.println("All appliances:");
        System.out.println(appliances1);
        System.out.println("===================");

        var map = appliances1
                .stream()
                .collect(Collectors.groupingBy(
                        Appliance::getManufacturer,
                        Collectors.mapping(Appliance::getModel, Collectors.toList())
                ));

        Scanner input = new Scanner(System.in);
        System.out.print("Enter count of entries: ");
        int count = Integer.parseInt(input.nextLine());
        System.out.println(map
                .entrySet()
                .stream()
                .map(
                        entry -> new AbstractMap.SimpleEntry<>(
                                entry.getKey(), entry.getValue().stream().limit(count).toList()
                        )
                )
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)));

        System.out.println("Name frequency:");
        System.out.println(
                appliances1.stream().collect(Collectors.groupingBy(Appliance::getName, Collectors.counting()))
        );

        var mergedList = Collections.unmodifiableList(
                Stream
                .concat(appliances1.stream(), appliances2.stream())
                .sorted((o1, o2) -> Double.compare(o2.price, o1.price))
                .toList()
        );

        System.out.println("Merged list:");
        System.out.println(mergedList);
        System.out.print("Appliances total count: ");
        System.out.println(mergedList.stream().count());
        System.out.print("Appliances total price: ");
        System.out.println(mergedList.stream().mapToDouble(o -> o.price).sum());
    }

    private static LinkedList<Appliance> getAppliances(File file) {
        LinkedList<Appliance> appliances = new LinkedList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine())
                appliances.add(new Appliance(scanner));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return appliances;
    }
}
