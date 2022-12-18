package net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCannon;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileEntity;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileTypeManager;
import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.forsteri.createmorepotatoes.tileEntity.CannonInventoryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Objects;

public class StationaryPotatoCannonTileEntity extends KineticTileEntity{

    protected int timeOut;

    public LazyOptional<IItemHandler> capability;

    public ItemStackHandler inventory;

    public StationaryPotatoCannonTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        inventory = new ItemStackHandler(1);
        capability = LazyOptional.of(() -> new CannonInventoryHandler(inventory));
    }

    @Override
    public void tick() {
        super.tick();
        if (Objects.requireNonNull(getLevel()).hasNeighborSignal(getBlockPos()) && (this.timeOut <= 0) && (this.getSpeed() != 0) && (inventory.getStackInSlot(0) != ItemStack.EMPTY))
        {
            this.shoot();
        }
        timeOut--;
    }

    public void shoot() {
        CreateMorePotatoes.LOGGER.info("SHOOTING");
        PotatoProjectileEntity projectile = AllEntityTypes.POTATO_PROJECTILE.create(Objects.requireNonNull(getLevel()));
        assert projectile != null;
        projectile.setItem(inventory.getStackInSlot(0));
        float xMove = 0;
        float yMove = 0;
        float zMove = 0;
        switch (this.getBlockState().getValue(BlockStateProperties.FACING)){
            case UP -> yMove = 1;
            case DOWN -> yMove = -1;
            case EAST -> xMove = 1;
            case WEST -> xMove = -1;
            case SOUTH -> zMove = 1;
            case NORTH -> zMove = -1;
        }
        projectile.setPos(getBlockPos().getX()+xMove+0.5, getBlockPos().getY()+yMove+0.5, getBlockPos().getZ()+zMove+0.5);
        projectile.setDeltaMovement(xMove * 2 , yMove * 2, zMove * 2);
        getLevel().addFreshEntity(projectile);
        assert PotatoProjectileTypeManager.getTypeForStack(inventory.getStackInSlot(0)).isPresent();
        timeOut = (inventory.getStackInSlot(0) == ItemStack.EMPTY)? 0 : PotatoProjectileTypeManager.getTypeForStack(inventory.getStackInSlot(0)).get().getReloadTicks() /2;
        inventory.getStackInSlot(0).shrink(1);
        if (inventory.getStackInSlot(0).getCount() == 0){
            inventory.setStackInSlot(0, ItemStack.EMPTY.copy());
        }
    }

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
