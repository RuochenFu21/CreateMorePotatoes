package net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCannon;

import java.util.Objects;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileEntity;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileTypeManager;

import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

@SuppressWarnings("unused")
public class StationaryPotatoCannonTileEntity extends KineticTileEntity {

	protected int timeOut;

	private static final ResourceLocation CHANNEL = CreateMorePotatoes.asResource("potato_cannon");

	public final SingleVariantStorage<ItemVariant> storage = new SingleVariantStorage<>() {

		@Override
		protected ItemVariant getBlankVariant() {
			return ItemVariant.blank();
		}

		@Override
		protected long getCapacity(ItemVariant variant) {
			return 64;
		}

		@Override
		protected void onFinalCommit() {
			setChanged();
			// if (!level.isClientSide())
			// PlayerLookup.tracking(StationaryPotatoCannonTileEntity.this).forEach(player
			// -> ServerPlayNetworking
			// .send(player, CHANNEL, PacketByteBufs.create()));
		}

	};

	public StationaryPotatoCannonTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
		super(typeIn, pos, state);
	}

	@Override
	public void tick() {
		super.tick();
		if (level.hasNeighborSignal(getBlockPos()) && this.timeOut <= 0
				&& this.getSpeed() != 0 && !level.isClientSide())
			this.shoot();
		timeOut--;
	}

	public SingleVariantStorage<ItemVariant> getStorage() {
		return this.storage;
	}

	public ItemStack toStack() {
		return this.toStack((int) this.storage.getAmount());
	}

	public ItemStack toStack(int count) {
		return this.storage.variant.toStack(count);
	}

	public void shoot() {
		// CreateMorePotatoes.LOGGER.info("SHOOTING");
		if (this.storage.getAmount() == 0)
			return;
		PotatoProjectileEntity projectile = AllEntityTypes.POTATO_PROJECTILE.create(Objects.requireNonNull(getLevel()));
		assert projectile != null;
		projectile.setItem(this.toStack(1));
		float xMove = 0;
		float yMove = 0;
		float zMove = 0;

		switch (this.getBlockState().getValue(BlockStateProperties.FACING)) {
			case UP -> yMove = 1;
			case DOWN -> yMove = -1;
			case EAST -> xMove = 1;
			case WEST -> xMove = -1;
			case SOUTH -> zMove = 1;
			case NORTH -> zMove = -1;
		}

		projectile.setPos(getBlockPos().getX() + xMove + 0.5, getBlockPos().getY() + yMove + 0.5,
				getBlockPos().getZ() + zMove + 0.5);
		projectile.setDeltaMovement(xMove * 2, yMove * 2, zMove * 2);
		this.getLevel().addFreshEntity(projectile);
		assert PotatoProjectileTypeManager.getTypeForStack(this.toStack(1)).isPresent();
		timeOut = this.storage.getAmount() == 0 ? 0
				: PotatoProjectileTypeManager.getTypeForStack(this.toStack(1)).get().getReloadTicks() / 2;
		TransferUtil.extractAnyItem(storage, 1);
	}

	@Override
	protected void read(CompoundTag compound, boolean clientPacket) {
		super.read(compound, clientPacket);
		if (compound.contains("item"))
			storage.variant = ItemVariant.fromNbt(compound.getCompound("item"));
		if (compound.contains("count"))
			storage.amount = compound.getInt("count");
		if (compound.contains("timeout"))
			timeOut = compound.getInt("timeout");
	}

	@Override
	protected void write(CompoundTag compound, boolean clientPacket) {
		super.write(compound, clientPacket);
		compound.put("item", storage.variant.toNbt());
		compound.putInt("count", (int) storage.amount);
		compound.putInt("timeout", timeOut);
	}

}
