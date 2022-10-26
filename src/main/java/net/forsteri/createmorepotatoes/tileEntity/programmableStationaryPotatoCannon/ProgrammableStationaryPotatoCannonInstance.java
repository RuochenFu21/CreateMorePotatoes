package net.forsteri.createmorepotatoes.tileEntity.programmableStationaryPotatoCannon;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.Material;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.simibubi.create.content.contraptions.base.SingleRotatingInstance;
import net.forsteri.createmorepotatoes.entry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;

public class ProgrammableStationaryPotatoCannonInstance extends SingleRotatingInstance implements DynamicInstance {
    protected ModelData cannonInstance;

    protected ProgrammableStationaryPotatoCannonTileEntity tileEntity;

    protected float phiLastRender = 0;
    protected float thetaLastRender = 0;
    public ProgrammableStationaryPotatoCannonInstance(MaterialManager modelManager, ProgrammableStationaryPotatoCannonTileEntity tile) {
        super(modelManager, tile);
        this.tileEntity = tile;

        Material<ModelData> mat = materialManager.defaultSolid()
                .material(Materials.TRANSFORMED);

        Instancer<ModelData> cannon = mat.getModel(ModBlocks.STATIONARY_POTATO_CANNON.getDefaultState());
        cannonInstance = cannon.createInstance();

        cannonInstance.loadIdentity()
                .translate(getInstancePosition())
                .setBlockLight(world.getBrightness(LightLayer.BLOCK, pos));

    }
    @Override
    public void remove() {
        super.remove();
        cannonInstance.delete();
    }

    @Override
    public void updateLight() {
        super.updateLight();
        relight(getWorldPosition(), cannonInstance);
    }

    @Override
    public void beginFrame() {
        float phi = (float) tileEntity.getPhi();
        float theta = (float) tileEntity.getTheta();

        cannonInstance.rotateCentered(Direction.EAST, -thetaLastRender);
        cannonInstance.rotateCentered(Direction.UP, -phiLastRender);

        cannonInstance.rotateCentered(Direction.UP, phi);
        cannonInstance.rotateCentered(Direction.EAST, theta);


        phiLastRender = phi;
        thetaLastRender = theta;
    }
    @Override
    protected BlockState getRenderedBlockState() {
        return shaft();
    }
}
