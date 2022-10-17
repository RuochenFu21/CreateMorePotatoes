package net.forsteri.createmorepotatoes.entry;

import com.simibubi.create.foundation.block.BlockStressDefaults;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.forsteri.createmorepotatoes.block.ExplosivePotatoCropBlock;
import net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCanon.StationaryPotatoCanonBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.simibubi.create.AllMovementBehaviours.movementBehaviour;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CreateMorePotatoes.MOD_ID);

    public static final RegistryObject<Block> EXPLOSIVE_POTATO_CROP = BLOCKS.register("explosive_potato_crop",
            () -> new ExplosivePotatoCropBlock(BlockBehaviour.Properties.copy(Blocks.POTATOES).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registryBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }

    private static final CreateRegistrate REGISTRATE = CreateMorePotatoes
            .registrate()
            .creativeModeTab(
                    () -> ModCreativeModeTab.MORE_POTATOES_TAB
            );

    public static final BlockEntry<StationaryPotatoCanonBlock> STATIONARY_POTATO_CANON =
            REGISTRATE.block("stationary_potato_canon", StationaryPotatoCanonBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .blockstate(BlockStateGen.axisBlockProvider(true))
                    .item()
                    .transform(customItemModel())
                    .addLayer(() -> RenderType::cutoutMipped)
                    .register();
}