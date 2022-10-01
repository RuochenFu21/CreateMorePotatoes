package net.forsteri.createmorepotatoes.mixin;

import com.mojang.logging.LogUtils;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PotatoProjectileEntity.class)
public abstract class EffectHandler extends AbstractHurtingProjectile {

    protected EffectHandler(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
        super(p_36833_, p_36834_);
    }

    @Inject(at = @At(value = "HEAD"), method = "onHitEntity(Lnet/minecraft/world/phys/EntityHitResult;)V")
    @OnlyIn(Dist.DEDICATED_SERVER)
    protected void onHit(EntityHitResult ray, CallbackInfo info) {
        LogUtils.getLogger().info(getX() + " " + getY());
        Explosion derp = new Explosion(getLevel(), this, getX(), getY(), getZ(), 3, false, Explosion.BlockInteraction.BREAK);

        derp.explode();
        derp.finalizeExplosion(true);
    }
}
