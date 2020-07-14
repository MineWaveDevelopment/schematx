package de.flxnet.schematx.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public abstract class YamlConfig {
	
    private FileConfiguration config;

    private File file;
    private String name;
    private final String filenameExtension = ".yml";

    public YamlConfig(String name) {
    	this.name = name;
        File folder = new File("plugins/" + name);
        this.file = new File(folder, name + filenameExtension);

        folder.mkdirs();
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void save() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @return FileConfiguration
     */
    public FileConfiguration getConfig()
	{
		return config;
	}
    
    /**
     * @return String
     */
    public String getName()
	{
		return name;
	}
    
    /**
     * @return String
     */
    public String getFilenameExtension()
	{
		return filenameExtension;
	}
    
    /**
     * @return String
     */
    public String getFullFileName()
    {
    	return this.name + this.filenameExtension;
    }
}
