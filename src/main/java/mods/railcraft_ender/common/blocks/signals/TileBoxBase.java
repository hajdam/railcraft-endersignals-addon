package mods.railcraft_ender.common.blocks.signals;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import mods.railcraft.api.signals.SignalAspect;
import mods.railcraft.common.util.misc.AABBFactory;
import net.minecraft.tileentity.TileEntity;

public abstract class TileBoxBase extends TileSignalFoundation {

    private static final float PIXEL = 1F / 16F;
    private static final AxisAlignedBB SELECTION_BOX = AABBFactory.start().box().expandHorizontally(-PIXEL * 2).raiseCeiling(-PIXEL).build();
    private static final AxisAlignedBB BOUNDING_BOX = AABBFactory.start().box().raiseCeiling(-PIXEL).build();

    @Override
    public AxisAlignedBB getBoundingBox(IBlockAccess world, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos pos) {
        return SELECTION_BOX.offset(pos);
    }

    public abstract boolean isConnected(ForgeDirection side);

    public abstract SignalAspect getBoxSignalAspect(ForgeDirection side);

    public boolean canTransferAspect() {
        return false;
    }

    public boolean canReceiveAspect() {
        return false;
    }

    public void onNeighborStateChange(mods.railcraft.common.blocks.machine.wayobjects.boxes.TileBoxBase neighbor, EnumFacing side) {
    }

    public final void updateNeighborBoxes() {
        for (int side = 2; side < 6; side++) {
            ForgeDirection forgeSide = ForgeDirection.getOrientation(side);
            TileEntity tile = tileCache.getTileOnSide(forgeSide);
            if (tile instanceof TileBoxBase) {
                TileBoxBase box = (TileBoxBase) tile;
                box.onNeighborStateChange(this, forgeSide);
            }
        }
    }

    @Override
    public boolean canConnectRedstone(int dir) {
        return true;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return TileEntity.INFINITE_EXTENT_AABB;
    }
}
