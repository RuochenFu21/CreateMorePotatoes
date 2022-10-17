package net.forsteri.createmorepotatoes.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;

import java.util.Collection;

public class PotionPotatoItem extends PotionItem {
    public PotionPotatoItem(){
        super(new Item.Properties().tab(PotionPotatoCreativeModeTab.POTION_POTATOES_TAB));
    }
}
