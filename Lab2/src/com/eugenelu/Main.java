package com.eugenelu;

import com.eugenelu.electricity.ElectricityManager;
import com.eugenelu.electricity.consumers.*;
import com.eugenelu.electricity.sources.Source;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ElectricityManager manager = new ElectricityManager();
        manager.appliances.add(new Kettle(220, 7, true));
        manager.appliances.add(new ComputerPowerSupply(220, 2.4));
        manager.appliances.add(new Kettle(220, 5.8, false));
        manager.appliances.add(new VacuumCleaner(220, 6.7, 8));
        manager.appliances.add(new Dishwasher(220, 5.2));
        manager.appliances.add(new ComputerPowerSupply(220, 3.1));
        manager.sources.add(new Source("Socket 1", 220, 50, 7));

        while (true) {
            System.out.print("command> ");
            try {
                switch (scanner.nextLine()) {
                    case "close": return;
                    case "appliances":
                        System.out.println(manager.appliancesToString());
                        break;
                    case "sources":
                        System.out.println(manager.sourcesToString());
                        break;
                    case "create source": {
                        var source = new Source();
                        source.read(System.in, System.out);
                        manager.sources.add(source);
                        break;
                    }
                    case "create": {
                        ElectricalAppliance appliance;
                        System.out.print("Enter appliance name: ");
                        switch (scanner.nextLine()) {
                            case "create ComputerPowerSupply" -> appliance = new ComputerPowerSupply();
                            case "create Dishwasher" -> appliance = new Dishwasher();
                            case "create Kettle" -> appliance = new Kettle();
                            case "create VacuumCleaner" -> appliance = new VacuumCleaner();
                            default -> {
                                System.out.println("Unknown appliance name");
                                continue;
                            }
                        }
                        appliance.read(System.in, System.out);
                        manager.appliances.add(appliance);
                        break;
                    }
                    case "remove source": {
                        System.out.print("Enter index of source: ");
                        int index = scanner.nextInt();
                        if (index < 0 || index >= manager.sources.size())
                            throw new Exception("Incorrect index of source");
                        var source = manager.sources.get(index);
                        source.unplugAll();
                        manager.sources.remove(source);
                        scanner.nextLine();
                        break;
                    }
                    case "remove appliance": {
                        System.out.print("Enter index of appliance: ");
                        int index = scanner.nextInt();
                        if (index < 0 || index >= manager.appliances.size())
                            throw new Exception("Incorrect index of appliance");
                        var appliance = manager.appliances.get(index);
                        appliance.turnOff();
                        manager.appliances.remove(appliance);
                        scanner.nextLine();
                        break;
                    }
                    case "plug in": {
                        System.out.print("Enter index of appliance: ");
                        int applianceIndex = scanner.nextInt();
                        if (applianceIndex < 0 || applianceIndex >= manager.appliances.size())
                            throw new Exception("Incorrect index of appliance");

                        System.out.print("Enter index of source: ");
                        int sourceIndex = scanner.nextInt();
                        if (sourceIndex < 0 || sourceIndex >= manager.sources.size())
                            throw new Exception("Incorrect index of source");

                        var appliance = manager.appliances.get(applianceIndex);
                        var source = manager.sources.get(sourceIndex);
                        appliance.turnOn(source);
                        scanner.nextLine();
                        break;
                    }
                    case "unplug": {
                        System.out.print("Enter index of appliance: ");
                        int applianceIndex = scanner.nextInt();
                        if (applianceIndex < 0 || applianceIndex >= manager.appliances.size())
                            throw new Exception("Incorrect index of appliance");
                        manager.appliances.get(applianceIndex).turnOff();
                        scanner.nextLine();
                        break;
                    }
                    case "sort by power asc":
                        manager.sortAscendingPowerAppliances();
                        break;
                    case "sort by power desc":
                        manager.sortDescendingPowerAppliances();
                        break;
                    case "sort by amperage asc":
                        manager.sortAscendingAmperageAppliances();
                        break;
                    case "sort by amperage desc":
                        manager.sortDescendingAmperageAppliances();
                        break;
                    default:
                        System.out.println("Unknown command");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
