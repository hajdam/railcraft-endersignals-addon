package mods.railcraft_ender.common.modules;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import mods.railcraft.common.items.ItemCircuit;
import mods.railcraft.common.items.RailcraftItem;
import mods.railcraft.common.plugins.forge.CraftingPlugin;
import mods.railcraft_ender.common.blocks.signals.EnumEnderSignal;
import mods.railcraft_ender.common.blocks.RailcraftEnderBlocks;
import mods.railcraft.common.plugins.forge.RailcraftRegistry;
import mods.railcraft_ender.common.core.Railcraft_Ender;
import mods.railcraft_ender.common.gui.GuiHandler;
import net.minecraft.init.Items;
import cpw.mods.fml.common.network.NetworkRegistry;

public class ModuleSignals {
    
    private ModuleSignals() {
    }

    public static void preInit() {
        RailcraftEnderBlocks.registerBlockSignal();
    }

    public static void initFirst() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Railcraft_Ender.getMod(), new GuiHandler());
    }

    public static void init() {
        initFirst();
        initSecond();

        Block blockSignal = RailcraftEnderBlocks.getBlockSignal();
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
    
    private static void initSecond() {
        // Finish initializing ItemRegistry
        for (EnumEnderSignal type : EnumEnderSignal.values()) {
            if (type.isEnabled())
                RailcraftRegistry.register(type.getItem());
        }
    }
}
