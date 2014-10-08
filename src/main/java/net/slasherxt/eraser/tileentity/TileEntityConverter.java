package net.slasherxt.eraser.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import net.slasherxt.eraser.item.ItemEraser;

public class TileEntityConverter extends TileEntity implements IInventory {
	private ItemStack[] inventory;
	public NBTTagCompound blockTagCompound;
	
	public TileEntityConverter() {
		inventory = new ItemStack[3];
	}
	
	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		
		if(inventory[i] != null && i == 1) {
			ItemStack item = inventory[i];
			
			if(item.getItem() instanceof ItemEraser) {
				if(blockTagCompound == null) {
					blockTagCompound = new NBTTagCompound();
					blockTagCompound.setInteger("shavingCount", 0);
				}
				
				int count1 = item.stackTagCompound.getInteger("shavingCount");
				int count2 = this.blockTagCompound.getInteger("shavingCount");
				
				blockTagCompound.setInteger("shavingCount", count1 + count2);
				item.stackTagCompound.setInteger("shavingCount", 0);
			}
		}
		
		return inventory[i];
	}

	@Override
	public ItemStack decrStackSize(int slot, int count) {
		ItemStack stack = getStackInSlot(slot);
		
		if(stack != null) {
			if(stack.stackSize <= count) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(count);
				markDirty();
			}
		}
		
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		setInventorySlotContents(slot, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;
		
		if(stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
		
		markDirty();
	}

	@Override
	public String getInventoryName() {
		return "Eraser Converter";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < getSizeInventory(); i++) {
			ItemStack itemstack = getStackInSlot(i);
			
			if(itemstack != null) {
				NBTTagCompound item = new NBTTagCompound();
				
				item.setByte("SlotTutDeployer", (byte) i);
				itemstack.writeToNBT(item);
				list.appendTag(item);
			}
		}
		
		if(compound.getTag("shavingCount") == null) {
			compound.setInteger("shavingCount", 0);
		}
		
		compound.setTag("converterInv", list);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		NBTTagList list = compound.getTagList("converterInv", Constants.NBT.TAG_LIST);
		
		for(int i=0;i<list.tagCount();i++) {
			NBTTagCompound item = (NBTTagCompound) list.getCompoundTagAt(i);
			int slot = item.getByte("SlotConverter");
			
			if(slot >= 0 && slot < getSizeInventory()) {
				this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
			}
		}
		
		if(compound.getTag("shavingCount") != null) {
			compound.setInteger("shavingCount", compound.getInteger("shavingTag"));
		}
	}
	
	public Integer getShavingCount() {		
		if(blockTagCompound == null) {
			blockTagCompound = new NBTTagCompound();
			blockTagCompound.setInteger("shavingCount", 0);
		}
		
		return blockTagCompound.getInteger("shavingCount");
	}
}
