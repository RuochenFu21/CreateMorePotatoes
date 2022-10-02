package net.forsteri.createmorepotatoes.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModCreativeModeTab {
    public static final CreativeModeTab MORE_POTATOES_TAB = new CreativeModeTab("potatotab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModItems.EXPLOSIVE_POTATO.get());
        }
    };
}
