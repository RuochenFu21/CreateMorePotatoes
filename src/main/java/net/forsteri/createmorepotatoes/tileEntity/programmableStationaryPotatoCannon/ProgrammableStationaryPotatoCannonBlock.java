package net.forsteri.createmorepotatoes.tileEntity.programmableStationaryPotatoCannon;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.base.HorizontalKineticBlock;
import com.simibubi.create.content.contraptions.base.KineticBlock;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileTypeManager;
import com.simibubi.create.foundation.block.ITE;
import net.forsteri.createmorepotatoes.entry.ModTileEntities;
import net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCannon.StationaryPotatoCannonTileEntity;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ProgrammableStationaryPotatoCannonBlock extends KineticBlock implements ITE<StationaryPotatoCannonTileEntity> {
    public ProgrammableStationaryPotatoCannonBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public Class<StationaryPotatoCannonTileEntity> getTileEntityClass() {
        return StationaryPotatoCannonTileEntity.class;
    }

    @Override
    public BlockEntityType<? extends StationaryPotatoCannonTileEntity> getTileEntityType() {
        return ModTileEntities.STATIONARY_POTATO_CANNON.get();
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn,
                                 BlockHitResult hit) {
        ItemStack heldByPlayer = player.getItemInHand(handIn)
                .copy();
        if (AllItems.WRENCH.isIn(heldByPlayer))
            return InteractionResult.PASS;

        if (worldIn.isClientSide)
            return InteractionResult.SUCCESS;
        if (PotatoProjectileTypeManager.getTypeForStack(heldByPlayer).isEmpty() && !heldByPlayer.isEmpty()){
            return InteractionResult.PASS;
        }

        withTileEntityDo(worldIn, pos, te -> {
            ItemStack inStationary = te.stack
                    .copy();
            if (inStationary.isEmpty() && heldByPlayer.isEmpty())
                return;

            player.setItemInHand(handIn, inStationary);
            te.stack = heldByPlayer;
        });

        return InteractionResult.SUCCESS;
    }
}
