package net.forsteri.createmorepotatoes.entry;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.forsteri.createmorepotatoes.tileEntity.programmableStationaryPotatoCannon.ProgrammableStationaryPotatoCannonInstance;
import net.forsteri.createmorepotatoes.tileEntity.programmableStationaryPotatoCannon.ProgrammableStationaryPotatoCannonRenderer;
import net.forsteri.createmorepotatoes.tileEntity.programmableStationaryPotatoCannon.ProgrammableStationaryPotatoCannonTileEntity;
import net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCannon.StationaryPotatoCannonInstance;
import net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCannon.StationaryPotatoCannonRenderer;
import net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCannon.StationaryPotatoCannonTileEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ModTileEntities {
	private static final CreateRegistrate REGISTRATE = CreateMorePotatoes.registrate();

	public static final BlockEntityEntry<StationaryPotatoCannonTileEntity> STATIONARY_POTATO_CANNON = REGISTRATE
			.tileEntity("stationary_potato_cannon", StationaryPotatoCannonTileEntity::new)
			.instance(() -> StationaryPotatoCannonInstance::new)
			.validBlocks(ModBlocks.STATIONARY_POTATO_CANNON)
			.renderer(() -> StationaryPotatoCannonRenderer::new)
			.register();

	public static final BlockEntityEntry<ProgrammableStationaryPotatoCannonTileEntity> PROGRAMMABLE_STATIONARY_POTATO_CANNON = REGISTRATE
			.tileEntity("potato_turret", ProgrammableStationaryPotatoCannonTileEntity::new)
			.instance(() -> ProgrammableStationaryPotatoCannonInstance::new, false)
			.validBlocks(ModBlocks.PROGRAMMABLE_STATIONARY_POTATO_CANNON_BLOCK)
			.renderer(() -> ProgrammableStationaryPotatoCannonRenderer::new)
			.register();

	public static void register() {
	}

	public static void registerTransfer() {
		ItemStorage.SIDED.registerForBlockEntity((be,
				side) -> isRightDirection(be, side) ? be.storage : null,
				STATIONARY_POTATO_CANNON.get());

		ItemStorage.SIDED.registerForBlockEntity((be,
				side) -> isRightDirection(be, side) ? be.storage : null,
				PROGRAMMABLE_STATIONARY_POTATO_CANNON.get());
	}

	private static boolean isRightDirection(BlockEntity be, Direction direction) {
		return be.getLevel().getBlockState(be.getBlockPos())
				.getValue(BlockStateProperties.FACING).getOpposite() != direction;
	}
}
