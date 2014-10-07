package net.slasherxt.eraser.client.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.slasherxt.eraser.tileentity.TileEntityConverter;

public class ContainerConverter extends Container {
	private TileEntityConverter converter;
	
	public ContainerConverter(InventoryPlayer invPlayer, TileEntityConverter entity) {
		this.converter = entity;
		
		for(int x=0;x<9;x++) {
			this.addSlotToContainer(new Slot(invPlayer, x, 8, 142));
		}
		
		for(int y=0;y<3;y++) {
			for(int x=0;x<9;x++) {
				this.addSlotToContainer(new Slot(invPlayer, 9 + x + y * 9, 8 + x * 18, 84 + y * 18));
			}
		}
		
		this.addSlotToContainer(new Slot(invPlayer, 0, 56, 17));
        this.addSlotToContainer(new Slot(invPlayer, 1, 56, 53));
        this.addSlotToContainer(new Slot(invPlayer, 2, 116, 35));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return converter.isUseableByPlayer(player);
	}
}
