package mods.railcraft_ender.common.core;

import mods.railcraft_ender.client.render.RenderBlockSignal;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraftforge.client.MinecraftForgeClient;
import mods.railcraft.client.render.BlockRenderer;
import mods.railcraft.client.render.RenderTESRSignals;
import mods.railcraft_ender.common.blocks.signals.TileSignalFoundation;
import net.minecraft.item.Item;

public class ClientProxy extends CommonProxy {

    @Override
    public void initClient() {
        RenderTESRSignals controllerRenderer = new RenderTESRSignals();
        ClientRegistry.bindTileEntitySpecialRenderer(TileSignalFoundation.class, controllerRenderer);

        registerBlockRenderer(new RenderBlockSignal());
    }

    private void registerBlockRenderer(BlockRenderer renderer) {
        if (renderer.getBlock() != null) {
            RenderingRegistry.registerBlockHandler(renderer);
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(renderer.getBlock()), renderer.getItemRenderer());
        }
    }
}
