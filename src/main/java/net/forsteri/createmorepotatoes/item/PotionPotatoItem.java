package net.forsteri.createmorepotatoes.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;

import java.util.Collection;

public class PotionPotatoItem extends PotionItem {
	private static final String NBT_ID = "ID";
	private static final String NBT_DURATION = "Duration";
	private static final String NBT_AMPLIFIER = "Amplifier";

	public PotionPotatoItem() {
		super(new Item.Properties().tab(PotionPotatoCreativeModeTab.POTION_POTATOES_TAB));
	}

	public static int getPotionColor(ItemStack stack) {
		return PotionUtils.getColor(
				stack
		);
	}

}
