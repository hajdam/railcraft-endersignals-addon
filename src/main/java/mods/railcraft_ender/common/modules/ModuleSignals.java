package mods.railcraft_ender.common.modules;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.util.IIcon;
import mods.railcraft.common.blocks.RailcraftBlocks;
import mods.railcraft.common.blocks.signals.ItemSignal;
import mods.railcraft.common.core.Railcraft;
import mods.railcraft.common.core.RailcraftConfig;
import mods.railcraft.common.items.ItemCircuit;
import mods.railcraft.common.items.RailcraftItem;
import mods.railcraft.common.plugins.forge.CraftingPlugin;
import mods.railcraft.common.plugins.forge.RailcraftRegistry;
import mods.railcraft.common.blocks.signals.BlockSignalBase;
import mods.railcraft.common.blocks.signals.ISignalTileDefinition;
import mods.railcraft_ender.common.blocks.signals.EnumEnderSignal;
import mods.railcraft_ender.common.blocks.signals.TileBoxEnderController;
import mods.railcraft_ender.common.blocks.signals.TileBoxEnderReceiver;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;

public class ModuleSignals {

    private ModuleSignals() {
    }

    public static void preInit() {
        Block blockSignal = RailcraftBlocks.getBlockSignal();
        if (blockSignal != null) {
            int renderId = Railcraft.getProxy().getRenderId();
            BlockSignalEnder enderBlockSignal = new BlockSignalEnder(renderId);
            RailcraftRegistry.register(enderBlockSignal, ItemSignal.class);
        }
    }

    public static void init() {
        Block blockSignal = RailcraftBlocks.getBlockSignal();
        if (blockSignal != null) {

            // Define Ender Receiver Box
            {
                EnumEnderSignal structure = EnumEnderSignal.BOX_ENDER_RECEIVER;
                ItemStack stack = structure.getItem();
                CraftingPlugin.addShapedRecipe(stack,
                        " L ",
                        "ICI",
                        "IRI",
                        'I', "ingotIron",
                        'R', "dustRedstone",
                        'L', Items.ender_pearl,
                        'C', RailcraftItem.circuit.getRecipeObject(ItemCircuit.EnumCircuit.RECEIVER));
            }

            // Define Ender Controller Box
            {
                EnumEnderSignal structure = EnumEnderSignal.BOX_ENDER_CONTROLLER;
                ItemStack stack = structure.getItem();
                CraftingPlugin.addShapedRecipe(stack,
                        " L ",
                        "ICI",
                        "IRI",
                        'I', "ingotIron",
                        'R', "dustRedstone",
                        'L', Items.ender_pearl,
                        'C', RailcraftItem.circuit.getRecipeObject(ItemCircuit.EnumCircuit.CONTROLLER));
            }
        }
    }

    private static class BlockSignalEnder extends BlockSignalBase {

        public BlockSignalEnder(int renderType) {
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
        public void registerBlockIcons(IIconRegister iconRegister) {
            EnumEnderSignal.BOX_ENDER_CONTROLLER.setIcon(iconRegister.registerIcon("railcraft:signal.box.ender-controller"));
            EnumEnderSignal.BOX_ENDER_RECEIVER.setIcon(iconRegister.registerIcon("railcraft:signal.box.ender-receiver"));
        }

        // getIcon
        @Override
        public IIcon func_149691_a(int side, int meta) {
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
    }
}
