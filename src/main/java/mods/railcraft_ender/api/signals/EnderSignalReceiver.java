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

public class EnderSignalReceiver extends SignalReceiver {

    private boolean needsInit = true;

    @Nonnull
    private SignalAspect aspect = SignalAspect.BLINK_RED;

    public EnderSignalReceiver(String locTag, TileEntity tile) {
        super(locTag, tile, 1);
    }

    @Nonnull
    public SignalAspect getAspect() {
        return aspect;
    }

    public void setAspect(@Nonnull SignalAspect aspect) {
        this.aspect = aspect;
    }

    @Override
    public void onControllerAspectChange(SignalController con, @Nonnull SignalAspect aspect) {
        if (this.aspect != aspect) {
            this.aspect = aspect;
            super.onControllerAspectChange(con, aspect);
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
        aspect = SignalAspect.VALUES[data.getByte("aspect")];
    }

    @Override
    public void writePacketData(DataOutputStream data) throws IOException {
        super.writePacketData(data);
        data.writeByte(aspect.ordinal());
    }

    @Override
    public void readPacketData(DataInputStream data) throws IOException {
        super.readPacketData(data);
        aspect = SignalAspect.VALUES[data.readByte()];
    }

    @Override
    public String toString() {
        return String.format("Receiver:%s (%s)", aspect, super.toString());
    }

    public void registerController(SignalController controller) {
        if (controller instanceof EnderSignalController) {
            super.registerController(controller);
        } else {
            // TODO
        }
    }

    @Override
    public void tickServer() {
        if (needsInit) {
            needsInit = false;
            for (BlockPos pair : getPairs()) {
                SignalController controller = getControllerAt(pair);
                if (controller != null) {
                    onControllerAspectChange(controller, controller.getAspectFor(getCoords()));
                } else {
                    // Read aspect from world storage
                    EnderSignalsCollection enderSignals = EnderSignalsCollection.forDimension(pair.dimension);
                    SignalAspect enderAspect = enderSignals.getAspect(pair);
                    if (enderAspect != null) {
                        onControllerAspectChange(null, enderAspect);
                    }
                }
            }
        }
    }
}
