package net.exec64.cc_powered.mixin;

import dan200.computercraft.shared.computer.blocks.AbstractComputerBlockEntity;
import dan200.computercraft.shared.computer.blocks.ComputerBlockEntity;
import dan200.computercraft.shared.computer.core.ComputerFamily;
import net.exec64.cc_powered.energy.EnergyProfile;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ComputerBlockEntity.class)
public abstract class MixinComputerBlockEntity extends MixinAbstractComputerBlockEntity {
    @Override
    public EnergyProfile getEnergyProfile() {
        AbstractComputerBlockEntity self = (AbstractComputerBlockEntity)(Object) this;

        return self.getFamily() == ComputerFamily.ADVANCED
                ? EnergyProfile.ADVANCED_COMPUTER
                : self.getFamily() == ComputerFamily.COMMAND ? EnergyProfile.COMMAND_COMPUTER : EnergyProfile.BASIC_COMPUTER;
    }
}
