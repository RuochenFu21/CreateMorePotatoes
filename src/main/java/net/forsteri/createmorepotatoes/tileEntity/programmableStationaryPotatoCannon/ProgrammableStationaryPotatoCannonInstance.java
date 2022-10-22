package net.forsteri.createmorepotatoes.tileEntity.programmableStationaryPotatoCannon;

import com.jozufozu.flywheel.api.InstanceData;
import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.Material;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.KineticTileInstance;
import com.simibubi.create.content.contraptions.base.SingleRotatingInstance;
import com.simibubi.create.content.contraptions.base.flwdata.RotatingData;
import com.simibubi.create.content.contraptions.relays.elementary.ShaftBlock;
import net.forsteri.createmorepotatoes.entry.ModBlockPartial;
import net.forsteri.createmorepotatoes.entry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;

public class ProgrammableStationaryPotatoCannonInstance extends KineticTileInstance<ProgrammableStationaryPotatoCannonTileEntity> {

    protected RotatingData shaftInstance;
    protected ModelData cannonInstance;
    public ProgrammableStationaryPotatoCannonInstance(MaterialManager modelManager, ProgrammableStationaryPotatoCannonTileEntity tile) {
        super(modelManager, tile);
        Material<ModelData> mat = materialManager.defaultSolid()
                .material(Materials.TRANSFORMED);
        Instancer<RotatingData> shaft = getRotatingMaterial().getModel(AllBlockPartials.SHAFT_HALF, blockState, Direction.UP);
        shaftInstance = shaft.createInstance();
        shaftInstance.setRotationAxis(Direction.Axis.Y)
                .setRotationalSpeed(tile.getSpeed())
                .setRotationOffset(getRotationOffset(axis)).setColor(tile)
                .setPosition(getInstancePosition())
                .setBlockLight(world.getBrightness(LightLayer.BLOCK, pos))
                .setSkyLight(world.getBrightness(LightLayer.SKY, pos));

        Instancer<ModelData> cannon = mat.getModel(ModBlocks.STATIONARY_POTATO_CANNON.getDefaultState());
        cannonInstance = cannon.createInstance();
        cannonInstance.setBlockLight(world.getBrightness(LightLayer.BLOCK, pos));

        cannonInstance.loadIdentity()
                .translate(getInstancePosition());

    }
    @Override
    protected void remove() {
        shaftInstance.delete();
    }

    @Override
    public void updateLight() {
        relight(getWorldPosition(), cannonInstance);
    }
}
