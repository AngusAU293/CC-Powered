package net.exec64.cc_powered.energy;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemEnergyCapabilityProvider implements ICapabilityProvider {
    private final LazyOptional<IEnergyStorage> optional;

    public ItemEnergyCapabilityProvider(ItemStack stack, EnergyProfile profile) {
        this.optional = LazyOptional.of(() -> new IEnergyStorage() {
            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                int stored = getEnergyStored();
                int capacity = getMaxEnergyStored();
                int accepted = Math.min(profile.maxReceive, capacity - stored);

                if (!simulate) stack.getOrCreateTag().putInt("Energy", stored + accepted);

                return accepted;
            }

            @Override
            public int extractEnergy(int maxExtract, boolean simulate) {
                int stored = getEnergyStored();
                int extracted = Math.min(maxExtract, stored);

                if (!simulate) stack.getOrCreateTag().putInt("Energy", stored - extracted);

                return extracted;
            }

            @Override
            public int getEnergyStored() {
                return stack.getOrCreateTag().getInt("Energy");
            }

            @Override
            public int getMaxEnergyStored() {
                return profile.capacity;
            }

            @Override
            public boolean canExtract() {
                return true;
            }

            @Override
            public boolean canReceive() {
                return true;
            }
        });
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == ForgeCapabilities.ENERGY ? optional.cast() : LazyOptional.empty();
    }
}
