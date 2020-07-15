package de.flxnet.schematx.helper;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.flxnet.schematx.block.Cuboid;
import net.minecraft.server.v1_16_R1.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_16_R1.ParticleType;

/**
 * Software by FLXnet More info at FLXnet.de Copyright (c) 2015-2020 by FLXnet
 * 
 * @author Felix
 */
public class CuboidHelper {

	public static HashSet<Location> cuboidEdges(Location low, Location high) {
		HashSet<Location> toReturn = new HashSet<Location>();

		// These variables are the components of the displacement between the two
		// corners
		// The difference in x, y, and z
		int dx = high.getBlockX() - low.getBlockX();
		int dy = high.getBlockY() - low.getBlockY();
		int dz = high.getBlockZ() - low.getBlockZ();
		
		for (int x = 0; x <= dx; x++) {
			Location temp = low.add(x, 0, 0);
			toReturn.add(temp);
			toReturn.add(temp.clone().add(0, dy, 0));
			toReturn.add(temp.clone().add(0, 0, dz));
			toReturn.add(temp.clone().add(0, dy, dz));
		}
		
		for (int y = 0; y <= dy; y++) {
			Location temp = low.add(0, y, 0);
			toReturn.add(temp);
			toReturn.add(temp.clone().add(dx, 0, 0));
			toReturn.add(temp.clone().add(0, 0, dz));
			toReturn.add(temp.clone().add(dx, 0, dz));
		}
		
		for (int z = 0; z <= dz; z++) {
			Location temp = low.add(0, 0, z);
			toReturn.add(temp);
			toReturn.add(temp.clone().add(dx, 0, 0));
			toReturn.add(temp.clone().add(0, dy, 0));
			toReturn.add(temp.clone().add(dx, dy, 0));
		}
		
		return toReturn;
	}

	public static void markCuboid(Player player, Cuboid cuboid, ParticleType particleType) {
		
		for(Location location : cuboidEdges(cuboid.getStartLocation(), cuboid.getEndLocation())) {
			sendParticles(player, particleType, location.getX(), location.getY(), location.getZ());
		}
		
	}
	
	public static void hilightCuboid(Player player, Cuboid cuboid, ParticleType particleType) {

		for (double x = cuboid.getLowerX(); x <= cuboid.getUpperY() + 1; x++) {
			for (double y = cuboid.getLowerY(); y <= cuboid.getUpperY() + 1; y++) {
				for (double z = cuboid.getLowerZ(); z <= cuboid.getLowerZ() + 1; z++) {
					boolean edge = false;
					if (((int) x == cuboid.getLowerX() || (int) x == cuboid.getUpperX() + 1)
							&& ((int) y == cuboid.getLowerY() || (int) y == cuboid.getUpperY() + 1))
						edge = true;
					if (((int) z == cuboid.getLowerZ() || (int) z == cuboid.getUpperY() + 1)
							&& ((int) y == cuboid.getLowerY() || (int) y == cuboid.getUpperY() + 1))
						edge = true;
					if (((int) x == cuboid.getLowerX() || (int) x == cuboid.getUpperX() + 1)
							&& ((int) z == cuboid.getLowerZ() || (int) z == cuboid.getUpperZ() + 1))
						edge = true;

					if (edge) {
						sendParticles(player, particleType, x, y, z);
					}

				}
			}
		}

	}

	public static void sendParticles(Player player, ParticleType particleType, double x, double y, double z) {
		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particleType, true, x, y, z, 0f, 0f, 0f,
				0f, 1);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

}
