package net.forsteri.createmorepotatoes.tileEntity.programmableStationaryPotatoCannon;

import java.util.Objects;
import java.util.stream.Collectors;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileEntity;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileTypeManager;

import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCannon.StationaryPotatoCannonTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class ProgrammableStationaryPotatoCannonTileEntity extends StationaryPotatoCannonTileEntity {

	protected double phi = 0;
	protected double theta = 0;

	protected LivingEntity nearestEntity;

	protected double entityX;

	protected double entityY;

	protected double entityZ;

	protected double g;

	protected double v;

	protected double x;
	protected double y;
	protected double z;
	protected double r;

	public ProgrammableStationaryPotatoCannonTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
		super(typeIn, pos, state);
	}

	@Override
	public void tick() {
		super.tick();
		if (level != null && level.isClientSide) {
			this.calculateDimensions();
		}
		if (Objects.requireNonNull(getLevel()).hasNeighborSignal(getBlockPos()) && (this.timeOut <= 0)
				&& (this.getSpeed() != 0) && (storage.getAmount() > 0)) {
			this.calculateDimensions();
			this.shoot();
		}
		timeOut--;
	}

	@Override
	public void shoot() {
		PotatoProjectileEntity projectile = AllEntityTypes.POTATO_PROJECTILE.create(Objects.requireNonNull(getLevel()));
		assert projectile != null;
		projectile.setItem(toStack(1));
		Vec3 facing = new Vec3(0, 0, -v / 10).yRot((float) phi)/* .xRot((float) theta) */;
		float xMove = (float) facing.x;
		float yMove = (float) Math.sin(getTheta());
		float zMove = (float) facing.z;
		projectile.setPos(getBlockPos().getX() + xMove + 0.5, getBlockPos().getY() + yMove + 0.5,
				getBlockPos().getZ() + zMove + 0.5);
		projectile.setDeltaMovement(xMove, yMove, zMove);
		getLevel().addFreshEntity(projectile);
		assert PotatoProjectileTypeManager.getTypeForStack(toStack(1)).isPresent();
		timeOut = (storage.getAmount() == 0) ? 0
				: PotatoProjectileTypeManager.getTypeForStack(toStack(1)).get().getReloadTicks() / 2;
		TransferUtil.extractAnyItem(storage, 1);
	}

	public double getPhi() {
		return this.phi;
	}

	public double getTheta() {
		return this.theta;
	}

	protected void calculateDimensions() {
		nearestEntity = Objects.requireNonNull(getLevel()).getNearestEntity(
				getLevel().getEntities(
						null, new AABB(
								getBlockPos().getX() + 128,
								getBlockPos().getY() + 256,
								getBlockPos().getZ() + 128,
								getBlockPos().getX() - 128,
								getBlockPos().getY() - 256,
								getBlockPos().getZ() - 128))
						.stream().filter(entity -> entity instanceof LivingEntity).map(entity -> (LivingEntity) entity)
						.collect(Collectors.toList()),
				TargetingConditions.forNonCombat().range(16.0D).ignoreInvisibilityTesting(), null, getBlockPos().getX(),
				getBlockPos().getY(), getBlockPos().getZ());

		if (nearestEntity == null) {
			return;
		}

		entityX = nearestEntity.getX();
		entityY = (nearestEntity.getEyeY() + nearestEntity.getY()) / 2;
		entityZ = nearestEntity.getZ();
		g = (storage.getAmount() == 0 ? 1.2
				: PotatoProjectileTypeManager.getTypeForStack(toStack(1)).get().getGravityMultiplier());
		v = (storage.getAmount() == 0 ? 9
				: PotatoProjectileTypeManager.getTypeForStack(toStack(1)).get().getVelocityMultiplier() * 10);
		x = entityX - getBlockPos().getX() - .5;
		y = entityY - getBlockPos().getY() - .5;
		z = entityZ - getBlockPos().getZ() - .5;
		r = Math.sqrt(x * x + z * z);

		this.phi = Math.atan2(x, z) + Math.PI;
		this.theta = Math.atan2(
				v * v - Math.sqrt(v * v * v * v - g * (g * r * r + 2 * y * v * v)),
				g * r);

	}

	@Override
	protected void read(CompoundTag compound, boolean clientPacket) {
		super.read(compound, clientPacket);
		if (compound.contains("phi"))
			phi = compound.getDouble("phi");
		if (compound.contains("theta"))
			theta = compound.getDouble("theta");
	}

	@Override
	protected void write(CompoundTag compound, boolean clientPacket) {
		super.write(compound, clientPacket);
		compound.putDouble("phi", phi);
		compound.putDouble("theta", theta);
	}
}
