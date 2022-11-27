package net.forsteri.createmorepotatoes.entry;

import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.forsteri.createmorepotatoes.item.ExplosivePotionPotatoItem;
import net.forsteri.createmorepotatoes.item.PotionPotatoItem;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;

// @SuppressWarnings("unused")
public class ModItems {
	public static final LazyRegistrar<Item> ITEMS = LazyRegistrar.create(Registry.ITEM, CreateMorePotatoes.MOD_ID);

	public static final RegistryObject<Item> EXPLOSIVE_POTATO = ITEMS.register("explosive_potato",
			() -> new ItemNameBlockItem(ModBlocks.EXPLOSIVE_POTATO_CROP.get(), new Item.Properties()
					.tab(ModCreativeModeTab.MORE_POTATOES_TAB).food(ModFoods.EXPLOSIVE_POTATO)));

	public static final RegistryObject<Item> GOLDEN_POTATO = ITEMS.register("golden_potato",
			() -> new Item(new Item.Properties().tab(ModCreativeModeTab.MORE_POTATOES_TAB)
					.food(ModFoods.GOLDEN_POTATO)));

	public static final RegistryObject<Item> DIAMOND_POTATO = ITEMS.register("diamond_potato",
			() -> new Item(new Item.Properties().tab(ModCreativeModeTab.MORE_POTATOES_TAB)
					.food(ModFoods.DIAMOND_POTATO)));

	public static final RegistryObject<Item> BAG_OF_POTATOES = ITEMS.register("bag_of_potatoes",
			() -> new Item(new Item.Properties().tab(ModCreativeModeTab.MORE_POTATOES_TAB)));

	public static final RegistryObject<Item> FRENCH_FRIES = ITEMS.register("french_fries",
			() -> new Item(new Item.Properties().tab(ModCreativeModeTab.MORE_POTATOES_TAB)));

	public static final RegistryObject<Item> CREATIVE_POTATO = ITEMS.register("creative_potato",
			() -> new Item(new Item.Properties().tab(ModCreativeModeTab.MORE_POTATOES_TAB)));

	public static final RegistryObject<Item> POTION_POTATO = ITEMS.register("potion_potato",
			PotionPotatoItem::new);

	public static final RegistryObject<Item> EXPLOSIVE_POTION_POTATO = ITEMS.register("explosive_potion_potato",
			ExplosivePotionPotatoItem::new);

	public static final RegistryObject<Item> FLAME_POTATO = ITEMS.register("flame_potato",
			() -> new Item(new Item.Properties().tab(ModCreativeModeTab.MORE_POTATOES_TAB)));

	public static final RegistryObject<Item> FROSTY_POTATO = ITEMS.register("frosty_potato",
			() -> new Item(new Item.Properties().tab(ModCreativeModeTab.MORE_POTATOES_TAB)));

	public static void register() {
		ITEMS.register();
	}
}
