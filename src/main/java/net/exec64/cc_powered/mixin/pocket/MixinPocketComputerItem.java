package net.exec64.cc_powered.mixin.pocket;

import dan200.computercraft.shared.computer.core.ComputerFamily;
import dan200.computercraft.shared.pocket.core.PocketBrain;
import dan200.computercraft.shared.pocket.core.PocketHolder;
import dan200.computercraft.shared.pocket.items.PocketComputerItem;
import net.exec64.cc_powered.energy.EnergyProfile;
import net.exec64.cc_powered.energy.ItemEnergyCapabilityProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(PocketComputerItem.class)
public abstract class MixinPocketComputerItem extends Item {
    @Shadow(remap = false)
    public abstract ComputerFamily getFamily();

    public MixinPocketComputerItem(Properties properties) {
        super(properties);
    }

    @Unique
    public EnergyProfile getEnergyProfile() {
        return getFamily() == ComputerFamily.NORMAL
                ? EnergyProfile.BASIC_POCKET_COMPUTER
                : EnergyProfile.ADVANCED_POCKET_COMPUTER;
    }

    @Unique
    private static Optional<IEnergyStorage> getEnergyStorage(ItemStack stack) {
        return stack.getCapability(ForgeCapabilities.ENERGY).resolve();
    }

    @Inject(method = "tick", at = @At("TAIL"), remap = false)
    private void tick(ItemStack stack, PocketHolder holder, PocketBrain brain, CallbackInfo ci) {
        if (getEnergyStorage(stack).isEmpty()
                || brain == null
                || !brain.computer().isOn()
                || brain.computer().getLevel().isClientSide)
            return;

        getEnergyStorage(stack).ifPresent(energy -> {
            int extractedEnergy = energy.extractEnergy(getEnergyProfile().consumption, true);

            if (extractedEnergy < getEnergyProfile().consumption) {
                brain.computer().shutdown();
            } else {
                energy.extractEnergy(getEnergyProfile().consumption, false);
            }
        });
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> list,
                                @NotNull TooltipFlag flag) {
        getEnergyStorage(stack).ifPresent(energy ->
            list.add(Component.literal("Charge: " + energy.getEnergyStored() + "/" +
                    getEnergyProfile().capacity)));
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return getEnergyProfile().capacity > 0;
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        Optional<Integer> energyStored = getEnergyStorage(stack).map(IEnergyStorage::getEnergyStored);
        if (energyStored.isEmpty()) return 0;

        if (getEnergyProfile().capacity == 0
                || energyStored.get() == 0)
            return 0;

        return Math.round((float) energyStored.get() * 13.0f /
                (float) getEnergyProfile().capacity);
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return 0x3CFE9A;
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemEnergyCapabilityProvider(stack, getEnergyProfile());
    }
}
