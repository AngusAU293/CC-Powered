package net.exec64.cc_powered.mixin.turtle;

import dan200.computercraft.shared.turtle.blocks.TurtleBlockEntity;
import net.exec64.cc_powered.energy.EnergyProfile;
import net.exec64.cc_powered.energy.IEnergyHolder;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TurtleBlockEntity.class)
public abstract class MixinTurtleBlockEntity implements IEnergyHolder {
    @Override
    public EnergyProfile getEnergyProfile() {
        return EnergyProfile.TURTLE;
    }
}
