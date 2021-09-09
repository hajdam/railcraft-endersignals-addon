package mods.railcraft_ender.common.gui;

public enum EnumEnderGui {

    BOX_ENDER_CONTROLLER(false),
    BOX_ENDER_RECEIVER(true),;
    private static final EnumEnderGui[] VALUES = values();
    private final boolean hasContainer;

    EnumEnderGui(boolean hasContainer) {
        this.hasContainer = hasContainer;
    }

    public boolean hasContainer() {
        return hasContainer;
    }

    public static EnumEnderGui fromOrdinal(int i) {
        if (i < 0 || i >= VALUES.length)
            return null;
        return VALUES[i];
    }

}
