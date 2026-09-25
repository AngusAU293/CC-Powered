package net.exec64.cc_powered;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CC_Powered.MOD_ID)
public class CC_Powered {
    public static final String MOD_ID = "cc_powered";

    public CC_Powered(FMLJavaModLoadingContext context) {
        context.registerConfig(ModConfig.Type.SERVER, Config.SPEC, "cc_powered-server.toml");
        MinecraftForge.EVENT_BUS.register(this);
    }
}
