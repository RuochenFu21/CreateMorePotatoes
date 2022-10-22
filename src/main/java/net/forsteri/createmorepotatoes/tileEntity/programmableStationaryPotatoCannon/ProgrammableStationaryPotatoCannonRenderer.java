package net.forsteri.createmorepotatoes.tileEntity.programmableStationaryPotatoCannon;

import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.KineticTileEntityRenderer;
import com.simibubi.create.content.contraptions.relays.elementary.ShaftBlock;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.forsteri.createmorepotatoes.entry.ModBlockPartial;
import net.forsteri.createmorepotatoes.entry.ModBlocks;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class ProgrammableStationaryPotatoCannonRenderer extends KineticTileEntityRenderer {

    public ProgrammableStationaryPotatoCannonRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(KineticTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        SuperByteBuffer shaft = CachedBufferer.partialFacing(AllBlockPartials.SHAFT_HALF, te.getBlockState(), Direction.UP);
        float offset = getRotationOffsetForPosition(te, te.getBlockPos(), Direction.Axis.Y);
        float angle = (AnimationTickHolder.getRenderTime(te.getLevel()) * te.getSpeed() * 3f / 10) % 360;

        if (te.getSpeed() != 0 && te.hasSource()) {
            assert te.source != null;
            BlockPos source = te.source.subtract(te.getBlockPos());
            Direction sourceFacing = Direction.getNearest(source.getX(), source.getY(), source.getZ());
            if (sourceFacing.getAxis() == Direction.UP.getAxis())
                angle *= sourceFacing == Direction.UP ? 1 : -1;
            else if (sourceFacing.getAxisDirection() == Direction.UP.getAxisDirection())
                angle *= -1;
        }

        angle += offset;
        angle = angle / 180f * (float) Math.PI;
        kineticRotationTransform(shaft, te, Direction.Axis.Y, angle, light);
        shaft.renderInto(ms, buffer.getBuffer(RenderType.solid()));

        SuperByteBuffer cannon = CachedBufferer.partialFacing(ModBlockPartial.cannon_partial, te.getBlockState(), Direction.UP);
        cannon.renderInto(ms, buffer.getBuffer(RenderType.solid()));
    }
}
