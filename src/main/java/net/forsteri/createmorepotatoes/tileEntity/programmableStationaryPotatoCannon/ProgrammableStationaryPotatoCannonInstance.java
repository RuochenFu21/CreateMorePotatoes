package net.forsteri.createmorepotatoes.tileEntity.programmableStationaryPotatoCannon;

import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.SingleRotatingInstance;
import com.simibubi.create.content.contraptions.relays.elementary.ShaftBlock;
import net.forsteri.createmorepotatoes.entry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class ProgrammableStationaryPotatoCannonInstance extends SingleRotatingInstance {
    public ProgrammableStationaryPotatoCannonInstance(MaterialManager modelManager, KineticTileEntity tile) {
        super(modelManager, tile);
    }

    @Override
    protected BlockState getRenderedBlockState() {
        return ModBlocks.STATIONARY_POTATO_CANNON.getDefaultState();
    }
}
