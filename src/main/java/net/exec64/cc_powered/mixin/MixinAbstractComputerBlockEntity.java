package net.exec64.cc_powered.mixin;

import dan200.computercraft.shared.computer.blocks.AbstractComputerBlockEntity;
import dan200.computercraft.shared.computer.core.ComputerFamily;
import dan200.computercraft.shared.computer.core.ServerComputer;
import net.exec64.cc_powered.energy.ComputerEnergyStorage;
import net.exec64.cc_powered.energy.EnergyProfile;
import net.exec64.cc_powered.energy.IEnergyHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(AbstractComputerBlockEntity.class)
public abstract class MixinAbstractComputerBlockEntity implements IEnergyHolder {
    @Shadow(remap = false)
    private boolean on;

    @Shadow(remap = false)
    @Nullable
    public abstract ServerComputer getServerComputer();

    @Shadow(remap = false)
    public abstract int getComputerID();

    @Unique
    private ComputerEnergyStorage cc_powered$energyStorage;

    @Unique
    private LazyOptional<IEnergyStorage> cc_powered$energyCapability;

    @Override
    public EnergyProfile getEnergyProfile() {
        return EnergyProfile.BASIC_COMPUTER;
    }

    @Override
    public ComputerEnergyStorage getEnergyStorage() {
        if (cc_powered$energyStorage == null) {
            cc_powered$energyStorage = new ComputerEnergyStorage(getEnergyProfile());
            cc_powered$energyCapability = LazyOptional.of(() -> cc_powered$energyStorage);
        }

        return cc_powered$energyStorage;
    }

    @Override
    public LazyOptional<IEnergyStorage> getEnergyCapability() {
        if (cc_powered$energyCapability == null) {
            getEnergyStorage();
        }

        return cc_powered$energyCapability;
    }

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void init(BlockEntityType type, BlockPos pos, BlockState state, ComputerFamily family, CallbackInfo ci) {
        cc_powered$energyStorage = null;
        cc_powered$energyCapability = null;

        getEnergyCapability();
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void saveAdditional(CompoundTag tag, CallbackInfo ci) {
        tag.put("cc_powered_energy", getEnergyStorage().serializeNBT());
    }

    @Inject(method = "load", at = @At("TAIL"))
    private void load(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("cc_powered_energy")) {
            getEnergyStorage().deserializeNBT(tag.getCompound("cc_powered_energy"));
        }
    }

    @Inject(method = "serverTick", at = @At("TAIL"), remap = false)
    private void serverTick(CallbackInfo ci) {
        if (!on || getComputerID() < 0
                || getServerComputer() == null
                || getServerComputer().getLevel().isClientSide
                || getServerComputer().getFamily() == ComputerFamily.COMMAND)
            return;

        int extractedEnergy = getEnergyStorage().extractEnergy(getEnergyProfile().consumption, true);

        if (extractedEnergy < getEnergyProfile().consumption) {
            getServerComputer().shutdown();
        } else {
            getEnergyStorage().extractEnergy(getEnergyProfile().consumption, false);
        }
    }
}
