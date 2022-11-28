package net.forsteri.createmorepotatoes.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.content.curiosities.weapons.PotatoProjectileEntity;

import net.forsteri.createmorepotatoes.entry.ModItems;
import net.forsteri.createmorepotatoes.item.ExplosivePotionPotatoItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

@Mixin(PotatoProjectileEntity.class)
public abstract class EffectHandler extends AbstractHurtingProjectile {

	@Shadow
	protected ItemStack stack;

	protected EffectHandler(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
		super(p_36833_, p_36834_);
	}

	@Inject(at = @At(value = "HEAD"), method = "onHitEntity(Lnet/minecraft/world/phys/EntityHitResult;)V")
	protected void onHit(EntityHitResult ray, CallbackInfo info) {
		if (stack.is(ModItems.EXPLOSIVE_POTATO.get())) {
			Explosion derp = new Explosion(getLevel(), this, getX(), getY(), getZ(), 3, false,
					Explosion.BlockInteraction.BREAK);
			if (!level.isClientSide()) {
				derp.explode();
			}
			derp.finalizeExplosion(true);
		}

		if (stack.is(ModItems.POTION_POTATO.get())) {
			for (MobEffectInstance Effect : PotionUtils.getMobEffects(stack)) {
				((LivingEntity) ray.getEntity()).addEffect(new MobEffectInstance(Effect));
			}
		}

		if (stack.is(ModItems.EXPLOSIVE_POTION_POTATO.get())) {
			ExplosivePotionPotatoItem.makeAreaOfEffectCloud(stack, PotionUtils.getPotion(stack), getLevel(), getX(),
					getY(), getZ(), getOwner());
			Explosion derp = new Explosion(getLevel(), this, getX(), getY(), getZ(), 3, false,
					Explosion.BlockInteraction.NONE);
			derp.finalizeExplosion(true);
		}

		if (stack.is(ModItems.FLAME_POTATO.get())) {
			ray.getEntity().setSecondsOnFire(10);
		}

		if (stack.is(ModItems.FROSTY_POTATO.get())) {
			ray.getEntity().makeStuckInBlock(Blocks.POWDER_SNOW.defaultBlockState(), new Vec3(0.9F, 1.5D, 0.9F));
			if (level.isClientSide) {
				Random random = level.getRandom();
				boolean flag = ray.getEntity().xOld != ray.getEntity().getX()
						|| ray.getEntity().zOld != ray.getEntity().getZ();
				if (flag && random.nextBoolean()) {
					level.addParticle(ParticleTypes.SNOWFLAKE, ray.getEntity().getX(), ray.getLocation().y + 1,
							ray.getEntity().getZ(), Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336F, 0.05F,
							Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336F);
				}
			}

			ray.getEntity().setTicksFrozen(ray.getEntity().getTicksRequiredToFreeze() + 140);

			if (!level.isClientSide) {
				if (ray.getEntity().isOnFire()
						&& (level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)
								|| ray.getEntity() instanceof Player)
						&& ray.getEntity().mayInteract(level, ray.getEntity().blockPosition())) {
					level.destroyBlock(ray.getEntity().blockPosition(), false);
				}

				ray.getEntity().setSharedFlagOnFire(false);
			}
		}

		if (stack.is(Items.ENDER_PEARL)) {
			if (this.getOwner() != null) {
				this.getOwner().teleportTo(ray.getEntity().getX(), ray.getEntity().getY(), ray.getEntity().getZ());
			}
		}
	}

	@Inject(at = @At(value = "HEAD"), method = "onHitBlock(Lnet/minecraft/world/phys/BlockHitResult;)V")
	protected void onHitBlock(BlockHitResult ray, CallbackInfo info) {
		if (stack.is(ModItems.EXPLOSIVE_POTATO.get())) {
			Explosion derp = new Explosion(getLevel(), this, getX(), getY(), getZ(), 3, false,
					Explosion.BlockInteraction.BREAK);
			if (!level.isClientSide()) {
				derp.explode();
			}
			derp.finalizeExplosion(true);
		}
		if (stack.is(ModItems.EXPLOSIVE_POTION_POTATO.get())) {
			ExplosivePotionPotatoItem.makeAreaOfEffectCloud(stack, PotionUtils.getPotion(stack), getLevel(), getX(),
					getY(), getZ(), getOwner());
			Explosion derp = new Explosion(getLevel(), this, getX(), getY(), getZ(), 3, false,
					Explosion.BlockInteraction.NONE);
			derp.finalizeExplosion(true);
		}
		if (stack.is(ModItems.FLAME_POTATO.get())) {
			level.explode(null, getX(), getY(), getZ(), 1, true, Explosion.BlockInteraction.NONE);
		}
		if (stack.is(ModItems.FROSTY_POTATO.get()) || stack.is(Items.PACKED_ICE)) {
			BlockState blockstate = Blocks.FROSTED_ICE.defaultBlockState();
			float f = (float) Math.min(16, 2 + 1);
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
			BlockPos pPos = ray.getBlockPos().above(1);
			// LivingEntity pLiving = (LivingEntity) getOwner();
			Level pLevel = getLevel();

			for (BlockPos blockpos : BlockPos.betweenClosed(pPos.offset(-f, -1.0D, -f), pPos.offset(f, -1.0D, f))) {
				if (blockpos.closerToCenterThan(ray.getLocation().add(0, 0.5, 0), f)) {
					blockpos$mutableblockpos.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
					BlockState blockstate1 = pLevel.getBlockState(blockpos$mutableblockpos);
					if (blockstate1.isAir()) {
						BlockState blockstate2 = pLevel.getBlockState(blockpos);
						boolean isFull = blockstate2.getBlock() == Blocks.WATER
								&& blockstate2.getValue(LiquidBlock.LEVEL) == 0;
						if (blockstate2.getMaterial() == Material.WATER && isFull
								&& blockstate.canSurvive(pLevel, blockpos)
								&& pLevel.isUnobstructed(blockstate, blockpos, CollisionContext.empty())) {
							pLevel.setBlockAndUpdate(blockpos, blockstate);
							pLevel.scheduleTick(blockpos, Blocks.FROSTED_ICE, 90);
						}
					}
				}
			}
		}

		if (stack.is(Items.ENDER_PEARL)) {
			if (this.getOwner() != null) {
				Vec3 loc = ray.getLocation().add(ray.getDirection().getStepX(), ray.getDirection().getStepY(),
						ray.getDirection().getStepZ());
				this.getOwner().teleportTo(loc.x, loc.y, loc.z);
			}
		}

	}

	@Inject(at = @At(value = "HEAD"), method = "tick()V")
	protected void OnTick(CallbackInfo info) {
		if (stack.is(ModItems.BAG_OF_POTATOES.get())) {
			stack = new ItemStack(Items.POTATO);
		}
	}
}
