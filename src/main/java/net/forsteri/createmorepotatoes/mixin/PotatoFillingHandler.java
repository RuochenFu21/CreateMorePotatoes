package net.forsteri.createmorepotatoes.mixin;

import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.fluids.actors.GenericItemFilling;
import com.simibubi.create.content.contraptions.fluids.potion.PotionFluidHandler;
import com.simibubi.create.foundation.fluid.FluidHelper;
import net.forsteri.createmorepotatoes.entry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(GenericItemFilling.class)
public abstract class PotatoFillingHandler {
    /**
     * @author Forsteri
     * @reason IDK WHY
     */
    @Overwrite
    public static ItemStack fillItem(Level world, int requiredAmount, ItemStack stack, FluidStack availableFluid) {
        FluidStack toFill = availableFluid.copy();
        toFill.setAmount(requiredAmount);
        availableFluid.shrink(requiredAmount);

        if (stack.getItem() == Items.GLASS_BOTTLE && canFillGlassBottleInternally(toFill)) {
            ItemStack fillBottle;
            Fluid fluid = toFill.getFluid();
            if (FluidHelper.isWater(fluid))
                fillBottle = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER);
            else if (fluid.isSame(AllFluids.TEA.get()))
                fillBottle = AllItems.BUILDERS_TEA.asStack();
            else
                fillBottle = PotionFluidHandler.fillBottle(stack, toFill);
            stack.shrink(1);
            return fillBottle;
        }

        if (stack.getItem() == Items.POTATO && toFill.getFluid().isSame(AllFluids.POTION.get())){
            CompoundTag tag = availableFluid.getOrCreateTag();
            ItemStack potionStack = new ItemStack(ModItems.POTION_POTATO.get());
            PotionUtils.setPotion(potionStack, PotionUtils.getPotion(tag));
            PotionUtils.setCustomEffects(potionStack, PotionUtils.getCustomEffects(tag));
        }

        ItemStack split = stack.copy();
        split.setCount(1);
        LazyOptional<IFluidHandlerItem> capability =
                split.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY);
        IFluidHandlerItem tank = capability.orElse(null);
        if (tank == null)
            return ItemStack.EMPTY;
        tank.fill(toFill, IFluidHandler.FluidAction.EXECUTE);
        ItemStack container = tank.getContainer()
                .copy();
        stack.shrink(1);
        return container;
    }

    private static boolean canFillGlassBottleInternally(FluidStack availableFluid) {
        Fluid fluid = availableFluid.getFluid();
        if (fluid.isSame(Fluids.WATER))
            return true;
        if (fluid.isSame(AllFluids.POTION.get()))
            return true;
        return fluid.isSame(AllFluids.TEA.get());
    }
}
