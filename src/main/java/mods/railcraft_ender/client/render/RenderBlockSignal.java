package mods.railcraft_ender.client.render;

import cpw.mods.fml.client.registry.ClientRegistry;
import mods.railcraft.client.render.BlockRenderer;
import mods.railcraft.client.render.ICombinedRenderer;
import mods.railcraft_ender.common.blocks.RailcraftEnderBlocks;
import mods.railcraft_ender.common.blocks.signals.EnumEnderSignal;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderBlockSignal extends BlockRenderer {

    public RenderBlockSignal() {
        super(RailcraftEnderBlocks.getBlockSignal());
        init();
    }
    
    private void init() {
        addCombinedRenderer(EnumEnderSignal.BOX_ENDER_RECEIVER, new RenderSignalBox(EnumEnderSignal.BOX_ENDER_RECEIVER));
        addCombinedRenderer(EnumEnderSignal.BOX_ENDER_CONTROLLER, new RenderSignalBox(EnumEnderSignal.BOX_ENDER_CONTROLLER));
    }

    public void addCombinedRenderer(EnumEnderSignal type, ICombinedRenderer renderer) {
        addCombinedRenderer(type.ordinal(), renderer);
        if(renderer instanceof TileEntitySpecialRenderer){
            ClientRegistry.bindTileEntitySpecialRenderer(type.getTileClass(), (TileEntitySpecialRenderer) renderer);
        }
    }
}
