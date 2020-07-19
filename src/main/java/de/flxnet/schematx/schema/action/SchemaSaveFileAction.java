package de.flxnet.schematx.schema.action;

import java.io.File;
import java.io.IOException;

import org.bukkit.entity.Player;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import de.flxnet.schematx.SAccess;
import de.flxnet.schematx.action.AbstractAction;
import de.flxnet.schematx.block.Cuboid;
import de.flxnet.schematx.helper.ConsoleHelper;
import de.flxnet.schematx.helper.JsonHelper;
import de.flxnet.schematx.helper.PersistenceHelper;
import de.flxnet.schematx.schema.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class SchemaSaveFileAction extends AbstractAction {

	@Getter @Setter
	private String name;
	
	public SchemaSaveFileAction(Player player, String name) {
		setPlayer(player);
		this.name = name;
	}
	
	@Override
	public void run() {
		
		Cuboid cuboid = SAccess.getSelectionManager().getCuboidSelection(getPlayer());
		
		if(cuboid == null) {
			ConsoleHelper.player(getPlayer(), "§6Please make a selection out of two points first");
			return;
		}
		
		Schema schema = new Schema(name, getPlayer().getUniqueId(), cuboid);
		String json = JsonHelper.getGson().toJson(schema);
		
		try {
			Files.write(json, new File(PersistenceHelper.getSchemaFolder(), schema.getName() + ".schema"), Charsets.UTF_8);
			ConsoleHelper.player(getPlayer(), "§bSchema §a" + name + " §bhas been saved to file");
		} catch (IOException e) {
			e.printStackTrace();
			ConsoleHelper.player(getPlayer(), "§cCould not save schema §7" + name + " §cto file");
		}
		
	}

}
