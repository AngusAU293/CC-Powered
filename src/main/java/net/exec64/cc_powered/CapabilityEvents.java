package net.exec64.cc_powered;

import net.exec64.cc_powered.energy.IEnergyHolder;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = CC_Powered.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityEvents {
    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<BlockEntity> event) {
        BlockEntity blockEntity = event.getObject();

        if (!(blockEntity instanceof IEnergyHolder holder)) return;

        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(CC_Powered.MOD_ID, "energy");
        if (event.getCapabilities().containsKey(id)) return;

        LazyOptional<IEnergyStorage> lazy = holder.getEnergyCapability();

        event.addCapability(id, new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                if (cap == ForgeCapabilities.ENERGY) return lazy.cast();
                return LazyOptional.empty();
            }
        });

        event.addListener(lazy::invalidate);
    }
}
