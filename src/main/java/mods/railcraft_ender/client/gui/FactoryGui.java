package mods.railcraft_ender.client.gui;

import org.apache.logging.log4j.Level;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.InventoryPlayer;
import mods.railcraft.client.gui.GuiAspectAction;
import mods.railcraft.common.blocks.machine.TileMultiBlock;
import mods.railcraft.common.blocks.signals.IAspectActionManager;
import mods.railcraft_ender.common.blocks.signals.TileBoxEnderController;
import mods.railcraft_ender.common.gui.EnumGui;
import mods.railcraft.common.plugins.forge.LocalizationPlugin;
import mods.railcraft.common.util.misc.Game;
import net.minecraft.world.World;

public class FactoryGui {

    private FactoryGui() {
    }

    public static GuiScreen build(EnumGui gui, InventoryPlayer inv, Object obj, World world, int x, int y, int z) {
        if (obj instanceof TileMultiBlock && !((TileMultiBlock) obj).isStructureValid()) {
            return null;
        }

        try {
            switch (gui) {
                case BOX_ENDER_RECEIVER:
                    return new GuiAspectAction(inv.player, (IAspectActionManager) obj, LocalizationPlugin.translate("railcraft_ender.gui.box.aspect.action"));
                case BOX_ENDER_CONTROLLER:
                    return new GuiBoxEnderController((TileBoxEnderController) obj);
            }
        } catch (ClassCastException ex) {
            Game.log(Level.WARN, "Error when attempting to build gui {0}: {1}", gui, ex);
        }
        return null;
    }
}
