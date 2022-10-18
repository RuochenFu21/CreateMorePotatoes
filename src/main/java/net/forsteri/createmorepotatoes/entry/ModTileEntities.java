package net.forsteri.createmorepotatoes.entry;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCannon.StationaryPotatoCannonInstance;
import net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCannon.StationaryPotatoCannonRenderer;
import net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCannon.StationaryPotatoCannonTileEntity;

public class ModTileEntities {
    private static final CreateRegistrate REGISTRATE = CreateMorePotatoes.registrate();

    public static final BlockEntityEntry<StationaryPotatoCannonTileEntity> STATIONARY_POTATO_CANNON = REGISTRATE
            .tileEntity("stationary_potato_cannon", StationaryPotatoCannonTileEntity::new)
            .instance(() -> StationaryPotatoCannonInstance::new)
            .validBlocks(ModBlocks.STATIONARY_POTATO_CANNON)
            .renderer(() -> StationaryPotatoCannonRenderer::new)
            .register();

    public static void register(){

    }
}
