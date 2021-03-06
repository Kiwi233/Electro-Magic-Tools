package emt.tile;

import emt.init.EMTBlocks;
import emt.util.EMTConfigHandler;
import ic2.api.energy.prefab.BasicSink;
import ic2.api.tile.IWrenchable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.items.wands.ItemWandCasting;

public class TileEntityIndustrialWandRecharge extends TileEntityEMT implements IInventory, IWrenchable {

    ItemStack ItemStacks[];
    private BasicSink ic2EnergySink = new BasicSink(this, 100000, 4);

    public TileEntityIndustrialWandRecharge() {
        ItemStacks = new ItemStack[1];
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return ItemStacks[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (ItemStacks[i] != null) {

            if (ItemStacks[i].stackSize <= j) {
                ItemStack itemstack = ItemStacks[i];
                ItemStacks[i] = null;
                return itemstack;
            } else {
                ItemStack itemstack1 = ItemStacks[i].splitStack(j);

                if (ItemStacks[i].stackSize == 0) {
                    ItemStacks[i] = null;
                }

                return itemstack1;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        if (ItemStacks[i] != null) {
            ItemStack itemstack = ItemStacks[i];
            ItemStacks[i] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public void updateEntity() {
        ic2EnergySink.updateEntity();
        if (!this.worldObj.isRemote) {
            if (getStackInSlot(0) != null) {

                ItemStack wand = getStackInSlot(0);
                if (wand != null && wand.getItem() instanceof ItemWandCasting) {
                    ItemWandCasting wandItem = (ItemWandCasting) wand.getItem();
                    if (ic2EnergySink.useEnergy(EMTConfigHandler.wandChargerConsumption) && wandItem.getAspectsWithRoom(wand) != null) {
                        wandItem.addVis(wand, Aspect.ORDER, 1, true);
                        wandItem.addVis(wand, Aspect.FIRE, 1, true);
                        wandItem.addVis(wand, Aspect.ENTROPY, 1, true);
                        wandItem.addVis(wand, Aspect.WATER, 1, true);
                        wandItem.addVis(wand, Aspect.EARTH, 1, true);
                        wandItem.addVis(wand, Aspect.AIR, 1, true);
                    }
                }
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        ItemStacks[i] = itemstack;

        if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
            itemstack.stackSize = getInventoryStackLimit();
        }
    }

    public void readFromNBT(NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        NBTTagList nbttaglist = p_145839_1_.getTagList("Items", 10);
        this.ItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.ItemStacks.length) {
                this.ItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }

    public void writeToNBT(NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.ItemStacks.length; ++i) {
            if (this.ItemStacks[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.ItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        p_145841_1_.setTag("Items", nbttaglist);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
            return false;
        }

        return player.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return true;
    }

    @Override
    public void invalidate() {
        ic2EnergySink.invalidate();
        super.invalidate();
    }

    @Override
    public void onChunkUnload() {
        ic2EnergySink.onChunkUnload();
    }

    @Override
    public String getInventoryName() {
        return "Industrial Wand Charging Station";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
        return false;
    }

    @Override
    public short getFacing() {
        return 0;
    }

    @Override
    public void setFacing(short facing) {

    }

    @Override
    public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    public float getWrenchDropRate() {
        return 0.8f;
    }

    @Override
    public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
        return new ItemStack(EMTBlocks.emtMachines, 1, 0);
    }
}
