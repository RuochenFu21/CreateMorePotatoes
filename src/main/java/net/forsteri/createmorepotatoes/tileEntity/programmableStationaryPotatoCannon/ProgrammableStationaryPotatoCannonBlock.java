package net.forsteri.createmorepotatoes.tileEntity.programmableStationaryPotatoCannon;

import javax.annotation.ParametersAreNonnullByDefault;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.base.KineticBlock;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileTypeManager;
import com.simibubi.create.foundation.block.ITE;

import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.forsteri.createmorepotatoes.entry.ModTileEntities;
import net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCannon.StationaryPotatoCannonTileEntity;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

// @SuppressWarnings("deprecation")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ProgrammableStationaryPotatoCannonBlock extends KineticBlock
		implements ITE<ProgrammableStationaryPotatoCannonTileEntity> {
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
	public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
		return face.getAxis() == Direction.Axis.Y;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
		if ((!blockState.is(blockState2.getBlock()))
				&& level.getBlockEntity(blockPos) instanceof ProgrammableStationaryPotatoCannonTileEntity be)
			Containers.dropItemStack(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), be.toStack());
		super.onRemove(blockState, level, blockPos, blockState2, bl);
	}
}
