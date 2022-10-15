package net.forsteri.createmorepotatoes.entry;

import com.mojang.logging.LogUtils;
import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.forsteri.createmorepotatoes.item.PotionPotatoItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateMorePotatoes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ColorHandlers {
    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event)
    {
        LogUtils.getLogger().info("REGISTER ITEM COLORS");
        event.getItemColors().register(((pStack, pTintIndex) -> (0x555555)), ModItems.POTION_POTATO.get());
    }
}
