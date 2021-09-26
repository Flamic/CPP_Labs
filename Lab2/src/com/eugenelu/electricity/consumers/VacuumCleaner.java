package com.eugenelu.electricity.consumers;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class VacuumCleaner extends ElectricalAppliance {
    private float capacity;

    public VacuumCleaner() {}
    public VacuumCleaner(double inputVoltage, double inputAmperage, float capacity) {
        super(inputVoltage, inputAmperage);
        this.capacity = capacity;
    }

    @Override
    public void read(@NotNull InputStream inputStream,
                     @NotNull PrintStream printStream) throws Exception {
        super.read(inputStream, printStream);
        Scanner scanner = new Scanner(inputStream);
        printStream.println("Enter capacity (liters): ");
        try { this.capacity = scanner.nextFloat(); }
        catch (Exception e) { throw new Exception("Incorrect capacity value"); }
        if (capacity < 0) throw new Exception("Incorrect capacity value");
        scanner.nextLine();
    }

    @Override
    public String getState() {
        return isTurnedOn
                ? "Is turned on (source: " + currentSource.getSourceName() + "): cleaning."
                : "Is turned off.";
    }

    @Override
    public String toString() {
        return "Appliance: vacuum cleaner " + String.format("(capacity: %.2f). ", capacity)
                + getState()
                + "\nPower consumption: "
                + getPowerStateInfo();
    }
}
