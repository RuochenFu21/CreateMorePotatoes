package net.forsteri.createmorepotatoes;

import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import com.simibubi.create.events.ClientEvents;

public class EventHandler {
    @SubscribeEvent
    public static void arrowNocked(ArrowNockEvent event) {
        System.out.println("Arrow nocked!");
    }
}
