package net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCannon;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.Create;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.contraptions.base.DirectionalKineticBlock;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.curiosities.armor.BackTankUtil;
import com.simibubi.create.content.curiosities.weapons.*;
import com.simibubi.create.content.curiosities.zapper.ShootableGadgetItemMethods;
import com.simibubi.create.foundation.utility.VecHelper;
import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class StationaryPotatoCannonTileEntity extends KineticTileEntity{

    protected int timeOut;

    public ItemStack stack = ItemStack.EMPTY;

    public StationaryPotatoCannonTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if (Objects.requireNonNull(getLevel()).hasNeighborSignal(getBlockPos()) && (this.timeOut <= 0) && (this.getSpeed() != 0))
        {
            this.shoot();
        }
        timeOut--;
    }

    protected final Vec3 calculateViewVector(float pPitch, float pYaw) {
        float f = pPitch * ((float)Math.PI / 180F);
        float f1 = -pYaw * ((float)Math.PI / 180F);
        float f2 = Mth.cos(f1);
        float f3 = Mth.sin(f1);
        float f4 = Mth.cos(f);
        float f5 = Mth.sin(f);
        return new Vec3((double)(f3 * f4), (double)(-f5), (double)(f2 * f4));
    }
    public void shoot(){
        CreateMorePotatoes.LOGGER.info("SHOOTING");
        if (Objects.requireNonNull(getLevel()).isClientSide) {
            return;
        }

        Vec3 barrelPos = new Vec3(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ());
        Vec3 correction = new Vec3(0,0,0);

        PotatoCannonProjectileType projectileType = PotatoProjectileTypeManager.getTypeForStack(stack)
                .orElse(BuiltinPotatoProjectileTypes.FALLBACK);
        float pPitch = 0;
        float pYaw = 0;
        switch (getBlockState().getValue(DirectionalKineticBlock.FACING)){
            case UP -> {
                pPitch = 0;
                pYaw = 90;
            }
            case DOWN -> {
                pPitch = 0;
                pYaw = -90;
            }
            case SOUTH -> {
                pPitch = 0;
                pYaw = 0;
            }
            case EAST -> {
                pPitch = 90;
                pYaw = 0;
            }
            case WEST -> {
                pPitch = 180;
                pYaw = 0;
            }
            case NORTH -> {
                pPitch = 270;
                pYaw = 0;
            }
        }
        Vec3 lookVec = calculateViewVector(pPitch, pYaw);

        Vec3 motion = lookVec.add(correction)
                .normalize()
                .scale(2)
                .scale(projectileType.getVelocityMultiplier());

        float soundPitch = projectileType.getSoundPitch() + (Create.RANDOM.nextFloat() - .5f) / 4f;

        boolean spray = projectileType.getSplit() > 1;
        Vec3 sprayBase = VecHelper.rotate(new Vec3(0, 0.1, 0), 360 * Create.RANDOM.nextFloat(), Direction.Axis.Z);
        float sprayChange = 360f / projectileType.getSplit();

        for (int i = 0; i < projectileType.getSplit(); i++) {
            PotatoProjectileEntity projectile = AllEntityTypes.POTATO_PROJECTILE.create(getLevel());
            projectile.setItem(stack);

            Vec3 splitMotion = motion;
            if (spray) {
                float imperfection = 40 * (Create.RANDOM.nextFloat() - 0.5f);
                Vec3 sprayOffset = VecHelper.rotate(sprayBase, i * sprayChange + imperfection, Direction.Axis.Z);
                splitMotion = splitMotion.add(VecHelper.lookAt(sprayOffset, motion));
            }

            projectile.setPos(barrelPos.x, barrelPos.y, barrelPos.z);
            projectile.setDeltaMovement(splitMotion);
            projectile.setOwner(null);
            getLevel().addFreshEntity(projectile);
        }

        stack.shrink(1);

        timeOut =
                PotatoProjectileTypeManager.getTypeForStack(stack).get().getReloadTicks();

        ShootableGadgetItemMethods.sendPackets(null,
                b -> new PotatoCannonPacket(barrelPos, lookVec.normalize(), stack, null, soundPitch, b));

    }


}
