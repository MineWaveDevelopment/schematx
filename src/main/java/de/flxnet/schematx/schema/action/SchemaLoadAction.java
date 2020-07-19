package de.flxnet.schematx.schema.action;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.flxnet.schematx.SAccess;
import de.flxnet.schematx.action.AbstractAction;
import de.flxnet.schematx.block.PastedBlock;
import de.flxnet.schematx.block.SyncBlockQueue;
import de.flxnet.schematx.helper.ConsoleHelper;
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
public class SchemaLoadAction extends AbstractAction {

	@Getter @Setter
	private String name;
	
	public SchemaLoadAction(Player player, String name) {
		setPlayer(player);
		this.name = name;
	}

	@Override
	public void run() {

		if(!SAccess.getSchemaManager().exists(name)) {
			ConsoleHelper.player(getPlayer(), "§6A schema named §b" + name + " §6not exists");
			return;
		}
		
		Location paste = getPlayer().getTargetBlock(null, 3).getLocation();
		Schema schema = SAccess.getSchemaManager().getSchema(name);
		List<String> blocks = schema.getBlocks();

		int counter = 0;
		for (String block : blocks) {
			SchemaBlockDescription description = SchemaBlockDescription.fromString(block);
			Location p = paste.clone().add(description.getDifferenceX(), description.getDifferenceY(), description.getDifferenceZ());
			PastedBlock pastedBlock = new PastedBlock(p.getBlockX(), p.getBlockY(), p.getBlockZ(), description.createBukkitBlockData());
			SyncBlockQueue.getQueue(getPlayer().getWorld()).add(pastedBlock);
			counter++;
		}

		ConsoleHelper.player(getPlayer(), "§bSchema §a" + schema.getName() + " §bhas been queued §7(§d" + counter + " blocks§7)");
		
	}
	
}
