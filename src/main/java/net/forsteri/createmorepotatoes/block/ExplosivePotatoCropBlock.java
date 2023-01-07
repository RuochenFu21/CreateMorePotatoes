package net.forsteri.createmorepotatoes.block;

import net.forsteri.createmorepotatoes.entry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PotatoBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ExplosivePotatoCropBlock extends PotatoBlock {
    public ExplosivePotatoCropBlock(Properties p_55198_) {
        super(p_55198_);
    }

    protected @NotNull ItemLike getBaseSeedId() {
        return ModItems.EXPLOSIVE_POTATO.get();
    }

    @Override
    public void entityInside(@NotNull BlockState pState, @NotNull Level pLevel, BlockPos pPos, @NotNull Entity pEntity) {
        Explosion derp = new Explosion(pLevel, null, pPos.getX(), pPos.getY(), pPos.getZ(), 3, false, Explosion.BlockInteraction.BREAK);
        if (!pLevel.isClientSide()) {
            derp.explode();
        }
        derp.finalizeExplosion(true);

        if (pEntity instanceof Ravager && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(pLevel, pEntity)) {
            pLevel.destroyBlock(pPos, true, pEntity);
        }

        super.entityInside(pState, pLevel, pPos, pEntity);
    }
}
