package net.forsteri.createmorepotatoes.entry;

import com.dm.earth.deferred_registries.DeferredObject;
import com.dm.earth.deferred_registries.DeferredRegistries;

import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.forsteri.createmorepotatoes.item.PotionPotatoItem;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

public class ModItems {
    public static final DeferredRegistries<Item> ITEMS =
			DeferredRegistries.create(Registry.ITEM, CreateMorePotatoes.MOD_ID);

    public static final DeferredObject<Item> EXPLOSIVE_POTATO = ITEMS.register("explosive_potato",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MORE_POTATOES_TAB).food(ModFoods.EXPLOSIVE_POTATO)));

    public static final DeferredObject<Item> GOLDEN_POTATO = ITEMS.register("golden_potato",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MORE_POTATOES_TAB).food(ModFoods.GOLDEN_POTATO)));

    public static final DeferredObject<Item> DIAMOND_POTATO = ITEMS.register("diamond_potato",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MORE_POTATOES_TAB).food(ModFoods.DIAMOND_POTATO)));

    public static final DeferredObject<Item> BAG_OF_POTATOES = ITEMS.register("bag_of_potatoes",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MORE_POTATOES_TAB)));

    public static final DeferredObject<Item> FRENCH_FRIES = ITEMS.register("french_fries",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.MORE_POTATOES_TAB)));

    public static final DeferredObject<Item> POTION_POTATO = ITEMS.register("potion_potato",
            PotionPotatoItem::new);


    public static void register(){
        ITEMS.register();
    }
}
