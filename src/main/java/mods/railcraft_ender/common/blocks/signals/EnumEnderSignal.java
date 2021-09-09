/* 
 * Copyright (c) CovertJaguar, 2014 http://railcraft.info
 * 
 * This code is the property of CovertJaguar
 * and may only be used with explicit written
 * permission unless otherwise specified on the
 * license page at http://railcraft.info/wiki/info:license.
 */
package mods.railcraft_ender.common.blocks.signals;

import java.util.ArrayList;
import java.util.List;
import mods.railcraft.client.render.IIconProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import mods.railcraft.common.blocks.RailcraftBlocks;
import mods.railcraft.common.blocks.signals.ISignalTileDefinition;
import mods.railcraft.common.blocks.signals.TileSignalFoundation;
import mods.railcraft.common.core.RailcraftConfig;
import mods.railcraft.common.modules.ModuleManager;
import mods.railcraft.common.modules.ModuleManager.Module;
import net.minecraft.block.Block;

public enum EnumEnderSignal implements IIconProvider, ISignalTileDefinition {

    // Name (module, hardness, needsSupport, tag, tile)
    BOX_ENDER_RECEIVER(Module.SIGNALS, 3, true, "box.ender.receiver", TileBoxEnderReceiver.class),
    BOX_ENDER_CONTROLLER(Module.SIGNALS, 3, true, "box.ender.controller", TileBoxEnderController.class),;

    private final Module module;
    private final float hardness;
    private final boolean needsSupport;
    private final String tag;
    private final Class<? extends TileSignalFoundation> tile;
    private IIcon icon;
    private static final List<EnumEnderSignal> creativeList = new ArrayList<EnumEnderSignal>();
    public static final EnumEnderSignal[] VALUES = values();

    static {
        creativeList.add(BOX_ENDER_RECEIVER);
        creativeList.add(BOX_ENDER_CONTROLLER);
    }

    private EnumEnderSignal(Module module, float hardness, boolean needsSupport, String tag, Class<? extends TileSignalFoundation> tile) {
        this.module = module;
        this.hardness = hardness;
        this.needsSupport = needsSupport;
        this.tile = tile;
        this.tag = tag;
    }

    public ItemStack getItem() {
        return getItem(1);
    }

    public ItemStack getItem(int qty) {
        return new ItemStack(RailcraftBlocks.getBlockSignal(), qty, ordinal());
    }

    @Override
    public String getTag() {
        return "tile.railcraft.signal." + tag;
    }

    public Module getModule() {
        return module;
    }

    @Override
    public Class<? extends TileSignalFoundation> getTileClass() {
        return tile;
    }

    public TileSignalFoundation getBlockEntity() {
        if (tile == null)
            return null;
        try {
            return tile.newInstance();
        } catch (Exception ex) {
        }
        return null;
    }

    @Override
    public float getHardness() {
        return hardness;
    }

    public void setIcon(IIcon icon) {
        this.icon = icon;
    }

    @Override
    public IIcon getIcon() {
        return icon;
    }

    public static EnumEnderSignal fromId(int id) {
        if (id < 0 || id >= VALUES.length)
            return BOX_ENDER_CONTROLLER;
        return VALUES[id];
    }

    public static List<EnumEnderSignal> getCreativeList() {
        return creativeList;
    }

    @Override
    public boolean needsSupport() {
        return needsSupport;
    }

    @Override
    public boolean isEnabled() {
        if (module == null) return false;
        return ModuleManager.isModuleLoaded(getModule()) && getBlock() != null && RailcraftConfig.isSubBlockEnabled(getTag());
    }

    @Override
    public Block getBlock() {
        return RailcraftBlocks.getBlockSignal();
    }

    @Override
    public int getMeta() {
        return ordinal();
    }
}
