package net.forsteri.createmorepotatoes.mixin;

import com.mojang.logging.LogUtils;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileEntity;
import net.forsteri.createmorepotatoes.entry.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import java.util.Objects;

@Mixin(PotatoProjectileEntity.class)
public abstract class EffectHandler extends AbstractHurtingProjectile {

    @Shadow protected ItemStack stack;

    @Shadow protected Entity stuckEntity;

    protected EffectHandler(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }

    @Inject(at = @At(value = "HEAD"), method = "onHitEntity(Lnet/minecraft/world/phys/EntityHitResult;)V")
    protected void onHit(EntityHitResult ray, CallbackInfo info) {
        LogUtils.getLogger().info(Objects.requireNonNull(stack.getItem().getRegistryName()).toString());

        if (stack.is(ModItems.EXPLOSIVE_POTATO.get())) {
            Explosion derp = new Explosion(getLevel(), this, getX(), getY(), getZ(), 3, false, Explosion.BlockInteraction.BREAK);
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
    }

    @Inject(at = @At(value = "HEAD"), method = "onHitBlock(Lnet/minecraft/world/phys/BlockHitResult;)V")
    protected void onHitBlock(BlockHitResult ray, CallbackInfo info) {
        LogUtils.getLogger().info(Objects.requireNonNull(stack.getItem().getRegistryName()).toString());

        if (stack.is(ModItems.EXPLOSIVE_POTATO.get())) {
            Explosion derp = new Explosion(getLevel(), this, getX(), getY(), getZ(), 3, false, Explosion.BlockInteraction.BREAK);
            if (!level.isClientSide()) {
                derp.explode();
            }
            derp.finalizeExplosion(true);
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "tick()V")
    protected void OnTick(CallbackInfo info) {
        if (Objects.requireNonNull(stack.getItem().getRegistryName()).toString().equals("createmorepotatoes:bag_of_potatoes")) {
            stack = new ItemStack(Items.POTATO);
            LogUtils.getLogger().info("converted!");
        }
    }
}
