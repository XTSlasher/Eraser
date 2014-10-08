package net.slasherxt.eraser.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.slasherxt.eraser.item.ItemEraser;

public class SlotConverter extends Slot {
	
	private EntityPlayer player;

	public SlotConverter(EntityPlayer player, IInventory inv, int slotIndex, int xDis, int yDis) {
		super(inv, slotIndex, xDis, yDis);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		if(stack.getItem() instanceof ItemEraser) {			
			return true;
		}
		
		return false;
	}
}
