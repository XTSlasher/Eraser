package net.slasherxt.eraser.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.slasherxt.eraser.client.container.ContainerConverter;
import net.slasherxt.eraser.reference.Reference;
import net.slasherxt.eraser.tileentity.TileEntityConverter;

import org.lwjgl.opengl.GL11;

public class GuiConverter extends GuiContainer {
	public static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/gui/container/converter.png");
	
	private TileEntityConverter converter;
	
	public GuiConverter(InventoryPlayer invPlayer, TileEntityConverter entity) {
		super(new ContainerConverter(invPlayer, entity));
		
		converter = entity;
		
		xSize = 176;
		ySize = 165;
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float f, int j, int i) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		this.fontRendererObj.drawString(I18n.format("Stored Shavings: " + converter.getShavingCount(), new Object[0]), 8, this.ySize - 50 + 2, 4210752);
		//drawString(null, "Shaving Count: " + converter.blockTagCompound.getInteger("shavingCount"), 50, 50, 1);
	}
}
