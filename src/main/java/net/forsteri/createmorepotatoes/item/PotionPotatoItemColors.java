package net.forsteri.createmorepotatoes.item;

import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.forsteri.createmorepotatoes.entry.ModItems;
import net.minecraft.client.color.item.ItemColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class PotionPotatoItemColors {
    @SubscribeEvent
    public void registerItemColors(ColorHandlerEvent.Item event)
    {
        event.getItemColors().register(((pStack, pTintIndex) -> (0xFFFFFF)), new PotionPotatoItem());
    }
}
