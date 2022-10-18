package net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCanon;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.base.DirectionalAxisKineticBlock;
import com.simibubi.create.content.contraptions.base.DirectionalKineticBlock;
import com.simibubi.create.content.contraptions.base.KineticBlock;
import com.simibubi.create.content.contraptions.base.RotatedPillarKineticBlock;
import com.simibubi.create.content.contraptions.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.contraptions.relays.gauge.GaugeInstance;
import com.simibubi.create.content.curiosities.weapons.PotatoProjectileTypeManager;
import com.simibubi.create.foundation.block.ITE;
import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.forsteri.createmorepotatoes.entry.ModTileEntities;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.Random;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class StationaryPotatoCanonBlock extends DirectionalAxisKineticBlock implements ITE<StationaryPotatoCanonTileEntity> {

    public StationaryPotatoCanonBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Class<StationaryPotatoCanonTileEntity> getTileEntityClass() {
        return StationaryPotatoCanonTileEntity.class;
    }

    @Override
    public BlockEntityType<? extends StationaryPotatoCanonTileEntity> getTileEntityType() {
        return ModTileEntities.STATIONARY_POTATO_CANON.get();
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn,
                                 BlockHitResult hit) {
        ItemStack heldByPlayer = player.getItemInHand(handIn)
                .copy();
        if (AllItems.WRENCH.isIn(heldByPlayer))
            return InteractionResult.PASS;

        if (hit.getDirection() == state.getValue(FACING).getOpposite())
            return InteractionResult.PASS;
        if (worldIn.isClientSide)
            return InteractionResult.SUCCESS;
        if (PotatoProjectileTypeManager.getTypeForStack(heldByPlayer).isEmpty()){
            return InteractionResult.PASS;
        }

        withTileEntityDo(worldIn, pos, te -> {
            ItemStack inStationary = te.stack
                    .copy();
            if (inStationary.isEmpty() && heldByPlayer.isEmpty())
                return;

            player.setItemInHand(handIn, inStationary);
            te.stack = heldByPlayer;
        });

        return InteractionResult.SUCCESS;
    }
}
