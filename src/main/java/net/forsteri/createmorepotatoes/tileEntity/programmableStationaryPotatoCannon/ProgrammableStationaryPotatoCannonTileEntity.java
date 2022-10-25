package net.forsteri.createmorepotatoes.tileEntity.programmableStationaryPotatoCannon;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileEntity;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileTypeManager;
import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProgrammableStationaryPotatoCannonTileEntity extends KineticTileEntity {

    protected int timeOut;
    protected double theta = 0;

    public ItemStack stack = ItemStack.EMPTY;

    public ProgrammableStationaryPotatoCannonTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if (Objects.requireNonNull(getLevel()).hasNeighborSignal(getBlockPos()) && (this.timeOut <= 0) && (this.getSpeed() != 0) && (stack != ItemStack.EMPTY))
        {
            theta = this.shoot();
        }
        timeOut--;
        CreateMorePotatoes.LOGGER.info("this.getTheta = " + this.getTheta());
    }

    public double shoot() {
        LivingEntity nearestEntity = Objects.requireNonNull(getLevel()).getNearestEntity(
                getLevel().getEntities(
                        null, new AABB(
                                getBlockPos().getX()+256,
                                getBlockPos().getY()+256,
                                getBlockPos().getZ()+256,
                                getBlockPos().getX()-256,
                                getBlockPos().getY()-256,
                                getBlockPos().getZ()-256
                        )
                ).stream().filter(entity -> entity instanceof LivingEntity).map(entity -> (LivingEntity) entity).collect(Collectors.toList()), TargetingConditions.forNonCombat().range(16.0D).ignoreInvisibilityTesting(), null, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ()
        );

        assert nearestEntity != null;
        this.theta = Math.atan2(nearestEntity.getX()-getBlockPos().getX(), nearestEntity.getY()-getBlockPos().getY()) * 180 / Math.PI;

        CreateMorePotatoes.LOGGER.info("theta: " + this.getTheta());
        PotatoProjectileEntity projectile = AllEntityTypes.POTATO_PROJECTILE.create(Objects.requireNonNull(getLevel()));
        assert projectile != null;
        projectile.setItem(stack);
        float xMove = 0;
        float yMove = 0;
        float zMove = 0;
        projectile.setPos(getBlockPos().getX()+xMove+0.5, getBlockPos().getY()+yMove+0.5, getBlockPos().getZ()+zMove+0.5);
        projectile.setDeltaMovement(xMove * 2 , yMove * 2, zMove * 2);
        getLevel().addFreshEntity(projectile);
        assert PotatoProjectileTypeManager.getTypeForStack(stack).isPresent();
        timeOut = (stack == ItemStack.EMPTY)? 0 : PotatoProjectileTypeManager.getTypeForStack(stack).get().getReloadTicks() /2;
        stack.shrink(1);
        if (stack.getCount() == 0){
            stack = ItemStack.EMPTY.copy();
        }
        return Math.atan2(nearestEntity.getX()-getBlockPos().getX(), nearestEntity.getY()-getBlockPos().getY()) * 180 / Math.PI;
    }

    public double getTheta() {
        return this.theta;
    }
}
