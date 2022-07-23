package mods.railcraft_ender.common.blocks.signals;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.annotation.Nonnull;
import mods.railcraft.api.signals.SignalAspect;
import mods.railcraft.common.blocks.signals.IAspectActionManager;
import mods.railcraft.common.util.network.IGuiReturnHandler;
import mods.railcraft.common.util.network.RailcraftInputStream;
import mods.railcraft.common.util.network.RailcraftOutputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileBoxActionManager extends TileBoxSecured {

    private final boolean[] powerOnAspects = new boolean[SignalAspect.values().length];

    public TileBoxActionManager() {
        powerOnAspects[SignalAspect.GREEN.ordinal()] = true;
    }

    @Override
    public boolean doesActionOnAspect(SignalAspect aspect) {
        return powerOnAspects[aspect.ordinal()];
    }

    @Override
    public void doActionOnAspect(SignalAspect aspect, boolean trigger) {
        powerOnAspects[aspect.ordinal()] = trigger;
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound data) {
        super.writeToNBT(data);
        byte[] array = new byte[powerOnAspects.length];
        for (int i = 0; i < powerOnAspects.length; i++) {
            array[i] = (byte) (powerOnAspects[i] ? 1 : 0);
        }
        data.setByteArray("powerOnAspects", array);
        return data;
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound data) {
        super.readFromNBT(data);
        if (data.hasKey("PowerOnAspect")) {
            byte[] array = data.getByteArray("PowerOnAspect");
            for (int i = 0; i < powerOnAspects.length; i++) {
                powerOnAspects[i] = array[i] == 1;
            }
        } else if (data.hasKey("powerOnAspects")) {
            byte[] array = data.getByteArray("powerOnAspects");
            for (int i = 0; i < powerOnAspects.length; i++) {
                powerOnAspects[i] = array[i] == 1;
            }
        }
    }

    @Override
    public void writePacketData(@Nonnull RailcraftOutputStream data) throws IOException {
        super.writePacketData(data);
        writeActionInfo(data);
    }

    @Override
    public void readPacketData(@Nonnull RailcraftInputStream data) throws IOException {
        super.readPacketData(data);
        readActionInfo(data.readByte());
    }

    @Override
    public void writeGuiData(@Nonnull RailcraftOutputStream data) throws IOException {
        super.writeGuiData(data);
        writeActionInfo(data);
    }

    @Override
    public void readGuiData(@Nonnull RailcraftInputStream data, EntityPlayer sender) throws IOException {
        super.readGuiData(data, sender);
        byte bits = data.readByte();
        if (sender == null || canAccess(sender.getGameProfile())) {
            readActionInfo(bits);
        }
    }

    private void writeActionInfo(DataOutputStream data) throws IOException {
        byte bits = 0;
        for (int i = 0; i < powerOnAspects.length; i++) {
            bits |= (powerOnAspects[i] ? 1 : 0) << i;
        }
        data.writeByte(bits);
    }

    private void readActionInfo(byte bits) {
        for (int bit = 0; bit < powerOnAspects.length; bit++) {
            powerOnAspects[bit] = ((bits >> bit) & 1) == 1;
        }
    }
}
