package net.forsteri.createmorepotatoes.item;

import net.forsteri.createmorepotatoes.entry.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PotionPotatoCreativeModeTab {
    public static final CreativeModeTab POTION_POTATOES_TAB = new CreativeModeTab("potionpotatotab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModItems.POTION_POTATO.get());
        }
    };
}
