package mods.railcraft_ender.api.signals;

import mods.railcraft.api.signals.SignalAspect;
import mods.railcraft.api.signals.SignalReceiver;
import mods.railcraft.api.signals.SignalController;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class EnderSignalController extends SignalController {

    @Nonnull
    private SignalAspect aspect = SignalAspect.BLINK_RED;
    private boolean needsInit = true;

    public EnderSignalController(String locTag, TileEntity tile) {
        super(locTag, tile, 1);
    }

    @Nonnull
    public SignalAspect getAspect() {
        return aspect;
    }

    public void setAspect(@Nonnull SignalAspect aspect) {
        if (this.aspect != aspect) {
            this.aspect = aspect;

            BlockPos coords = getCoords();
            EnderSignalsCollection enderSignals = EnderSignalsCollection.forDimension(coords.dimension);
            enderSignals.setAspect(coords, aspect);

            updateReceiver();
        }
    }

    @Nonnull
    @Override
    public SignalAspect getAspectFor(BlockPos receiver) {
        return aspect;
    }

    @Override
    public void tickServer() {
        super.tickServer();
        if (needsInit) {
            needsInit = false;
            updateReceiver();
        }
    }

    private void updateReceiver() {
        for (BlockPos recv : getPairs()) {
            SignalReceiver receiver = getReceiverAt(recv);
            if (receiver != null) {
                receiver.onControllerAspectChange(this, aspect);
            }
        }
    }

    @Override
    public void registerReceiver(SignalReceiver receiver) {
        if (receiver instanceof EnderSignalReceiver) {
            BlockPos coords = receiver.getCoords();
            addPairing(coords);

            ((EnderSignalReceiver) receiver).registerController(this);
            receiver.onControllerAspectChange(this, getAspectFor(coords));
        } else {
            // ChatPlugin.sendLocalizedChatFromServer(player, "railcraft.gui.tuner.incompatible");
        }
    }

    @Override
    protected void saveNBT(NBTTagCompound data) {
        super.saveNBT(data);
        data.setByte("aspect", (byte) aspect.ordinal());
    }

    @Override
    protected void loadNBT(NBTTagCompound data) {
        super.loadNBT(data);
        aspect = SignalAspect.fromOrdinal(data.getByte("aspect"));
    }

    @Override
    public void writePacketData(DataOutputStream data) throws IOException {
        super.writePacketData(data);
        data.writeByte(aspect.ordinal());
    }

    @Override
    public void readPacketData(DataInputStream data) throws IOException {
        super.readPacketData(data);
        aspect = SignalAspect.fromOrdinal(data.readByte());
    }

    @Override
    public String toString() {
        return String.format("Controller:%s (%s)", aspect, super.toString());
    }

    public void onBlockRemoval() {
        BlockPos coords = getCoords();
        EnderSignalsCollection enderSignals = EnderSignalsCollection.forDimension(coords.dimension);
        enderSignals.removeAspect(coords);
    }
}
