package com.eugenelu.electricity.consumers;

import com.eugenelu.electricity.sources.Source;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public abstract class ElectricalAppliance {
    protected double inputVoltage;
    protected double inputAmperage;
    protected boolean isTurnedOn;
    protected Source currentSource;

    public ElectricalAppliance() {}

    public ElectricalAppliance(double inputVoltage, double inputAmperage) {
        this.inputVoltage = inputVoltage;
        this.inputAmperage = inputAmperage;
    }

    public double getInputVoltage() {
        return inputVoltage;
    }

    public void setInputVoltage(double inputVoltage) {
        this.inputVoltage = inputVoltage;
    }

    public double getInputAmperage() {
        return inputAmperage;
    }

    public void setInputAmperage(double inputAmperage) {
        this.inputAmperage = inputAmperage;
    }

    public double getPowerConsumption() {
        return isTurnedOn ? inputVoltage * inputAmperage : 0;
    }

    public String getPowerStateInfo() {
        return isTurnedOn
                    ? String.format("%.3fV %.3fA %.0fW⋅h", inputVoltage, inputAmperage, getPowerConsumption())
                    : "0V 0A 0kW⋅h";
    }

    public boolean isTurnedOn() {
        return isTurnedOn;
    }

    public void turnOn(Source source) throws Exception {
        if (!isTurnedOn) {
            isTurnedOn = true;
            source.plugIn(this);
            currentSource = source;
        }
    }

    public void turnOff() {
        if (isTurnedOn) {
            isTurnedOn = false;
            currentSource.unplug(this);
            currentSource = null;
        }
    }

    public void read(@NotNull InputStream inputStream,
                     @NotNull PrintStream printStream) throws Exception {
        Scanner scanner = new Scanner(inputStream);
        printStream.print("Enter input voltage: ");
        try { this.inputVoltage = scanner.nextDouble(); }
        catch (Exception e) { throw new Exception("Incorrect voltage value"); }
        if (this.inputVoltage < 0) throw new Exception("Incorrect voltage value");
        printStream.print("Enter input amperage: ");
        try { this.inputVoltage = scanner.nextDouble(); }
        catch (Exception e) { throw new Exception("Incorrect amperage value"); }
        if (this.inputAmperage < 0) throw new Exception("Incorrect amperage value");
        scanner.nextLine();
    }

    public abstract String getState();

    @Override
    public abstract String toString();
}
