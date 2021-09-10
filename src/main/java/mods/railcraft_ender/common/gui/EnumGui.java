package mods.railcraft_ender.common.gui;

public enum EnumGui {

    BOX_ENDER_CONTROLLER(false),
    BOX_ENDER_RECEIVER(true);

    private final boolean hasContainer;

    EnumGui(boolean hasContainer) {
        this.hasContainer = hasContainer;
    }

    public boolean hasContainer() {
        return hasContainer;
    }

    public static EnumGui fromOrdinal(int i) {
        return values()[i];
    }
}
