package net.exec64.cc_powered.energy;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.energy.EnergyStorage;

public class CCEnergyStorage extends EnergyStorage {
    public CCEnergyStorage(EnergyProfile profile) {
        super(profile.capacity, profile.maxReceive, profile.consumption, 0);
    }

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Energy", this.energy);
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        this.energy = Math.min(tag.getInt("Energy"), this.capacity);
    }
}
