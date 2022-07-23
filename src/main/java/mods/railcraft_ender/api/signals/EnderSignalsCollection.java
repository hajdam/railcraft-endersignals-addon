package mods.railcraft_ender.api.signals;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.math.BlockPos;
import mods.railcraft.api.signals.SignalAspect;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.common.DimensionManager;
import javax.annotation.Nullable;

public class EnderSignalsCollection extends WorldSavedData {

    private static int MAX_CACHE_SIZE = 200;

    private World worldObj;
    private int tickCounter;
    private final Map<BlockPos, SignalAspect> aspectsCache = new HashMap<BlockPos, SignalAspect>();
    private NBTTagCompound aspectSignals;

    public EnderSignalsCollection(String p_i1677_1_) {
        super(p_i1677_1_);
    }

    public EnderSignalsCollection(World worldObj) {
        super("railcraft.endersignals");
        this.worldObj = worldObj;
        this.markDirty();
    }

    public void tick() {
        ++this.tickCounter;
    }

    @Nullable
    public SignalAspect getAspect(BlockPos coord) {
        SignalAspect aspect = aspectsCache.get(coord);
        if (aspect == null) {
            String coordCode = coord.x + "," + coord.y + "," + coord.z;
            if (aspectSignals.hasKey("coordCode")) {
                aspect = SignalAspect.fromOrdinal(aspectSignals.getInteger(coordCode));
                if (aspectsCache.size() >= MAX_CACHE_SIZE) {
                    releaseSomeFromCache();
                }
                aspectsCache.put(coord, aspect);
            }
        }

        return aspect;
    }

    public void setAspect(BlockPos coord, SignalAspect aspect) {
        if (!aspectsCache.containsKey(coord) && aspectsCache.size() >= MAX_CACHE_SIZE) {
            releaseSomeFromCache();
        }
        aspectsCache.put(coord, aspect);
    }

    private void releaseSomeFromCache() {
        // remove some item (eldest would be preferred)
        for (BlockPos someCoord : aspectsCache.keySet()) {
            SignalAspect someAspect = aspectsCache.get(someCoord);
            String coordCode = someCoord.x + "," + someCoord.y + "," + someCoord.z;
            aspectSignals.setInteger(coordCode, someAspect.ordinal());
            aspectsCache.remove(someCoord);
            break;
        }
    }

    public void removeAspect(BlockPos coord) {
        aspectsCache.remove(coord);
        String coordCode = coord.x + "," + coord.y + "," + coord.z;
        if (aspectSignals != null) {
            aspectSignals.removeTag(coordCode);
        }
    }

    /**
     * reads in data from the NBTTagCompound into this MapDataBase
     */
    public void readFromNBT(NBTTagCompound tagCompound) {
        this.tickCounter = tagCompound.getInteger("Tick");
        this.aspectSignals = tagCompound.getCompoundTag("Aspects");
        if (aspectSignals == null) {
            aspectSignals = new NBTTagCompound();
            tagCompound.setTag("Aspects", aspectSignals);
        }
    }

    /**
     * write data to NBTTagCompound from this MapDataBase, similar to Entities
     * and TileEntities
     */
    public void writeToNBT(NBTTagCompound tagCompound) {
        tagCompound.setInteger("Tick", this.tickCounter);
        NBTTagCompound writeAspects;
        if (tagCompound.hasKey("Aspects")) {
            writeAspects = tagCompound.getCompoundTag("Aspects");
        } else {
            writeAspects = new NBTTagCompound();
            tagCompound.setTag("Aspects", writeAspects);
        }
        if (aspectSignals == null) {
            aspectSignals = tagCompound;
        }

        for (BlockPos coord : this.aspectsCache.keySet()) {
            SignalAspect aspect = this.aspectsCache.get(coord);
            String coordCode = coord.x + "," + coord.y + "," + coord.z;
            writeAspects.setInteger(coordCode, aspect.ordinal());
        }
    }

    public static EnderSignalsCollection forDimension(int dimension) {
        World world = DimensionManager.getWorld(dimension);
        EnderSignalsCollection instance = (EnderSignalsCollection) world.perWorldStorage.loadData(EnderSignalsCollection.class, "railcraft.endersignals");
        if (instance == null) {
            instance = new EnderSignalsCollection(world);
            world.perWorldStorage.setData("railcraft.endersignals", instance);
        }

        return instance;
    }
}
