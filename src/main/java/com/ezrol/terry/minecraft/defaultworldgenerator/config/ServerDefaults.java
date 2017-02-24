package com.ezrol.terry.minecraft.defaultworldgenerator.config;

import com.ezrol.terry.minecraft.defaultworldgenerator.lib.Log;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraftforge.fml.server.FMLServerHandler;

public class ServerDefaults {
    public static void SetDefaults() {
        /*
         * Force the properties of the dedicated server to include our defaults
		 */

        try {
            MinecraftServer s = FMLServerHandler.instance().getServer();
            if (s.isDedicatedServer()) {
                DedicatedServer server = (DedicatedServer) s;
                server.getStringProperty("level-type", ConfigGeneralSettings.cfgWorldGenerator);
                server.getStringProperty("generator-settings", ConfigGeneralSettings.cfgCustomizationJson);
                server.getStringProperty("level-seed", ConfigGeneralSettings.cfgSeed);
            }
        } catch (Exception ex) {
            Log.fatal("Unable to set dedicated server properties: ");
            Log.fatal(ex);
        }
    }
}
