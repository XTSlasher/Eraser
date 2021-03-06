package net.slasherxt.eraser.client.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.slasherxt.eraser.inventory.SlotConverter;
import net.slasherxt.eraser.inventory.SlotConverterInput;
import net.slasherxt.eraser.inventory.SlotConverterOutput;
import net.slasherxt.eraser.tileentity.TileEntityConverter;

public class ContainerConverter extends Container {
	private TileEntityConverter converter;
	
	public ContainerConverter(InventoryPlayer invPlayer, TileEntityConverter entity) {
		this.converter = entity;
		
		for(int x=0;x<9;x++) {
			this.addSlotToContainer(new Slot(invPlayer, x, 8 + x * 18, 142));
		}
		
		for(int y=0;y<3;y++) {
			for(int x=0;x<9;x++) {
				this.addSlotToContainer(new Slot(invPlayer, 9 + x + y * 9, 8 + x * 18, 84 + y * 18));
			}
		}
		
		this.addSlotToContainer(new SlotConverterInput(invPlayer.player, entity, 0, 56, 17));
        this.addSlotToContainer(new SlotConverter(invPlayer.player, entity, 1, 56, 53));
        this.addSlotToContainer(new SlotConverterOutput(invPlayer.player, entity, 2, 116, 35));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return converter.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
		Slot slot = getSlot(i);
		
		if(slot != null && slot.getHasStack()) {
		    ItemStack itemstack = slot.getStack();
		    ItemStack result = itemstack.copy();

		    if(i >= 36) {
		      if(!mergeItemStack(itemstack, 0, 36, false)) {
		        return null;
		      }
		    } else if(!mergeItemStack(itemstack, 36, 36 + converter.getSizeInventory(), false)) {
		      return null;
		    }

		    if(itemstack.stackSize == 0) {
		      slot.putStack(null);
		    } else {
		      slot.onSlotChanged();
		    }
		    slot.onPickupFromSlot(player, itemstack); 
		    return result;
		  }
		  return null;
	}
}
