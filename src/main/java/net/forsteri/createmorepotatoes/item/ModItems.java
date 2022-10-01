package net.forsteri.createmorepotatoes.item;

import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CreateMorePotatoes.MOD_ID);

    public static final Item TNT_POTATO_ITEM = new Item(new Item.Properties().tab(ModCreativeModeTab.MORE_POTATOES_TAB));

    public static final RegistryObject<Item> TNT_POTATO = ITEMS.register("tnt_potato",
            () -> TNT_POTATO_ITEM);


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
