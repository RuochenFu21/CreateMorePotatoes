package net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCanon;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.KineticTileInstance;
import com.simibubi.create.content.contraptions.base.flwdata.RotatingData;
import com.simibubi.create.content.contraptions.relays.encased.ShaftInstance;
import net.minecraft.world.level.block.state.BlockState;

public class StationaryPotatoCanonInstance extends ShaftInstance {
    public StationaryPotatoCanonInstance(MaterialManager dispatcher, StationaryPotatoCanonTileEntity tile) {
        super(dispatcher, tile);
    }
}
