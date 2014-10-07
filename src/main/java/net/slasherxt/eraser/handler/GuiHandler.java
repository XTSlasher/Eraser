package net.slasherxt.eraser.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.slasherxt.eraser.client.container.ContainerConverter;
import net.slasherxt.eraser.client.gui.GuiConverter;
import net.slasherxt.eraser.tileentity.TileEntityConverter;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		
		switch(id) {
			case 0: 
				if(entity != null && entity instanceof TileEntityConverter) {
					return new ContainerConverter(player.inventory, (TileEntityConverter) entity);
				} else {
					return null;
				}
				
				
			default: return null;
		}
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		
		switch(id) {
			case 0: 
				if(entity != null && entity instanceof TileEntityConverter) {
					return new GuiConverter(player.inventory, (TileEntityConverter) entity);
				} else {
					return null;
				}
				
				
			default: return null;
		}
	}
	
}
