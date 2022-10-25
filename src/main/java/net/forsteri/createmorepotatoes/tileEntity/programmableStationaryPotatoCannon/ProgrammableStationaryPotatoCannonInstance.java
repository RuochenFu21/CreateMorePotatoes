package net.forsteri.createmorepotatoes.tileEntity.programmableStationaryPotatoCannon;

import com.jozufozu.flywheel.api.InstanceData;
import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.Material;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.KineticTileInstance;
import com.simibubi.create.content.contraptions.base.SingleRotatingInstance;
import com.simibubi.create.content.contraptions.base.flwdata.RotatingData;
import com.simibubi.create.content.contraptions.relays.elementary.ShaftBlock;
import net.forsteri.createmorepotatoes.CreateMorePotatoes;
import net.forsteri.createmorepotatoes.entry.ModBlockPartial;
import net.forsteri.createmorepotatoes.entry.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public class ProgrammableStationaryPotatoCannonInstance extends SingleRotatingInstance implements DynamicInstance {
    protected ModelData cannonInstance;

    protected double theta_now = 0;
    public ProgrammableStationaryPotatoCannonInstance(MaterialManager modelManager, ProgrammableStationaryPotatoCannonTileEntity tile) {
        super(modelManager, tile);

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
        double theta = ((ProgrammableStationaryPotatoCannonTileEntity) Objects.requireNonNull(world.getExistingBlockEntity(getWorldPosition()))).theta;
        cannonInstance.rotateCentered(Direction.UP, (float) ((float) theta-theta_now));
        CreateMorePotatoes.LOGGER.info("framing! theta now:" + theta_now + ". theta we got: " + theta);
//        cannonInstance.rotateCentered(Direction.UP, (float) ((ProgrammableStationaryPotatoCannonTileEntity) blockEntity).theta);
        theta_now = theta;
    }
    @Override
    protected BlockState getRenderedBlockState() {
        return shaft();
    }
}
