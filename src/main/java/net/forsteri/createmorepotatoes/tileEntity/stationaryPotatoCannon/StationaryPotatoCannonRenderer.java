package net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCannon;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.KineticTileEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class StationaryPotatoCannonRenderer extends KineticTileEntityRenderer {
    public StationaryPotatoCannonRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected BlockState getRenderedBlockState(KineticTileEntity te) {
        return shaft(getRotationAxisOf(te));
    }


    /*protected void renderSafe(StationaryPotatoCanonTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        *//* renderItem(te, partialTicks, ms, bufferSource, light, overlay); *//*
        FilteringRenderer.renderOnTileEntity(te, partialTicks, ms, bufferSource, light, overlay);
    }*/
}
