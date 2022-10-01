package net.forsteri.createmorepotatoes.mixin;

import com.mojang.logging.LogUtils;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileEntity;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PotatoProjectileEntity.class)
public class EffectHandler {
    @Inject(at = @At(value = "HEAD"), method = "onHitEntity(Lnet/minecraft/world/phys/EntityHitResult;)V", cancellable = true)
    protected void onHit(EntityHitResult ray, CallbackInfo info) {

        LogUtils.getLogger().info("HI, Im random");
    }
}
