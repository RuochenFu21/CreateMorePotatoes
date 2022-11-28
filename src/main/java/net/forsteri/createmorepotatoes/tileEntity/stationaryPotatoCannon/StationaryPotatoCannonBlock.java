package net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCannon;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.base.DirectionalAxisKineticBlock;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileTypeManager;
import com.simibubi.create.foundation.block.ITE;
import net.forsteri.createmorepotatoes.entry.ModTileEntities;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
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
@SuppressWarnings("deprecation")
public class StationaryPotatoCannonBlock extends DirectionalAxisKineticBlock implements ITE<StationaryPotatoCannonTileEntity> {

    public StationaryPotatoCannonBlock(Properties properties) {
        super(properties);
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

        if (hit.getDirection() == state.getValue(FACING).getOpposite())
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
}
