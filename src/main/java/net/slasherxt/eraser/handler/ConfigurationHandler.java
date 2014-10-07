package net.slasherxt.eraser.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.slasherxt.eraser.reference.Reference;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {
	public static Configuration configuration;
	
	// Config Variables Here
	
	public static void init(File configFile) {
		if(configuration == null) {
			configuration = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if(event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration() {
		
		// Config Values Here
		
		if(configuration.hasChanged()) {
			configuration.save();
		}
	}
}
