package net.forsteri.createmorepotatoes;

import com.mojang.logging.LogUtils;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.forsteri.createmorepotatoes.entry.ModCreativeModeTab;
import net.forsteri.createmorepotatoes.entry.ModItems;
import net.forsteri.createmorepotatoes.item.PotionPotatoCreativeModeTab;
import net.minecraft.resources.ResourceLocation;
import net.forsteri.createmorepotatoes.entry.ColorHandlers;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;

public class CreateMorePotatoes implements ModInitializer, ClientModInitializer
{
    public static final String MOD_ID = "createmorepotatoes";
	public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
		setup();
	}

	@Override
	public void onInitializeClient() {
		ColorHandlers.registerItemColors();
	}

    private static void setup() {
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

		ModCreativeModeTab.loadClass();
		PotionPotatoCreativeModeTab.loadClass();

		ModItems.register();
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
