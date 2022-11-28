package net.forsteri.createmorepotatoes.tileEntity.programmableStationaryPotatoCannon;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.base.KineticBlock;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileTypeManager;
import com.simibubi.create.foundation.block.ITE;
import com.simibubi.create.foundation.item.ItemHelper;
import net.forsteri.createmorepotatoes.entry.ModTileEntities;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("deprecation")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ProgrammableStationaryPotatoCannonBlock extends KineticBlock implements ITE<ProgrammableStationaryPotatoCannonTileEntity> {
    public ProgrammableStationaryPotatoCannonBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public Class<ProgrammableStationaryPotatoCannonTileEntity> getTileEntityClass() {
        return ProgrammableStationaryPotatoCannonTileEntity.class;
    }

    @Override
    public BlockEntityType<? extends ProgrammableStationaryPotatoCannonTileEntity> getTileEntityType() {
        return ModTileEntities.PROGRAMMABLE_STATIONARY_POTATO_CANNON.get();
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
            ItemStack inStationary = te.inventory.getStackInSlot(0);
            if (inStationary.isEmpty() && heldByPlayer.isEmpty())
                return;

            player.setItemInHand(handIn, inStationary);
            te.inventory.setStackInSlot(0, heldByPlayer);
        });

        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() == Direction.Axis.Y;
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() == newState.getBlock())
            return;
        withTileEntityDo(worldIn, pos, te -> ItemHelper.dropContents(worldIn, pos, te.inventory));
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }
}
