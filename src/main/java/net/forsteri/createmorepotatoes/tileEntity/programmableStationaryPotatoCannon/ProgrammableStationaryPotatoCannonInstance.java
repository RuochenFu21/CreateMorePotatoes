package net.forsteri.createmorepotatoes.tileEntity.programmableStationaryPotatoCannon;

import com.jozufozu.flywheel.api.InstanceData;
import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.KineticTileInstance;
import com.simibubi.create.content.contraptions.base.SingleRotatingInstance;
import com.simibubi.create.content.contraptions.base.flwdata.RotatingData;
import com.simibubi.create.content.contraptions.relays.elementary.ShaftBlock;
import net.forsteri.createmorepotatoes.entry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;

public class ProgrammableStationaryPotatoCannonInstance extends KineticTileInstance<ProgrammableStationaryPotatoCannonTileEntity> {

    protected RotatingData shaftInstance;
    public ProgrammableStationaryPotatoCannonInstance(MaterialManager modelManager, ProgrammableStationaryPotatoCannonTileEntity tile) {
        super(modelManager, tile);
        Instancer<RotatingData> shaft = getRotatingMaterial().getModel(AllBlockPartials.SHAFT_HALF, blockState, Direction.UP);
        shaftInstance = shaft.createInstance();
        shaftInstance.setRotationAxis(Direction.Axis.Y)
                .setRotationalSpeed(tile.getSpeed())
                .setRotationOffset(getRotationOffset(axis)).setColor(tile)
                .setPosition(getInstancePosition())
                .setBlockLight(world.getBrightness(LightLayer.BLOCK, pos))
                .setSkyLight(world.getBrightness(LightLayer.SKY, pos));
    }
    @Override
    protected void remove() {
        shaftInstance.delete();
    }
}
