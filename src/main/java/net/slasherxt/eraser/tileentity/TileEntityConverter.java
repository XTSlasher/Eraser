package net.slasherxt.eraser.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import net.slasherxt.eraser.item.ItemEraser;
import net.slasherxt.eraser.item.crafting.ConverterRecipes;
import net.slasherxt.eraser.utility.LogHelper;

public class TileEntityConverter extends TileEntity implements IInventory {
	private ItemStack[] inventory;
	protected int shavingCount;
	private int maxStoredShavings = 150000;
	
	public TileEntityConverter() {
		inventory = new ItemStack[3];
	}
	
	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {		
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
		
		if(stack != null && stack.getItem() instanceof ItemEraser && slot == 1) {
			int count1;
			
			if(stack.stackTagCompound.getTag("shavingCount") != null) {
				count1 = stack.stackTagCompound.getInteger("shavingCount");
			} else {
				count1 = 0;
			}
			int dif = maxStoredShavings - shavingCount;
			
			if(shavingCount < maxStoredShavings && count1 > dif) {
				shavingCount += dif;
				stack.stackTagCompound.setInteger("shavingCount", count1-dif);
			}
			if(shavingCount < maxStoredShavings && count1 < dif) {
				shavingCount += count1;
				stack.stackTagCompound.setInteger("shavingCount", 0);
			}
			
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
				
				item.setByte("SlotConverter", (byte) i);
				itemstack.writeToNBT(item);
				list.appendTag(item);
			}
		}
		
		compound.setTag("converterInv", list);
		
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("shavingCount", shavingCount);
		
		compound.setTag("shavingCounter", nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		NBTTagList list = compound.getTagList("converterInv", 10);
		
		for(int i=0;i<list.tagCount();i++) {
			NBTTagCompound tag = list.getCompoundTagAt(i);
			byte b0 = tag.getByte("SlotConverter");
			
			if(b0 >= 0 && b0 < this.getSizeInventory()) {
				this.setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(tag));
			}
		}
		
		
		NBTTagCompound nbt;
		
		if(compound.getTag("shavingCounter") == null) {
			shavingCount = 0;
		} else {
			nbt = (NBTTagCompound) compound.getTag("shavingCounter");
			shavingCount = nbt.getInteger("shavingCount");
		}
	}
 	
	@Override
	public Packet getDescriptionPacket()
    {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("shavingCount", shavingCount);
		
		S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
		
        return packet;
    }
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		shavingCount = pkt.func_148857_g().getInteger("shavingCount");
    }
	
	@Override
	public void updateEntity() {
		boolean hasShavings = this.shavingCount > 0;
		boolean flag1 = false;
		
		if(!this.worldObj.isRemote) {
			if(hasShavings && this.inventory[0] != null) {
				if(this.canShape()) {
					if(hasShavings) {
						this.shapeItem();
						flag1 = true;
					}
				}
			}
		}
		
		if(flag1) {
			markDirty();
		}
	}
	
	private boolean canShape() {
		if(this.inventory[0] == null) {
			return false;
		} else {
			ItemStack stack = ConverterRecipes.shaping().getShapingResult(this.inventory[0]);
			
			if(shavingCount >= ConverterRecipes.shaping().getRequiredShaving(this.inventory[0])) {
				if(stack == null) return false;
				if(this.inventory[2] == null) return true;
				if(!this.inventory[2].isItemEqual(stack)) return false;
			} else {
				return false;
			}
			
			int result = inventory[2].stackSize + stack.stackSize;
			return result <= getInventoryStackLimit() && result <= this.inventory[2].getMaxStackSize();
		}
	}
	
	public void shapeItem() {
		if(this.canShape()) {
			ItemStack stack = ConverterRecipes.shaping().getShapingResult(this.inventory[0]);
			
			if(this.inventory[2] == null) {
				this.inventory[2] = stack.copy();
			} else if(this.inventory[2].getItem() == stack.getItem()) {
				this.inventory[2].stackSize += stack.stackSize;
			}
			
			ItemStack checkStack = new ItemStack(inventory[0].getItem(), 1);
			
			shavingCount -= ConverterRecipes.shaping().getRequiredShaving(checkStack);
			--this.inventory[0].stackSize;
			
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			
			if(this.inventory[0].stackSize <= 0) {
				this.inventory[0] = null;
			}
			
			
		}
	}
	
	public Integer getShavingCount() {		
		return shavingCount;
	}
}
