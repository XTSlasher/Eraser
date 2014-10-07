package net.slasherxt.eraser;

import net.slasherxt.eraser.handler.ConfigurationHandler;
import net.slasherxt.eraser.handler.GuiHandler;
import net.slasherxt.eraser.init.ModBlocks;
import net.slasherxt.eraser.init.ModItems;
import net.slasherxt.eraser.proxy.IProxy;
import net.slasherxt.eraser.reference.Reference;
import net.slasherxt.eraser.utility.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY)
public class Eraser {
	
	@Instance(Reference.MOD_ID)
	public static Eraser instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static IProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
		FMLCommonHandler.instance().bus().register(new GuiHandler());
		
		ModItems.preInit();
		ModBlocks.preInit();
		
		LogHelper.info("PreInit Complete");
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		ModItems.init();
		ModBlocks.init();
		ModBlocks.registerTileEntities();
		
		LogHelper.info("Init Complete");
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {		
		LogHelper.info("PostInit Complete");
		
	}
}
