package net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCannon;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.content.contraptions.base.KineticTileInstance;
import com.simibubi.create.content.contraptions.base.flwdata.RotatingData;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LightLayer;

public class StationaryPotatoCannonInstance extends KineticTileInstance<StationaryPotatoCannonTileEntity> {
    public StationaryPotatoCannonInstance(MaterialManager dispatcher, StationaryPotatoCannonTileEntity tile) {
        super(dispatcher, tile);
        Instancer<RotatingData> shaft = getRotatingMaterial().getModel(AllBlockPartials.SHAFT_HALF, blockState, Direction.UP);
        shaft.createInstance()
                .setRotationAxis(Direction.Axis.Y)
                .setRotationalSpeed(blockEntity.getSpeed())
                .setRotationOffset(getRotationOffset(axis)).setColor(tile)
                .setPosition(getInstancePosition())
                .setBlockLight(world.getBrightness(LightLayer.BLOCK, pos))
                .setSkyLight(world.getBrightness(LightLayer.SKY, pos));
    }

    @Override
    protected void remove() {

    }
}
