package de.flxnet.schematx.schema;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.gson.annotations.Expose;

import de.flxnet.schematx.block.Cuboid;
import lombok.Getter;
import lombok.Setter;

/**
 * Software by FLXnet
 * More info at FLXnet.de
 * Copyright (c) 2015-2020 by FLXnet
 * @author Felix
 */
public class Schema {

	@Expose
	@Getter @Setter
	private UUID id;
	
	@Expose
	@Getter @Setter
	private String name;
	
	@Expose
	@Getter @Setter
	private UUID owner;
	
	@Expose
	@Getter @Setter
	private long created;
	
	@Expose
	@Getter
	private List<String> blocks;
	
	public Schema(String name, UUID owner, List<SchemaBlockDescription> blocks) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.owner = owner;
		this.created = System.currentTimeMillis();
		this.blocks = blocks.stream().map(SchemaBlockDescription::toString).collect(Collectors.toList());
	}
	
	public Schema(String name, UUID owner, Cuboid cuboid) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.owner = owner;
		this.created = System.currentTimeMillis();
		this.blocks = cuboid.getSchemaBlockDescriptionList().stream().map(SchemaBlockDescription::toString).collect(Collectors.toList());
	}
	
}
