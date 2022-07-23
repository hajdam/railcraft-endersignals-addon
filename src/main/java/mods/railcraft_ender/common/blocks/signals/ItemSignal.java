package mods.railcraft_ender.common.blocks.signals;

import mods.railcraft.common.blocks.machine.ItemMachine;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSignal extends ItemMachine {
    public ItemSignal(Block block) {
        super(block);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ModelResourceLocation getModelLocation(ItemStack stack, IBlockState state) {
        return new ModelResourceLocation(block.getRegistryName(), "inventory");
    }
}
