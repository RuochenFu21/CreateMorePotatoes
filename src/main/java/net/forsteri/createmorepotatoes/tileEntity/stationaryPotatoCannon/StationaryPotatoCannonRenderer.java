package net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCannon;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.KineticTileEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class StationaryPotatoCannonRenderer extends KineticTileEntityRenderer {
    public StationaryPotatoCannonRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(KineticTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        SuperByteBuffer shaft = CachedBufferer.partialFacing(AllBlockPartials.SHAFT_HALF, te.getBlockState(), Direction.DOWN);
        float offset = getRotationOffsetForPosition(te, te.getBlockPos(), Direction.Axis.Y);
        float angle = (AnimationTickHolder.getRenderTime(te.getLevel()) * te.getSpeed() * 3f / 10) % 360;

        if (te.getSpeed() != 0 && te.hasSource()) {
            BlockPos source = te.source.subtract(te.getBlockPos());
            Direction sourceFacing = Direction.getNearest(source.getX(), source.getY(), source.getZ());
            if (sourceFacing.getAxis() == Direction.DOWN.getAxis())
                angle *= sourceFacing == Direction.DOWN ? 1 : -1;
            else if (sourceFacing.getAxisDirection() == Direction.DOWN.getAxisDirection())
                angle *= -1;
        }

        angle += offset;
        angle = angle / 180f * (float) Math.PI;
        kineticRotationTransform(shaft, te, Direction.Axis.Y, angle, light);
        shaft.renderInto(ms, buffer.getBuffer(RenderType.solid()));
    }

    /*protected void renderSafe(StationaryPotatoCanonTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        *//* renderItem(te, partialTicks, ms, bufferSource, light, overlay); *//*
        FilteringRenderer.renderOnTileEntity(te, partialTicks, ms, bufferSource, light, overlay);
    }*/
}
