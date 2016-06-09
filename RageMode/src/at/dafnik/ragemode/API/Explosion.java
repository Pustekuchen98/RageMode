package at.dafnik.ragemode.API;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.MySQL.SQLCoins;
import net.minecraft.server.v1_10_R1.MathHelper;

public class Explosion {
	
	private Location loc = null;
	private double radius = 5;
	private String ground = null;
	private Player shooter;
	
	public Explosion(String ground, Location loc, Player shooter) {
		this.ground = ground;
		this.loc = loc;
		this.shooter = shooter;
		Explosioner();
	}

	private void Explosioner() {
		
		loc.getWorld().playEffect(loc, Effect.EXPLOSION_HUGE, 1);
		loc.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1000.0F, 1.0F);
		
		if(Main.isMySQL && Main.isShop) if(ground == "bow" && SQLCoins.getBowPowerUpgrade(shooter.getUniqueId().toString())) radius = 7;
			
		for (Entity entities : loc.getWorld().getNearbyEntities(loc, radius, radius, radius)) {
			if (entities instanceof Player) {
				if(!Library.spectatorlist.contains(entities)) {
					double distance = loc.distance(entities.getLocation());
					Location eloc = entities.getLocation();
					double damage = (radius - distance) * 7;					
					
					Player killer = shooter;
					Player victim = (Player) entities;
						
					if(victim.getMetadata("killedWith") != null && !victim.getMetadata("killedWith").isEmpty()) victim.removeMetadata("killedWith", Main.getInstance());
					
					if(ground == "bow") {
						if(Main.isMySQL && Main.isShop) if(SQLCoins.getBowPowerUpgrade(shooter.getUniqueId().toString())) damage = (radius - distance) * 9;		
						victim.setMetadata("killedWith", new FixedMetadataValue(Main.getInstance(), "bow"));
					} else if(ground == "grenade") victim.setMetadata("killedWith", new FixedMetadataValue(Main.getInstance(), "grenade"));
					else if(ground == "c4") victim.setMetadata("killedWith", new FixedMetadataValue(Main.getInstance(), "c4"));
					else System.out.println(Strings.error_explosion_no_killground);

					victim.damage(damage, killer);
					
					if(Main.isMySQL && Main.isShop) {
						if(SQLCoins.getSpectralArrowUpgrade(shooter.getUniqueId().toString())) {
							victim.setGlowing(true);
							
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
								@Override
								public void run() {			
									victim.setGlowing(false);
								}
							}, ((long)(damage/2))*20);
						}
					}

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