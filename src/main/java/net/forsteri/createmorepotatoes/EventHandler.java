package net.forsteri.createmorepotatoes;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import com.simibubi.create.events.ClientEvents;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class EventHandler {
    @SubscribeEvent
    public static void arrowNocked(ArrowNockEvent event) {
        LogUtils.getLogger().info("HELLO FROM PREINIT");
    }
//    @SubscribeEvent
//    public static void potato() {
//        LogUtils.getLogger().info("HELLO FROM PREINIT");
//    }
}
