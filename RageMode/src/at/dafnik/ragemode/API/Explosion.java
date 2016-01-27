package at.dafnik.ragemode.API;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.MySQL.SQLCoins;
import net.minecraft.server.v1_8_R3.MathHelper;

public class Explosion {
	
	private Location loc = null;
	private double radius = 5;
	private String ground = null;
	private Player shooter;
	Main plugin;
	
	public Explosion(String ground, Location loc, Player shooter, Main main) {
		this.ground = ground;
		this.loc = loc;
		this.plugin = main;
		this.shooter = shooter;
		Explosioner();
	}

	private void Explosioner() {
		
		loc.getWorld().playEffect(loc, Effect.EXPLOSION_HUGE, 1);
		loc.getWorld().playSound(loc, Sound.EXPLODE, 1000.0F, 1.0F);
		
		if(ground == "bow" && SQLCoins.getBowPowerUpgrade(shooter.getUniqueId().toString())) radius = 7;
			
		for (Entity entities : loc.getWorld().getNearbyEntities(loc, radius, radius, radius)) {
			if (entities instanceof Player) {
				if(!plugin.spectatorlist.contains(entities)) {
					double distance = loc.distance(entities.getLocation());
					Location eloc = entities.getLocation();
					double damage = (radius - distance) * 7;
					
					Player killer = shooter;
					Player victim = (Player) entities;
					
					victim.removeMetadata("killedWith", plugin);
					
					if(ground == "bow") {
						if(SQLCoins.getBowPowerUpgrade(shooter.getUniqueId().toString())) damage = (radius - distance) * 9;		
						victim.setMetadata("killedWith", new FixedMetadataValue(plugin, "bow"));
					} else if(ground == "grenade"){
						victim.setMetadata("killedWith", new FixedMetadataValue(plugin, "grenade"));
					} else {
						System.out.println("[RageMode] ERROR: ExplosionAPI doesn't work well. No Explosionground can found!");
					}

					victim.damage(damage, killer);

					double d4 = eloc.distance(loc) / radius;

					if (d4 <= 1.0D) {
						double d5 = eloc.getBlockX() - loc.getBlockX();
						double d6 = eloc.getBlockY() + (double) victim.getEyeHeight() - loc.getBlockY();
						double d7 = eloc.getBlockZ() - loc.getBlockZ();
						double d9 = (double) MathHelper.sqrt(d5 * d5 + d6 * d6 + d7 * d7);

						if (d9 != 0.0D) {
							d5 /= d9;
							d6 /= d9;
							d7 /= d9;

							Vector vector = new Vector(d5,d6, d7).normalize();
							victim.setVelocity(vector);
						}
					}	
				}
			}
		}
	}
}
