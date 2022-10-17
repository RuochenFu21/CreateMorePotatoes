package net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCanon;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class StationaryPotatoCanonTileEntity extends KineticTileEntity {
    public StationaryPotatoCanonTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }
}
