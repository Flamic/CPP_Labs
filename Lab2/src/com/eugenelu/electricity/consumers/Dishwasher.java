package com.eugenelu.electricity.consumers;

public class Dishwasher extends ElectricalAppliance {
    public Dishwasher() {}
    public Dishwasher(double inputVoltage, double inputAmperage) {
        super(inputVoltage, inputAmperage);
    }

    @Override
    public String getState() {
        return isTurnedOn
                ? "Is turned on (source: " + currentSource.getSourceName() + "): washing."
                : "Is turned off.";
    }

    @Override
    public String toString() {
        return "Appliance: dishwasher. "
                + getState()
                + "\nPower consumption: "
                + getPowerStateInfo();
    }
}
