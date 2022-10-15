package net.forsteri.createmorepotatoes.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.forsteri.createmorepotatoes.entry.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PotionPotatoCreativeModeTab {
    public static final CreativeModeTab POTION_POTATOES_TAB = FabricItemGroupBuilder.create(CreateMorePotatoes.asResource("potionpotatotab")).icon(() -> new ItemStack(ModItems.POTION_POTATO.get())).build();

	public static void loadClass() {}
}
