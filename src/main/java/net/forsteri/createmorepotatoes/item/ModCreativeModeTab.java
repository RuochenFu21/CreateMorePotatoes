package net.forsteri.createmorepotatoes.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab MORE_POTATOES_TAB = new CreativeModeTab("potatotab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.TNT_POTATO.get());
        }
    };
}
