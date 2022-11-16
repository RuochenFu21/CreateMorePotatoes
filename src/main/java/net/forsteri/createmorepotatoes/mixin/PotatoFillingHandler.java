package net.forsteri.createmorepotatoes.mixin;

import com.simibubi.create.AllFluids;
import com.simibubi.create.content.contraptions.fluids.actors.GenericItemFilling;
import com.simibubi.create.foundation.fluid.FluidHelper;
import net.forsteri.createmorepotatoes.entry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GenericItemFilling.class)
public class PotatoFillingHandler {

    @Inject(
            at = @At(value = "HEAD"),
            method = "fillItem(Lnet/minecraft/world/level/Level;ILnet/minecraft/world/item/ItemStack;Lnet/minecraftforge/fluids/FluidStack;)Lnet/minecraft/world/item/ItemStack;",
            cancellable = true)
    public static void fillItem(Level world, int requiredAmount, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<ItemStack> info){
        if (stack.getItem() == Items.POTATO && availableFluid.copy().getFluid().isSame(AllFluids.POTION.get())) {
            ItemStack fillBottle = ItemStack.EMPTY;
            Fluid fluid = availableFluid.copy().getFluid();
            if (FluidHelper.isWater(fluid))
                fillBottle = Items.POTATO.asItem().getDefaultInstance();
            else {
                CompoundTag tag = availableFluid.getOrCreateTag();
                ItemStack potionStack = new ItemStack(ModItems.POTION_POTATO.get());
                PotionUtils.setPotion(potionStack, PotionUtils.getPotion(tag));
                PotionUtils.setCustomEffects(potionStack, PotionUtils.getCustomEffects(tag));
            }
            stack.shrink(1);
            info.cancel();
            info.setReturnValue(fillBottle);
        }
    }
}
