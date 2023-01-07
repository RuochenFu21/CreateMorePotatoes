package net.forsteri.createmorepotatoes.entry;

import com.mojang.logging.LogUtils;
import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateMorePotatoes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ColorHandlers {
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event)
    {
        LogUtils.getLogger().info("REGISTER ITEM COLORS");
        event.getItemColors().register(
                (
                        (pStack, pTintIndex) -> (
                                pTintIndex == 0 ?
                                        PotionUtils.getColor(pStack) :
                                        0xFFFFFF
                        )
                )
                , ModItems.POTION_POTATO.get(), ModItems.EXPLOSIVE_POTION_POTATO.get()
        );
    }
}
