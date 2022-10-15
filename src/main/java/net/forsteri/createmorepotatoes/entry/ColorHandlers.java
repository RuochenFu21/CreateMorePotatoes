package net.forsteri.createmorepotatoes.entry;

import com.mojang.logging.LogUtils;
import net.forsteri.createmorepotatoes.item.PotionPotatoItem;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ColorHandlers {
    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event)
    {
        LogUtils.getLogger().info("REGISTER ITEM COLORS");
        event.getItemColors().register(((pStack, pTintIndex) -> (0x555555)), new PotionPotatoItem());
    }
}
