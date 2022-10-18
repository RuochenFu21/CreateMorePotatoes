package net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCanon;

import com.jozufozu.flywheel.backend.Backend;
import com.jozufozu.flywheel.core.PartialModel;
import com.jozufozu.flywheel.core.virtual.VirtualRenderWorld;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.base.IRotate;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.KineticTileEntityRenderer;
import com.simibubi.create.content.contraptions.components.deployer.DeployerTileEntity;
import com.simibubi.create.content.contraptions.components.structureMovement.MovementContext;
import com.simibubi.create.content.contraptions.components.structureMovement.render.ContraptionMatrices;
import com.simibubi.create.content.contraptions.components.structureMovement.render.ContraptionRenderDispatcher;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.tileEntity.behaviour.filtering.FilteringRenderer;
import com.simibubi.create.foundation.tileEntity.renderer.SafeTileEntityRenderer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.NBTHelper;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import static com.simibubi.create.content.contraptions.base.DirectionalKineticBlock.FACING;

public class StationaryPotatoCanonRenderer extends SafeTileEntityRenderer<StationaryPotatoCanonTileEntity> {
    public StationaryPotatoCanonRenderer(BlockEntityRendererProvider.Context context) {
    }

    protected BlockState getRenderedBlockState(KineticTileEntity te) {
        return KineticTileEntityRenderer.shaft(KineticTileEntityRenderer.getRotationAxisOf(te));
    }

    @Override
    protected void renderSafe(StationaryPotatoCanonTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        /* renderItem(te, partialTicks, ms, bufferSource, light, overlay); */
        FilteringRenderer.renderOnTileEntity(te, partialTicks, ms, bufferSource, light, overlay);

        if (Backend.canUseInstancing(te.getLevel())) return;

        renderComponents(te, partialTicks, ms, bufferSource, light, overlay);
    }

    public static void renderComponents(StationaryPotatoCanonTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay){

    }

    public static void renderInContraption(MovementContext context, VirtualRenderWorld renderWorld,
                                           ContraptionMatrices matrices, MultiBufferSource buffer) {
        VertexConsumer builder = buffer.getBuffer(RenderType.solid());
        BlockState blockState = context.state;

        float speed = (float) context.getAnimationSpeed();
        if (context.contraption.stalled)
            speed = 0;

        SuperByteBuffer shaft = CachedBufferer.block(AllBlocks.SHAFT.getDefaultState());

        double factor;
        if (context.contraption.stalled || context.position == null || context.data.contains("StationaryTimer")) {
            factor = Mth.sin(AnimationTickHolder.getRenderTime() * .5f) * .25f + .25f;
        } else {
            Vec3 center = VecHelper.getCenterOf(new BlockPos(context.position));
            double distance = context.position.distanceTo(center);
            double nextDistance = context.position.add(context.motion)
                    .distanceTo(center);
            factor = .5f - Mth.clamp(Mth.lerp(AnimationTickHolder.getPartialTicks(), distance, nextDistance), 0, 1);
        }

        Vec3 offset = Vec3.atLowerCornerOf(blockState.getValue(FACING)
                .getNormal()).scale(factor);

        PoseStack m = matrices.getModel();
        m.pushPose();

        m.pushPose();
        Direction.Axis axis = Direction.Axis.Y;
        if (context.state.getBlock() instanceof IRotate) {
            IRotate def = (IRotate) context.state.getBlock();
            axis = def.getRotationAxis(context.state);
        }

        float time = AnimationTickHolder.getRenderTime(context.world) / 20;
        float angle = (time * speed) % 360;

        TransformStack.cast(m)
                .centre()
                .rotateY(axis == Direction.Axis.Z ? 90 : 0)
                .rotateZ(axis.isHorizontal() ? 90 : 0)
                .unCentre();
        shaft.transform(m);
        shaft.rotateCentered(Direction.get(Direction.AxisDirection.POSITIVE, Direction.Axis.Y), angle);
        m.popPose();

        m.translate(offset.x, offset.y, offset.z);


        shaft.light(matrices.getWorld(), ContraptionRenderDispatcher.getContraptionWorldLight(context, renderWorld))
                .renderInto(matrices.getViewProjection(), builder);

        m.popPose();
    }
}
