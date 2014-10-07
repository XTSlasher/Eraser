package net.slasherxt.eraser.init;

import net.minecraft.block.Block;
import net.slasherxt.eraser.block.BlockConverter;
import net.slasherxt.eraser.tileentity.TileEntityConverter;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
	public static Block converter;
	
	public static void preInit() {
		converter = new BlockConverter();
	}
	
	public static void init() {
		registerBlocks(converter, "eraserConverter");
	}
	
	public static void registerBlocks(Block block, String name) {
		GameRegistry.registerBlock(block, name);
	}
	
	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityConverter.class, "EraserConverter");
	}
}
