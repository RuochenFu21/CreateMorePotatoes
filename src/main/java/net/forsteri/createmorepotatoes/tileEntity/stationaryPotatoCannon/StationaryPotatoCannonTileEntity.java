package net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCannon;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

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
            this.timeOut = 10;
        }
        timeOut--;
    }

    public void shoot(){
        CreateMorePotatoes.LOGGER.info("SHOOTING");
    }


}
