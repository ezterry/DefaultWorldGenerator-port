package com.ezrol.terry.minecraft.defaultworldgenerator.gui;

import java.io.IOException;

import com.ezrol.terry.minecraft.defaultworldgenerator.config.ConfigGeneralSettings;
import com.ezrol.terry.minecraft.defaultworldgenerator.lib.Log;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateWorld;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.WorldType;

public class GuiCreateCustomWorld extends GuiCreateWorld {
	public GuiCreateCustomWorld(GuiScreen screen) {
		super(screen);
		// set the customization here so a user can change it
		this.chunkProviderSettingsJson = ConfigGeneralSettings.customizationJson;

		try {
			int WorldGenerator = 0;

			for (int i = 0; i < WorldType.WORLD_TYPES.length; i++) {
				if (WorldType.WORLD_TYPES[i] != null && WorldType.WORLD_TYPES[i].getCanBeCreated()) {
					if (WorldType.WORLD_TYPES[i].getWorldTypeName()
							.equalsIgnoreCase(ConfigGeneralSettings.generalWorldGenerator)) {
						WorldGenerator = WorldType.WORLD_TYPES[i].getWorldTypeID();
						Log.info("Changed world type to " + WorldType.WORLD_TYPES[i].getTranslateName());
					}
				}
			}
			GuiReflectHelper.selectedIndex.setInt(this, WorldGenerator);

			if (!ConfigGeneralSettings.generalSeed.equals("")) {
				GuiReflectHelper.worldSeed.set(this, ConfigGeneralSettings.generalSeed);
			}
		} catch (Exception ex) {
			Log.fatal("Fatal Error:");
			Log.fatal(ex);
		}
	}

	private void hideWorldConfig() {
		try {
			if (ConfigGeneralSettings.generalLockWorldGenerator) {
				((GuiButton) GuiReflectHelper.btnMapType.get(this)).visible = false;
				((GuiButton) GuiReflectHelper.btnCustomizeType.get(this)).visible = false;
				((GuiButton) GuiReflectHelper.btnMapFeatures.get(this)).xPosition = this.width / 2 - 75;
			}
		} catch (Exception ex) {
			Log.fatal("Error Hiding Buttons:");
			Log.fatal(ex);
		}

	}

	@Override
	public void initGui() {
		super.initGui();

		try {

			GuiReflectHelper.updateDisplayState.invoke(this);

		} catch (Exception ex) {
			Log.fatal("Fatal Error:");
			Log.fatal(ex);
		}
		this.hideWorldConfig();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		this.hideWorldConfig();
	}

	/**
	 * Draws the screen and all the components in it. Args : mouseX, mouseY,
	 * renderPartialTicks We need to replace the version in CreateWorldGui if we
	 * are hiding the override
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (!ConfigGeneralSettings.generalLockWorldGenerator) {
			super.drawScreen(mouseX, mouseY, partialTicks);
			return;
		}
		try {
			if (!GuiReflectHelper.inMoreWorldOptionsDisplay.getBoolean(this)) {
				super.drawScreen(mouseX, mouseY, partialTicks);
				return;
			}
			// We really need to update the draw
			this.drawDefaultBackground();
			this.drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.create", new Object[0]),
					this.width / 2, 20, -1);

			this.drawString(this.fontRendererObj, I18n.format("selectWorld.enterSeed", new Object[0]),
					this.width / 2 - 100, 47, -6250336);
			this.drawString(this.fontRendererObj, I18n.format("selectWorld.seedInfo", new Object[0]),
					this.width / 2 - 100, 85, -6250336);

			if (((GuiButton) GuiReflectHelper.btnMapFeatures.get(this)).visible) {
				this.drawString(this.fontRendererObj, I18n.format("selectWorld.mapFeatures.info", new Object[0]),
						this.width / 2 - 73, 122, -6250336);
			}

			if (((GuiButton) GuiReflectHelper.btnAllowCommands.get(this)).visible) {
				this.drawString(this.fontRendererObj, I18n.format("selectWorld.allowCommands.info", new Object[0]),
						this.width / 2 - 150, 172, -6250336);
			}

			((GuiTextField) GuiReflectHelper.worldSeedField.get(this)).drawTextBox();

			for (int i = 0; i < this.buttonList.size(); ++i) {
				((GuiButton) this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY);
			}

			for (int j = 0; j < this.labelList.size(); ++j) {
				((GuiLabel) this.labelList.get(j)).drawLabel(this.mc, mouseX, mouseY);
			}
		} catch (Exception ex) {
			Log.fatal("Fatal Error Drawing Screen:");
			Log.fatal(ex);
		}
	}
}
