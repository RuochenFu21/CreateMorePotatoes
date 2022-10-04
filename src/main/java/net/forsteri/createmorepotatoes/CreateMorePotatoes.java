package net.forsteri.createmorepotatoes;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.forsteri.createmorepotatoes.entry.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;

public class CreateMorePotatoes implements ModInitializer
{
    public static final String MOD_ID = "createmorepotatoes";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
		ModItems.register();
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
