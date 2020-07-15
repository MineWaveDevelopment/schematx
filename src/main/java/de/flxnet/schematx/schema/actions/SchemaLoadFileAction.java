package de.flxnet.schematx.schema.actions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import de.flxnet.schematx.SAccess;
import de.flxnet.schematx.action.AbstractAction;
import de.flxnet.schematx.block.PastedBlock;
import de.flxnet.schematx.helper.ConsoleHelper;
import de.flxnet.schematx.helper.JsonHelper;
import de.flxnet.schematx.schema.Schema;
import de.flxnet.schematx.schema.SchemaBlockDescription;
import lombok.Getter;
import lombok.Setter;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class SchemaLoadFileAction extends AbstractAction {

	@Getter @Setter
	private String name;
	
	public SchemaLoadFileAction(Player player, String name) {
		setPlayer(player);
		this.name = name;
	}

	@Override
	public void run() {

		Location paste = getPlayer().getTargetBlock(null, 3).getLocation();

		File file = new File(SAccess.getInstance().getDataFolder(), name + ".json");

		try {

			Schema schema = JsonHelper.getGson().fromJson(new JsonReader(new FileReader(file)), Schema.class);
			List<String> blocks = schema.getBlocks();

			int counter = 0;

			for (String block : blocks) {

				SchemaBlockDescription description = SchemaBlockDescription.fromString(block);
				Location p = paste.clone().add(description.getDifferenceX(), description.getDifferenceY(),
						description.getDifferenceZ());
				PastedBlock pastedBlock = new PastedBlock(p.getBlockX(), p.getBlockY(), p.getBlockZ(),
						description.getBlockDataString());
				PastedBlock.BlockQueue.getQueue(getPlayer().getWorld()).add(pastedBlock);
				counter++;

			}

			ConsoleHelper.player(getPlayer(), "§bQueued " + counter + " PastedBlock's");
			
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
