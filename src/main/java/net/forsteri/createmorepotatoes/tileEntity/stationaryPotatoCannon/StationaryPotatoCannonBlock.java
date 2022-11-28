package net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCannon;

import javax.annotation.ParametersAreNonnullByDefault;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.base.DirectionalAxisKineticBlock;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileTypeManager;
import com.simibubi.create.foundation.block.ITE;

import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.forsteri.createmorepotatoes.entry.ModTileEntities;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
// @SuppressWarnings("deprecation")
public class StationaryPotatoCannonBlock extends DirectionalAxisKineticBlock
		implements ITE<StationaryPotatoCannonTileEntity> {

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
		if (worldIn.isClientSide())
			return InteractionResult.SUCCESS;
		if (PotatoProjectileTypeManager.getTypeForStack(heldByPlayer).isEmpty() && !heldByPlayer.isEmpty()) {
			return InteractionResult.PASS;
		}

		withTileEntityDo(worldIn, pos, te -> {
			if ((te.storage.getAmount() == 0 && heldByPlayer.isEmpty())
					|| !StationaryPotatoCannonTileEntity.canInsert(ItemVariant.of(heldByPlayer)))
				return;

			if (te.storage.getAmount() > 0)
				player.setItemInHand(handIn,
						new ItemStack(te.storage.getResource().getItem(), (int) te.storage.getAmount()));
			else
				player.setItemInHand(handIn, ItemStack.EMPTY);
			TransferUtil.extractAnyItem(te.storage, 64);
			if (!heldByPlayer.isEmpty())
				TransferUtil.insertItem(te.storage, heldByPlayer);
		});

		return InteractionResult.SUCCESS;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
		if ((!blockState.is(blockState2.getBlock()))
				&& level.getBlockEntity(blockPos) instanceof StationaryPotatoCannonTileEntity be)
			Containers.dropItemStack(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), be.toStack());
		super.onRemove(blockState, level, blockPos, blockState2, bl);
	}
}
