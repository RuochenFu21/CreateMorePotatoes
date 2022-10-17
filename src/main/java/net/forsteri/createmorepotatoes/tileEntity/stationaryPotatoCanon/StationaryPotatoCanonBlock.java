package net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCanon;

import com.simibubi.create.content.contraptions.base.DirectionalKineticBlock;
import com.simibubi.create.content.contraptions.base.KineticBlock;
import com.simibubi.create.content.contraptions.base.RotatedPillarKineticBlock;
import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class StationaryPotatoCanonBlock extends RotatedPillarKineticBlock {
    public StationaryPotatoCanonBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        CreateMorePotatoes.LOGGER.info("axis of stationary:" + state.getValue(AXIS));
        return state.getValue(AXIS);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        CreateMorePotatoes.LOGGER.info("axis of shaft testing:" + face.getAxis());
        CreateMorePotatoes.LOGGER.info("It's " + (getRotationAxis(state) == face.getAxis()));
        return face.getAxis() == getRotationAxis(state);
    }
}
