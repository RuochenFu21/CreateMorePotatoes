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
import net.minecraft.world.phys.Vec3;

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
            this.calculateDimensions();
            this.shoot();
        }
        timeOut--;
    }

    public void shoot() {
        PotatoProjectileEntity projectile = AllEntityTypes.POTATO_PROJECTILE.create(Objects.requireNonNull(getLevel()));
        assert projectile != null;
        projectile.setItem(stack);
        Vec3 facing = new Vec3(0, 0, -v).yRot((float) phi)/* .xRot((float) theta) */;
        float xMove = (float) facing.x;
        float yMove = (float) Math.sin(getTheta());
        float zMove = (float) facing.z;
        projectile.setPos(getBlockPos().getX()+xMove+0.5, getBlockPos().getY()+yMove+0.5, getBlockPos().getZ()+zMove+0.5);
        projectile.setDeltaMovement(xMove , yMove, zMove);
        getLevel().addFreshEntity(projectile);
        assert PotatoProjectileTypeManager.getTypeForStack(stack).isPresent();
        timeOut = (stack == ItemStack.EMPTY)? 0 : PotatoProjectileTypeManager.getTypeForStack(stack).get().getReloadTicks() /2;
        stack.shrink(1);
        if (stack.getCount() == 0){
            stack = ItemStack.EMPTY.copy();
        }

    }

    protected void calculateDimensions(){
        nearestEntity = Objects.requireNonNull(getLevel()).getNearestEntity(
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
            return;
        }

        entityX = nearestEntity.getX();
        entityY = (nearestEntity.getEyeY()+nearestEntity.getY())/2;
        entityZ = nearestEntity.getZ();
        g = ((stack == ItemStack.EMPTY) ? 1.2 : PotatoProjectileTypeManager.getTypeForStack(stack).get().getGravityMultiplier());
        v = ((stack == ItemStack.EMPTY) ? 9 : PotatoProjectileTypeManager.getTypeForStack(stack).get().getVelocityMultiplier() * 10);
        x = entityX-getBlockPos().getX()-.5;
        y = entityY-getBlockPos().getY()-.5;
        z = entityZ-getBlockPos().getZ()-.5;
        r = Math.sqrt(x*x+z*z);

        this.phi = Math.atan2(x, z) + Math.PI;
        this.theta = Math.atan2(
                v*v - Math.sqrt(v*v*v*v-g*(g*r*r+2*y*v*v)),
                g*r
        );


    }

    public double getPhi() {
        return this.phi;
    }

    public double getTheta() {
        return this.theta;
    }

    protected LivingEntity nearestEntity;
    protected double entityX;
    protected double entityY;
    protected double entityZ;
    protected double g;
    protected double v;
    protected double x;
    protected double y;
    protected double z;
    protected double r;
}
