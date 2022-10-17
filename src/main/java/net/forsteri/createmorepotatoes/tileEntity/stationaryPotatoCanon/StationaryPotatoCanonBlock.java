package net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCanon;

import com.simibubi.create.content.contraptions.base.KineticBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class StationaryPotatoCanonBlock extends KineticBlock {
    public StationaryPotatoCanonBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return null;
    }
}
