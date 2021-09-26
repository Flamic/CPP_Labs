package com.eugenelu.electricity.sources;

import com.eugenelu.electricity.consumers.ElectricalAppliance;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Scanner;

public class Source {
    private String sourceName;
    private double outputVoltage;
    private double outputAmperage;
    private double consumedAmperage;
    private final LinkedList<ElectricalAppliance> slots = new LinkedList<>();
    private int slotsCount;

    public Source() {
        this.sourceName = "Source #" + hashCode();
    }

    public Source(String sourceName,
                  double outputVoltage,
                  double outputAmperage,
                  int slotsCount) {
        this.sourceName = sourceName == null ? "Source #" + hashCode() : sourceName;
        this.outputVoltage = outputVoltage;
        this.outputAmperage = outputAmperage;
        this.slotsCount = slotsCount;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName == null ? "Source #" + hashCode() : sourceName;
    }

    public double getOutputVoltage() {
        return outputVoltage;
    }

    public void setOutputVoltage(double outputVoltage) {
        slots.clear();
        this.outputVoltage = outputVoltage;
    }

    public double getOutputAmperage() {
        return outputAmperage;
    }

    public void setOutputAmperage(double outputAmperage) {
        this.outputAmperage = outputAmperage;
    }

    public int getSlotsCount() {
        return slotsCount;
    }

    public void setSlotsCount(int slotsCount) {
        this.slotsCount = slotsCount;
    }

    public int getUsedSlotsCount() {
        return slots.size();
    }

    public double getPowerValue() {
        return outputVoltage * outputAmperage;
    }

    public void plugIn(ElectricalAppliance appliance) throws Exception {
        if (slots.size() == slotsCount)
            throw new Exception("Source exception: all slots are used");
        if (consumedAmperage + appliance.getInputAmperage() > outputAmperage)
            throw new Exception("Source exception: source is overloaded");
        if (outputVoltage != appliance.getInputVoltage())
            throw new Exception("Source exception: voltage mismatch");
        slots.add(appliance);
        if (!appliance.isTurnedOn()) appliance.turnOn(this);
        consumedAmperage += appliance.getInputAmperage();
    }

    public void unplug(int index) {
        if (slots.size() > index) {
            unplug(slots.get(index));
        }
    }

    public void unplug(ElectricalAppliance appliance) {
        if (slots.remove(appliance)) {
            if (appliance.isTurnedOn()) appliance.turnOff();
            consumedAmperage -= appliance.getInputAmperage();
            slots.remove(appliance);
        }
    }

    public void unplugAll() {
        for (ElectricalAppliance appliance : slots) {
            appliance.turnOff();
        }
        slots.clear();
        consumedAmperage = 0;
    }

    public String getSourceLoadInfo() {
        int load = (int)Math.round(20 * consumedAmperage / outputAmperage);
        return "Source load: |"
                + "#".repeat(load)
                + "-".repeat(20 - load)
                + String.format("| %.1f%% (%.0f/%.0fW⋅h)",
                    100 * consumedAmperage / outputAmperage,
                    consumedAmperage * outputVoltage,
                    outputAmperage * outputVoltage);
    }

    public String getSlotsInfo() {
        int i = 0;
        StringBuilder str = new StringBuilder("[\n");
        for (var slot : slots) {
            str.append(String.format(" #%d %s\n", i, slot));
            ++i;
        }
        str.append("]");
        return str.toString();
    }

    public void read(@NotNull InputStream inputStream,
                     @NotNull PrintStream printStream) throws Exception {
        Scanner scanner = new Scanner(inputStream);

        printStream.print("Enter source name: ");
        try { this.sourceName = scanner.nextLine(); }
        catch (Exception e) { throw new Exception("Incorrect source name"); }
        if (this.sourceName == null || this.sourceName.equals(""))
            throw new Exception("Incorrect source name");

        printStream.print("Enter output voltage: ");
        try { this.outputVoltage = scanner.nextDouble(); }
        catch (Exception e) { throw new Exception("Incorrect voltage value"); }
        if (this.outputVoltage < 0) throw new Exception("Incorrect voltage value");

        printStream.print("Enter input amperage: ");
        try { this.outputAmperage = scanner.nextDouble(); }
        catch (Exception e) { throw new Exception("Incorrect amperage value"); }
        if (this.outputAmperage < 0) throw new Exception("Incorrect amperage value");

        printStream.print("Enter slots count: ");
        try { this.slotsCount = scanner.nextInt(); }
        catch (Exception e) { throw new Exception("Incorrect slots count value"); }
        if (this.slotsCount < 0) throw new Exception("Incorrect slots count value");

        scanner.nextLine();
    }

    @Override
    public String toString() {
        return String.format("Source '%s': %.3fV, %.3f/%.3fA, %d/%d slot(s)\n",
                sourceName,
                outputVoltage,
                consumedAmperage,
                outputAmperage,
                getUsedSlotsCount(),
                slotsCount)
                + getSourceLoadInfo()
                + "\n"
                + getSlotsInfo();
    }
}
