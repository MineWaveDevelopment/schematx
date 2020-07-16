package de.flxnet.schematx.schema;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.gson.stream.JsonReader;

import de.flxnet.schematx.Schematx;
import de.flxnet.schematx.helper.ConsoleHelper;
import de.flxnet.schematx.helper.GzipHelper;
import de.flxnet.schematx.helper.JsonHelper;
import de.flxnet.schematx.helper.PersistenceHelper;
import lombok.Getter;
import lombok.Setter;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class SchemaManager {

	@Getter @Setter
	private boolean fullBuffer;
	
	@Getter
	private List<Schema> schemata;
	
	public SchemaManager(boolean fullBuffer) {
		this.fullBuffer = fullBuffer;
		this.schemata = Lists.newArrayList();
		
		if(fullBuffer) {
			ConsoleHelper.console("§3all schemata will be loaded to the buffer, caused by attribute §7fullBuffer §aTRUE");
			loadAll();
		}
		
		Bukkit.getScheduler().runTaskTimerAsynchronously(Schematx.getInstance(), () -> {
			schemata.forEach(schema -> {
				SchemaResult result = save(schema);
				if(result != SchemaResult.Saved) {
					//ConsoleHelper.consoleInfo("Not saving schema " + schema.getName() + " (" + result + ")");
					return;
				}
				ConsoleHelper.consoleSuccess("Schema " + schema.getName() + " has been saved from buffer (" + result + ")");
			});
		}, 20 * 10, 20 * 10);
		
	}
	
	public int getBufferSize() {
		return schemata.size(); 
	}
	
	public boolean add(Schema schema) {
		return schemata.add(schema);
	}
	
	public Schema getSchema(String name) {
		if(hasInBuffer(name)) return getSchemaFromBuffer(name);
		SchemaResult result = load(name);
		if(result != SchemaResult.Loaded) return null;
		return getSchemaFromBuffer(name);
	}
	
	public boolean exists(String name) {
		if(hasInBuffer(name)) return true;
		if(load(name) == SchemaResult.Exists) return true;
		return false;
	}
	
	public boolean hasInBuffer(String name) {
		return schemata.stream().filter(schema -> schema.getName().equalsIgnoreCase(name)).findAny().isPresent();
	}
	
	public Schema getSchemaFromBuffer(String name) {
		return schemata.stream().filter(schema -> schema.getName().equalsIgnoreCase(name)).findAny().get();
	}
	
	public SchemaResult save(Schema schema) {
		File schemaFile = new File(PersistenceHelper.getSchemaFolder(), schema.getName() + ".schema");
		if(schemaFile.exists()) return SchemaResult.Exists;
		try {
			
			String jsonDataUncompressed = schema.getJsonData();
			byte[] jsonDataCompressed = GzipHelper.compress(jsonDataUncompressed);
			
			int uncompressedSize = jsonDataUncompressed.getBytes().length;
			int compressedSize = jsonDataCompressed.length;
			
			System.out.println(schema.getName() + " -> uncompressedSize = " + uncompressedSize + ", compressedSize = " + compressedSize);
			
			Files.write(jsonDataUncompressed, new File(PersistenceHelper.getSchemaFolder(), schema.getName() + ".schema"), Charsets.UTF_8);
			return SchemaResult.Saved;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SchemaResult.Error;
	}
	
	public SchemaResult load(String name) {
		File schemaFile = new File(PersistenceHelper.getSchemaFolder(), name + ".schema");
		if(!schemaFile.exists()) return SchemaResult.NotExists;
		if(hasInBuffer(name)) return SchemaResult.Buffered;
		try {
			Schema schema = JsonHelper.getGson().fromJson(new JsonReader(new FileReader(schemaFile)), Schema.class);
			boolean added = add(schema);
			if(!added) return SchemaResult.Error;
			return SchemaResult.Loaded;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SchemaResult.Error;
	}
	
	public void loadAll() {
		int counter = 0;
		for(String fileName : PersistenceHelper.getSchemaFolder().list()) {
			SchemaResult result = load(FilenameUtils.removeExtension(fileName));
			if(result != SchemaResult.Loaded) {
				ConsoleHelper.consoleError("Could not load schema from file §7" + fileName + " §d(" + result + ")");
			} else counter++;
		}
		ConsoleHelper.consoleInfo(counter + " schemata has been loaded from file into the buffer");
	}
	
}
