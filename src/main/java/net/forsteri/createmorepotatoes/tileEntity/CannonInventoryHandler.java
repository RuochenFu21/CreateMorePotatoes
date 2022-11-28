package net.forsteri.createmorepotatoes.tileEntity;

import com.simibubi.create.content.curiosities.weapons.PotatoProjectileTypeManager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;

public class CannonInventoryHandler extends CombinedInvWrapper {

    ItemStackHandler inventory;
    public CannonInventoryHandler(ItemStackHandler inventory) {
        super(inventory);
        this.inventory = inventory;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return PotatoProjectileTypeManager.getTypeForStack(stack).isPresent();
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (!isItemValid(slot, stack))
            return stack;
        return super.insertItem(slot, stack, simulate);
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        return super.extractItem(slot, amount, simulate);
    }
}

