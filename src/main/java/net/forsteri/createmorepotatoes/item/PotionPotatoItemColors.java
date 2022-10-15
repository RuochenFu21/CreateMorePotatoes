package net.forsteri.createmorepotatoes.item;

import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.forsteri.createmorepotatoes.entry.ModItems;
import net.minecraft.client.color.item.ItemColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class PotionPotatoItemColors {
    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event)
    {
        CreateMorePotatoes.LOGGER.info("REGISTERING ITEM COLOR");
        /* event.getItemColors().register(((pStack, pTintIndex) -> (0x555555)), new PotionPotatoItem()); */
    }
}
