package net.forsteri.createmorepotatoes.tileEntity;

import com.simibubi.create.content.curiosities.weapons.PotatoProjectileTypeManager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;

public class CannonInventoryHandler extends CombinedInvWrapper {
    public CannonInventoryHandler(ItemStackHandler inventory) {
        super(inventory);
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return PotatoProjectileTypeManager.getTypeForStack(stack).isPresent();
    }
}

