package net.forsteri.createmorepotatoes.tileEntity.programmableStationaryPotatoCannon;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileEntity;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileTypeManager;
import net.forsteri.createmorepotatoes.tileEntity.CannonInventoryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class ProgrammableStationaryPotatoCannonTileEntity extends KineticTileEntity {

    protected int timeOut;
    protected double phi = 0;
    protected double theta = 0;

    public LazyOptional<IItemHandler> capability;

    public ItemStackHandler inventory;

    public ProgrammableStationaryPotatoCannonTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        inventory = new ItemStackHandler(1);
        capability = LazyOptional.of(() -> new CannonInventoryHandler(inventory));
    }

    @Override
    public void tick() {
        super.tick();
        if(level != null && level.isClientSide){
            this.calculateDimensions();
        }
        if (Objects.requireNonNull(getLevel()).hasNeighborSignal(getBlockPos()) && (this.timeOut <= 0) && (this.getSpeed() != 0) && (inventory.getStackInSlot(0) != ItemStack.EMPTY))
        {
            this.calculateDimensions();
            this.shoot();
        }
        timeOut--;
    }

    public void shoot() {
        PotatoProjectileEntity projectile = AllEntityTypes.POTATO_PROJECTILE.create(Objects.requireNonNull(getLevel()));
        assert projectile != null;
        projectile.setItem(inventory.getStackInSlot(0));
        Vec3 facing = new Vec3(0, 0, -v/10).yRot((float) phi)/* .xRot((float) theta) */;
        float xMove = (float) facing.x;
        float yMove = (float) Math.sin(getTheta());
        float zMove = (float) facing.z;
        projectile.setPos(getBlockPos().getX()+xMove+0.5, getBlockPos().getY()+yMove+0.5, getBlockPos().getZ()+zMove+0.5);
        projectile.setDeltaMovement(xMove , yMove, zMove);
        getLevel().addFreshEntity(projectile);
        assert PotatoProjectileTypeManager.getTypeForStack(inventory.getStackInSlot(0)).isPresent();
        timeOut = (inventory.getStackInSlot(0) == ItemStack.EMPTY)? 0 : PotatoProjectileTypeManager.getTypeForStack(inventory.getStackInSlot(0)).get().getReloadTicks() /2;
        inventory.getStackInSlot(0).shrink(1);
        if (inventory.getStackInSlot(0).getCount() == 0){
            inventory.setStackInSlot(0, ItemStack.EMPTY.copy());
        }

    }

    protected void calculateDimensions(){
        nearestEntity = Objects.requireNonNull(getLevel()).getNearestEntity(
                getLevel().getEntities(
                        null, new AABB(
                                getBlockPos().getX()+128,
                                getBlockPos().getY()+256,
                                getBlockPos().getZ()+128,
                                getBlockPos().getX()-128,
                                getBlockPos().getY()-256,
                                getBlockPos().getZ()-128
                        )
                ).stream().filter(entity -> entity instanceof LivingEntity).map(entity -> (LivingEntity) entity).collect(Collectors.toList()), TargetingConditions.forNonCombat().range(16.0D).ignoreInvisibilityTesting(), null, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ()
        );

        if(nearestEntity == null){
            return;
        }

        entityX = nearestEntity.getX();
        entityY = (nearestEntity.getEyeY()+nearestEntity.getY())/2;
        entityZ = nearestEntity.getZ();
        g = ((inventory.getStackInSlot(0) == ItemStack.EMPTY) ? 1.2 : PotatoProjectileTypeManager.getTypeForStack(inventory.getStackInSlot(0)).get().getGravityMultiplier());
        v = ((inventory.getStackInSlot(0) == ItemStack.EMPTY) ? 9 : PotatoProjectileTypeManager.getTypeForStack(inventory.getStackInSlot(0)).get().getVelocityMultiplier() * 10);
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

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        compound.put("item", inventory.serializeNBT());
        super.write(compound, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        if (compound.contains("item")){
            inventory.deserializeNBT(compound.getCompound("item"));
        }
        super.read(compound, clientPacket);
    }
}
