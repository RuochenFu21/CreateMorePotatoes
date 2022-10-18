package net.forsteri.createmorepotatoes.tileEntity.stationaryPotatoCanon;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.KineticTileInstance;
import com.simibubi.create.content.contraptions.base.flwdata.RotatingData;
import com.simibubi.create.content.contraptions.relays.encased.ShaftInstance;
import net.minecraft.world.level.block.state.BlockState;

public class StationaryPotatoCanonInstance extends KineticTileInstance<StationaryPotatoCanonTileEntity> {

    public StationaryPotatoCanonInstance(MaterialManager dispatcher, StationaryPotatoCanonTileEntity tile) {
        super(dispatcher, tile);
    }

    protected RotatingData rotatingModel;

    @Override
    public void init() {
        rotatingModel = setup(getModel().createInstance());
    }

    @Override
    public void update() {
        updateRotation(rotatingModel);
    }

    @Override
    public void updateLight() {
        relight(pos, rotatingModel);
    }

    @Override
    public void remove() {
        rotatingModel.delete();
    }

    protected BlockState getRenderedBlockState() {
        return shaft();
    }

    protected Instancer<RotatingData> getModel() {
        return getRotatingMaterial().getModel(getRenderedBlockState());
    }

}
