package net.forsteri.createmorepotatoes.entry;

import com.jozufozu.flywheel.backend.instancing.InstancedRenderRegistry;
import net.forsteri.createmorepotatoes.tileEntity.programmableStationaryPotatoCannon.ProgrammableStationaryPotatoCannonInstance;

public class ModInstances {
    public static void init() {
        InstancedRenderRegistry.configure(ModTileEntities.PROGRAMMABLE_STATIONARY_POTATO_CANNON.get())
                .alwaysSkipRender() // Completely skip the BlockEntityRenderer.
                .factory(ProgrammableStationaryPotatoCannonInstance::new) // Use our BlockEntityInstance instead.
                .apply(); // Apply the instancing configuration.
    }
}
