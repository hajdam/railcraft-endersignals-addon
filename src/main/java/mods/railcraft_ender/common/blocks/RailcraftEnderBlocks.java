package mods.railcraft_ender.common.blocks;

import java.util.List;
import mods.railcraft_ender.common.blocks.signals.ItemSignal;
import mods.railcraft.common.core.Railcraft;
import mods.railcraft.common.core.RailcraftConfig;
import mods.railcraft.common.plugins.forge.RailcraftRegistry;
import mods.railcraft_ender.common.blocks.signals.BlockSignalBase;
import mods.railcraft_ender.common.blocks.signals.EnumEnderSignal;
import mods.railcraft_ender.common.blocks.signals.ISignalTileDefinition;
import mods.railcraft_ender.common.blocks.signals.TileBoxEnderController;
import mods.railcraft_ender.common.blocks.signals.TileBoxEnderReceiver;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class RailcraftEnderBlocks extends BlockSignalBase {

    private static Block blockSignal = null;

    public RailcraftEnderBlocks(int renderType) {
        super(renderType);
        setBlockName("railcraft_ender.signal");

        GameRegistry.registerTileEntity(TileBoxEnderController.class, "RCTileStructureEnderControllerBox");
        GameRegistry.registerTileEntity(TileBoxEnderReceiver.class, "RCTileStructureEnderReceiverBox");
    }

    @Override
    public ISignalTileDefinition getSignalType(int meta) {
        return EnumEnderSignal.fromId(meta);
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (EnumEnderSignal type : EnumEnderSignal.getCreativeList()) {
            if (type.isEnabled()) {
                list.add(type.getItem());
            }
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        EnumEnderSignal.BOX_ENDER_CONTROLLER.setIcon(iconRegister.registerIcon("railcraft_ender:signal.box.ender-controller"));
        EnumEnderSignal.BOX_ENDER_RECEIVER.setIcon(iconRegister.registerIcon("railcraft_ender:signal.box.ender-receiver"));
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        EnumEnderSignal type = EnumEnderSignal.fromId(meta);
        return type.getIcon();
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return EnumEnderSignal.fromId(metadata).getBlockEntity();
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return EnumEnderSignal.fromId(metadata).getBlockEntity();
    }

    public static void registerBlockSignal() {
        if (blockSignal == null && RailcraftConfig.isBlockEnabled("signal")) {
            int renderId = Railcraft.getProxy().getRenderId();
            blockSignal = new RailcraftEnderBlocks(renderId);
            RailcraftRegistry.register(blockSignal, ItemSignal.class);
        }
    }

    public static Block getBlockSignal() {
        return blockSignal;
    }
}
