package com.eugenelu.electricity;

import com.eugenelu.electricity.consumers.ElectricalAppliance;
import com.eugenelu.electricity.sources.Source;

import java.util.Comparator;
import java.util.LinkedList;

public class ElectricityManager {
    public static class PowerAscendingComparator implements Comparator<ElectricalAppliance> {
        @Override
        public int compare(ElectricalAppliance o1, ElectricalAppliance o2) {
            return Double.compare(o1.getPowerConsumption(), o2.getPowerConsumption());
        }
    }

    public class PowerDescendingComparator implements Comparator<ElectricalAppliance> {
        @Override
        public int compare(ElectricalAppliance o1, ElectricalAppliance o2) {
            return Double.compare(o2.getPowerConsumption(), o1.getPowerConsumption());
        }
    }

    public final LinkedList<Source> sources = new LinkedList<>();
    public final LinkedList<ElectricalAppliance> appliances = new LinkedList<>();

    public void sortAscendingPowerAppliances() {
        appliances.sort(new PowerAscendingComparator());
    }

    public void sortDescendingPowerAppliances() {
        appliances.sort(new PowerDescendingComparator());
    }

    public void sortAscendingAmperageAppliances() {
        appliances.sort(new Comparator<ElectricalAppliance>() {
            @Override
            public int compare(ElectricalAppliance o1, ElectricalAppliance o2) {
                return Double.compare(o1.getInputAmperage(), o2.getInputAmperage());
            }
        });
    }

    public void sortDescendingAmperageAppliances() {
        appliances.sort((o1, o2) -> Double.compare(o1.getInputAmperage(), o2.getInputAmperage()));
    }

    public String appliancesToString() {
        int i = 0;
        StringBuilder str = new StringBuilder("[\n");
        for (var appliance : appliances) {
            str.append(String.format(" - #%d %s\n", i, appliance));
            ++i;
        }
        str.append("]");
        return str.toString();
    }

    public String sourcesToString() {
        int i = 0;
        StringBuilder str = new StringBuilder();
        for (var source : sources) {
            str.append(String.format("#%d %s\n", i, source));
            ++i;
        }
        return str.toString();
    }
}
