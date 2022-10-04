package net.forsteri.createmorepotatoes.entry;

import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

public class ModItems {
	public static final Item EXPLOSIVE_POTATO = new Item(
			new Item.Properties().tab(ModCreativeModeTab.MORE_POTATOES_TAB).food(ModFoods.EXPLOSIVE_POTATO));
	public static final Item GOLDEN_POTATO = new Item(
			new Item.Properties().tab(ModCreativeModeTab.MORE_POTATOES_TAB).food(ModFoods.GOLDEN_POTATO));
	public static final Item BAG_OF_POTATOES = new Item(
			new Item.Properties().tab(ModCreativeModeTab.MORE_POTATOES_TAB));

	public static void register() {
		Registry.register(Registry.ITEM, CreateMorePotatoes.asResource("explosive_potato"), EXPLOSIVE_POTATO);
		Registry.register(Registry.ITEM, CreateMorePotatoes.asResource("golden_potato"), GOLDEN_POTATO);
		Registry.register(Registry.ITEM, CreateMorePotatoes.asResource("bag_of_potatoes"), BAG_OF_POTATOES);
	}
}
