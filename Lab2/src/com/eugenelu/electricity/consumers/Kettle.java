package com.eugenelu.electricity.consumers;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Kettle extends ElectricalAppliance {
    private boolean hasLights;

    public Kettle() {}
    public Kettle(double inputVoltage, double inputAmperage, boolean hasLights) {
        super(inputVoltage, inputAmperage);
        this.hasLights = hasLights;
    }

    @Override
    public void read(@NotNull InputStream inputStream,
                     @NotNull PrintStream printStream) throws Exception {
        super.read(inputStream, printStream);
        Scanner scanner = new Scanner(inputStream);
        printStream.println("Has lights: ");
        try { this.hasLights = scanner.nextBoolean(); }
        catch (Exception e) { throw new Exception("Incorrect value for lights"); }
        scanner.nextLine();
    }

    @Override
    public String getState() {
        return isTurnedOn
                ? "Is turned on (source: " + currentSource.getSourceName() + "): boiling water."
                : "Is turned off.";
    }

    @Override
    public String toString() {
        return (hasLights ? "Appliance: electrical kettle with lights. " : "Appliance: electrical kettle. ")
                + getState()
                + "\nPower consumption: "
                + getPowerStateInfo();
    }
}
