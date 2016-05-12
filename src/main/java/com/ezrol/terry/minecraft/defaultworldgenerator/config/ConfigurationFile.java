package com.ezrol.terry.minecraft.defaultworldgenerator.config;

import java.io.File;

import com.ezrol.terry.minecraft.defaultworldgenerator.helper.ConfigurationHelper;

import net.minecraftforge.common.config.Configuration;

public class ConfigurationFile {
	public static Configuration configuration;

	public static Configuration init(File configFile) {
		if (configuration == null) {
			configuration = new Configuration(configFile);
			loadConfiguration();
		}
		return configuration;
	}

	public static void loadConfiguration() {
		ConfigGeneralSettings.generalWorldGenerator = ConfigurationHelper.getString(configuration, "World Generator",
				"general", "default", "The world generator to select by default", true);
		ConfigGeneralSettings.generalSeed = ConfigurationHelper.getString(configuration, "Seed",
				"general", "", "The Suggested Seed for the pack, leave blank for a random seed, note" +
		                       "the player can change this even if locked", true);
		ConfigGeneralSettings.generalWorldGenerator = ConfigurationHelper.getString(configuration, "World Generator",
				"general", "default", "The world generator to select by default", true);
		ConfigGeneralSettings.generalShowDebugWorldGenerators = ConfigurationHelper.getBoolean(configuration,
				"Show World Generators in Log", "general", false,
				"Enabling this will display all world generators installed, useful for debug");
		ConfigGeneralSettings.customizationJson = ConfigurationHelper.getString(configuration, "CustomizationJson",
				"general", "", "The world customization string (JSON, or super flat string)", true);
		ConfigGeneralSettings.generalLockWorldGenerator = ConfigurationHelper.getBoolean(configuration,
				"Lock Worldtype", "general", false, "Prevent the user from changing the world type.");

		if (configuration.hasChanged()) {
			configuration.save();
		}
	}
}
