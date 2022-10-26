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
import net.minecraft.world.phys.AABB;

import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class ProgrammableStationaryPotatoCannonTileEntity extends KineticTileEntity {

    protected int timeOut;
    protected double phi = 0;
    protected double theta = 0;

    public ItemStack stack = ItemStack.EMPTY;

    public ProgrammableStationaryPotatoCannonTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if(level != null && level.isClientSide){
            this.calculateDimensions();
        }
        if (Objects.requireNonNull(getLevel()).hasNeighborSignal(getBlockPos()) && (this.timeOut <= 0) && (this.getSpeed() != 0) && (stack != ItemStack.EMPTY))
        {
            if (this.calculateDimensions()){
                this.shoot();
            }
        }
        timeOut--;
    }

    public void shoot() {

        CreateMorePotatoes.LOGGER.info("theta: " + this.getPhi());
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
    }

    protected boolean calculateDimensions(){
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

        if(nearestEntity == null){
            return false;
        }

        double g = ((stack == ItemStack.EMPTY) ? 1.2 : PotatoProjectileTypeManager.getTypeForStack(stack).get().getGravityMultiplier());
        double v = ((stack == ItemStack.EMPTY) ? 9 : PotatoProjectileTypeManager.getTypeForStack(stack).get().getVelocityMultiplier() * 10);
        double x = nearestEntity.getX()-getBlockPos().getX()-.5;
        double y = nearestEntity.getY()-getBlockPos().getY()-.5;
        double z = nearestEntity.getZ()-getBlockPos().getZ()-.5;
        double r = Math.sqrt(x*x+z*z);

        this.phi = Math.atan2(x, z) + Math.PI;
        this.theta = Math.atan2(
                v*v - Math.sqrt(v*v*v*v-g*(g*r*r+2*y*v*v)),
                g*r
        );

        this.theta = Math.PI/8;
        return true;
    }

    public double getPhi() {
        return this.phi;
    }

    public double getTheta() {
        return this.theta;
    }
}
