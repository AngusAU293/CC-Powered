package net.exec64.cc_powered.mixin;

import dan200.computercraft.shared.computer.core.ComputerFamily;
import dan200.computercraft.shared.computer.core.ServerComputer;
import net.exec64.cc_powered.energy.ComputerEnergyStorage;
import net.exec64.cc_powered.energy.EnergyProfile;
import net.exec64.cc_powered.energy.IEnergyHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerComputer.class)
public abstract class MixinServerComputer {
    @Shadow(remap = false)
    public abstract ServerLevel getLevel();

    @Shadow(remap = false)
    public abstract BlockPos getPosition();

    @Shadow(remap = false)
    public abstract ComputerFamily getFamily();

    @Inject(method = "turnOn", at = @At("HEAD"), remap = false, cancellable = true)
    private void turnOn(CallbackInfo ci) {
        if (getFamily() == ComputerFamily.COMMAND) return;

        BlockEntity blockEntity = getLevel().getExistingBlockEntity(getPosition());

        if (blockEntity instanceof IEnergyHolder) {
            ComputerEnergyStorage energyStorage = ((IEnergyHolder) blockEntity).getEnergyStorage();
            EnergyProfile energyProfile = ((IEnergyHolder) blockEntity).getEnergyProfile();

            int extractedEnergy = energyStorage.extractEnergy(energyProfile.consumption, true);
            if (extractedEnergy < energyProfile.consumption) ci.cancel();
        }
    }
}
