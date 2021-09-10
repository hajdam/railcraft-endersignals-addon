package mods.railcraft_ender.common.blocks.signals;

import net.minecraft.block.Block;

public interface ISignalTileDefinition {

    String getTag();

    float getHardness();

    boolean needsSupport();

    boolean isEnabled();

    Block getBlock();

    int getMeta();

    Class<? extends TileSignalFoundation> getTileClass();

}
