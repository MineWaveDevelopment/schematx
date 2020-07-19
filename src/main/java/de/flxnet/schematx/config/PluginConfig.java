package de.flxnet.schematx.config;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class PluginConfig extends YamlConfig {

	public PluginConfig() {
		super("SchematX");
		init();
	}

	private void init() {
		FileConfiguration cfg = getConfig();
		cfg.addDefault("SchematX.SelectionToolMaterial", "GOLDEN_BOOTS");
		cfg.addDefault("SchematX.CloudUrl", "http://localhost:1337/");
		cfg.options().copyDefaults(true);
		this.save();
	}
	
	public Material getSelectionToolMaterial() {
		return Material.valueOf(getConfig().getString("SchematX.SelectionToolMaterial"));
	}
	
	public String getCloudUrl() {
		return getConfig().getString("SchematX.CloudUrl");
	}

}
