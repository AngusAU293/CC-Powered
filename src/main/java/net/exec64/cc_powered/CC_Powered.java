package net.exec64.cc_powered;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(CC_Powered.MOD_ID)
public class CC_Powered {
    public static final String MOD_ID = "cc_powered";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CC_Powered() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SPEC, "cc_powered-server.toml");
        MinecraftForge.EVENT_BUS.register(this);
    }
}
