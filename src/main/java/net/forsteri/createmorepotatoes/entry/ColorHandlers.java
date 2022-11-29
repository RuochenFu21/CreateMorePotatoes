package net.forsteri.createmorepotatoes.entry;

import com.mojang.logging.LogUtils;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.world.item.alchemy.PotionUtils;

public class ColorHandlers {
	public static void registerItemColors() {
		LogUtils.getLogger().info("REGISTER ITEM COLORS");
		ColorProviderRegistry.ITEM.register(((pStack, pTintIndex) -> (PotionUtils.getColor(pStack))),
				ModItems.POTION_POTATO.get());
		ColorProviderRegistry.ITEM.register(
				((pStack, pTintIndex) -> pTintIndex == 0 ? (PotionUtils.getColor(pStack)) : -1),
				ModItems.EXPLOSIVE_POTION_POTATO.get());
	}
}
