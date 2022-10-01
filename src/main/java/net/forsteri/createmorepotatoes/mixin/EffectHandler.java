package net.forsteri.createmorepotatoes.mixin;

import com.mojang.logging.LogUtils;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileEntity;
import net.forsteri.createmorepotatoes.item.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.forsteri.createmorepotatoes.item.ModItems;

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

        if (true) {
            Explosion derp = new Explosion(getLevel(), this, getX(), getY(), getZ(), 3, false, Explosion.BlockInteraction.BREAK);
            if (!level.isClientSide()) {
                derp.explode();
            }
            derp.finalizeExplosion(true);
        }
    }
}
