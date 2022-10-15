package net.forsteri.createmorepotatoes.entry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab MORE_POTATOES_TAB = FabricItemGroupBuilder.create(CreateMorePotatoes.asResource("potatotab")).icon(() -> new ItemStack(ModItems.EXPLOSIVE_POTATO.get())).build();

	public static void loadClass() {}
}
