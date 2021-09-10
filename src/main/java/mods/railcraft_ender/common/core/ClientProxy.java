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
	
//	public static int railcraftPlatformRenderer;
//	public static int railcraftCosTrackRenderer;
//	private static final ModelTrainOperatorCap cap = new ModelTrainOperatorCap(1.0f);
	
	@Override
	public void initClient() 
	{
        RenderTESRSignals controllerRenderer = new RenderTESRSignals();
        ClientRegistry.bindTileEntitySpecialRenderer(TileSignalFoundation.class, controllerRenderer);

        registerBlockRenderer(new RenderBlockSignal());

//		railcraftPlatformRenderer = RenderingRegistry.getNextAvailableRenderId();
//		railcraftCosTrackRenderer = RenderingRegistry.getNextAvailableRenderId();
//		
//		RenderingRegistry.registerBlockHandler(railcraftPlatformRenderer, new RailcraftPlatformRenderer());
//		RenderingRegistry.registerBlockHandler(railcraftCosTrackRenderer, new RailcraftCosTrackRenderer());
//		RenderingRegistry.registerEntityRenderingHandler(EntityModelledChestCart.class, new RenderModelledCartCustom());
//		RenderingRegistry.registerEntityRenderingHandler(EntityModelledTankCart.class, new RenderModelledCartCustom());
//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRailcraftCosSignalBlock.class, new TESRCosSignalBase());
//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRailcraftCosSignalDistant.class, new TESRCosSignalBase());
//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRailcraftSignBasic.class, new TESRSignBasic());
//		//ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlatform.class, new RenderPlatform());
//		
//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRailcraftCos.SignalBanner),
//				(IItemRenderer) new ItemRendererRailcraftCosSignalBase(EnumCosSignalType.BANNER_REPEATER));
//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRailcraftCos.SignalSemaphore),
//				(IItemRenderer) new ItemRendererRailcraftCosSignalBase(EnumCosSignalType.SEMAPHORE_STOP));
//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRailcraftCos.SignalSemaphoreRepeater),
//				(IItemRenderer) new ItemRendererRailcraftCosSignalBase(EnumCosSignalType.SEMAPHORE_REPEATER));
//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRailcraftCos.SignalSemaphoreRepeaterAlt),
//				(IItemRenderer) new ItemRendererRailcraftCosSignalBase(EnumCosSignalType.SEMAPHORE_REPEATER_ALT));
//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRailcraftCos.SignWhistle),
//				(IItemRenderer) new ItemRendererRailcraftSignBasic(EnumSignBasicType.WHISTLE));
//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRailcraftCos.SignMPH5),
//				(IItemRenderer) new ItemRendererRailcraftSignBasic(EnumSignBasicType.MPH5));
//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRailcraftCos.SignClearance),
//				(IItemRenderer) new ItemRendererRailcraftSignBasic(EnumSignBasicType.CLEARANCE));
//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRailcraftCos.SignRefuge),
//				(IItemRenderer) new ItemRendererRailcraftSignBasic(EnumSignBasicType.REFUGE));
//		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockRailcraftCos.SignShunt),
//				(IItemRenderer) new ItemRendererRailcraftSignBasic(EnumSignBasicType.SHUNT));		
	}
	
    private void registerBlockRenderer(BlockRenderer renderer) {
        if (renderer.getBlock() != null) {
            RenderingRegistry.registerBlockHandler(renderer);
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(renderer.getBlock()), renderer.getItemRenderer());
        }
    }
}