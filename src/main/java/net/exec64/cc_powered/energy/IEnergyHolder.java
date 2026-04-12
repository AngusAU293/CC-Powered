package net.exec64.cc_powered.energy;

import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

public interface IEnergyHolder {
    ComputerEnergyStorage getEnergyStorage();
    LazyOptional<IEnergyStorage> getEnergyCapability();
    EnergyProfile getEnergyProfile();

    default void invalidateEnergyCapability() {
        getEnergyCapability().invalidate();
    }
}
