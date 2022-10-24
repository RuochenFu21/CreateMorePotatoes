package net.forsteri.createmorepotatoes.tileEntity.programmableStationaryPotatoCannon;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileEntity;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileTypeManager;
import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Objects;

public class ProgrammableStationaryPotatoCannonTileEntity extends KineticTileEntity {

    protected int timeOut;

    public ItemStack stack = ItemStack.EMPTY;

    public ProgrammableStationaryPotatoCannonTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        CreateMorePotatoes.LOGGER.info("TICKING");
        if (Objects.requireNonNull(getLevel()).hasNeighborSignal(getBlockPos()) && (this.timeOut <= 0) && (this.getSpeed() != 0) && (stack != ItemStack.EMPTY))
        {
            this.shoot();
        }
        timeOut--;
    }

    public void shoot() {
        CreateMorePotatoes.LOGGER.info("SHOOTING");
        PotatoProjectileEntity projectile = AllEntityTypes.POTATO_PROJECTILE.create(Objects.requireNonNull(getLevel()));
        assert projectile != null;
        projectile.setItem(stack);
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
            default -> {}
        }
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
}
