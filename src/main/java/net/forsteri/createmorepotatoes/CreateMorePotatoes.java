package net.forsteri.createmorepotatoes;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.forsteri.createmorepotatoes.entry.ColorHandlers;
import net.forsteri.createmorepotatoes.entry.ModBlocks;
import net.forsteri.createmorepotatoes.entry.ModCreativeModeTab;
import net.forsteri.createmorepotatoes.entry.ModItems;
import net.forsteri.createmorepotatoes.entry.ModTileEntities;
import net.forsteri.createmorepotatoes.item.PotionPotatoCreativeModeTab;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class CreateMorePotatoes implements ModInitializer, ClientModInitializer {
	public static final String MOD_ID = "createmorepotatoes";
	// Directly reference a slf4j logger
	public static final Logger LOGGER = LogUtils.getLogger();

	private static final CreateRegistrate registrate = CreateRegistrate.create(CreateMorePotatoes.MOD_ID);

	@Override
	public void onInitialize() {
		ModCreativeModeTab.load();
		PotionPotatoCreativeModeTab.load();

		ModItems.register();
		ModBlocks.register();
		ModTileEntities.register();
		registrate.register();

		ModTileEntities.registerTransfer();
	}

	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EXPLOSIVE_POTATO_CROP.get(), RenderType.cutout());
		ColorHandlers.registerItemColors();
	}

	public static CreateRegistrate registrate() {
		return registrate;
	}

	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
