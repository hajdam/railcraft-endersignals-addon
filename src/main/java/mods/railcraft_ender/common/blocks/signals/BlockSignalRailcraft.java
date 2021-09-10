package mods.railcraft_ender.common.blocks.signals;

import cpw.mods.fml.common.registry.GameRegistry;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import mods.railcraft.common.blocks.signals.ISignalTileDefinition;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class BlockSignalRailcraft extends BlockSignalBase {

    public BlockSignalRailcraft(int renderType) {
        super(renderType);
        setBlockName("railcraft.signal");

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
            if (type.isEnabled())
                list.add(type.getItem());
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

}
