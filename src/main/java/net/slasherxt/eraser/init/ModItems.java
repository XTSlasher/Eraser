package net.slasherxt.eraser.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.slasherxt.eraser.item.ItemEraser;
import net.slasherxt.eraser.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {
	public static Item pinkSlimeball;
	public static ItemEraser basicEraser;
	public static ItemEraser smallEraser;
	public static ItemEraser mediumEraser;
	public static ItemEraser largeEraser;
	public static ItemEraser megaEraser;
	
	public static void preInit() {
		pinkSlimeball = new Item().setCreativeTab(CreativeTabs.tabMaterials).setUnlocalizedName(Reference.MOD_ID + ":pinkSlimeball").setTextureName(Reference.MOD_ID + ":pinkSlimeball");
		
		basicEraser = new ItemEraser(0);
		smallEraser = new ItemEraser(1);
		mediumEraser = new ItemEraser(2);
		largeEraser = new ItemEraser(3);
		megaEraser = new ItemEraser(4);
	}
	
	public static void init() {
		registerItems(pinkSlimeball, "pinkSlimeball");
		
		registerItems(basicEraser, "basicEraser");
		registerItems(smallEraser, "smallEraser");
		registerItems(mediumEraser, "mediumEraser");
		registerItems(largeEraser, "largeEraser");
		registerItems(megaEraser, "megaEraser");
	}
	
	public static void registerItems(Item item, String name) {
		GameRegistry.registerItem(item, name);
	}
}
