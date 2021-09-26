package com.eugenelu.electricity.consumers;

public class ComputerPowerSupply extends ElectricalAppliance {
    public ComputerPowerSupply() {}
    public ComputerPowerSupply(double inputVoltage, double inputAmperage) {
        super(inputVoltage, inputAmperage);
    }

    @Override
    public String getState() {
        return isTurnedOn ? "Is turned on: computer is running." : "Is turned off.";
    }

    @Override
    public String toString() {
        return "Appliance: desktop computer. "
                + getState()
                + "\nPower consumption: "
                + getPowerStateInfo();
    }
}
