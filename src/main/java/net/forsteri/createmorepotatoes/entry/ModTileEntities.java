package net.forsteri.createmorepotatoes.entry;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCanon.StationaryPotatoCanonRenderer;
import net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCanon.StationaryPotatoCanonTileEntity;

public class ModTileEntities {
    private static final CreateRegistrate REGISTRATE = CreateMorePotatoes.registrate();

    public static final BlockEntityEntry<StationaryPotatoCanonTileEntity> FLYWHEEL = REGISTRATE
            .tileEntity("stationary_potato_canon", StationaryPotatoCanonTileEntity::new)
            .validBlocks(ModBlocks.STATIONARY_POTATO_CANON)
            .renderer(() -> StationaryPotatoCanonRenderer::new)
            .register();
}
