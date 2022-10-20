package net.forsteri.createmorepotatoes.tileEntity.programmableStationaryPotatoCannon;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.KineticTileEntityRenderer;
import com.simibubi.create.content.contraptions.relays.elementary.ShaftBlock;
import net.forsteri.createmorepotatoes.entry.ModBlocks;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class ProgrammableStationaryPotatoCannonRenderer extends KineticTileEntityRenderer {

    public ProgrammableStationaryPotatoCannonRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected BlockState getRenderedBlockState(KineticTileEntity te) {
        return ModBlocks.STATIONARY_POTATO_CANNON.getDefaultState();
    }
}
