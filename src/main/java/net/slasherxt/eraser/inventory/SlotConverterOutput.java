package net.slasherxt.eraser.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.slasherxt.eraser.item.ItemEraser;

public class SlotConverterOutput extends Slot {
	
	private EntityPlayer player;

	public SlotConverterOutput(EntityPlayer player, IInventory inv, int slotIndex, int xDis, int yDis) {
		super(inv, slotIndex, xDis, yDis);
		this.player = player;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {		
		return false;
	}
}
