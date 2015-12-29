package at.dafnik.ragemode.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.Vector;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class Listeners implements Listener {
	
	private Main plugin;
	
	public Listeners(Main main) {
		this.plugin = main;
	}
	
	List<Player> vectorlist = new ArrayList<Player>();
	Boolean happened = false;
	
	@EventHandler
	public void onMove(PlayerMoveEvent event){
		Player player = event.getPlayer();
		
		if(Main.status == Status.INGAME){
			Location loc = player.getLocation();
			
			if(event.getTo().getY() <= 0.0D){
				Location newloc = new Location(loc.getWorld(), loc.getX(), loc.getY()+20, loc.getZ());
				player.teleport(newloc);
				
				if(plugin.ingameplayer.contains(player)) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
						 public void run(){
							 player.setHealth(0.0D);
						  }
					 }, 5);
					
					player.playSound(player.getLocation(), Sound.HURT_FLESH, 10, 1);
				} else if(plugin.spectatorlist.contains(player)) {
					player.sendMessage(Strings.map_worldborder);
				} else {
					System.out.println("[RageMode] WARNING: Not registered player out of the map!");
				}
			}
			
			String w = plugin.getConfig().getString("ragemode.mapspawn." + plugin.votedmap + ".middlepoint.world");
			double x = plugin.getConfig().getDouble("ragemode.mapspawn." + plugin.votedmap + ".middlepoint.x");
			double y = plugin.getConfig().getDouble("ragemode.mapspawn." + plugin.votedmap + ".middlepoint.y");
			double z = plugin.getConfig().getDouble("ragemode.mapspawn." + plugin.votedmap + ".middlepoint.z");
			if(w != null) {
				Location middle = new Location(Bukkit.getWorld(w), x, y, z);
				
				Integer distance = plugin.getConfig().getInt("ragemode.mapspawn." + plugin.votedmap + ".mapradius");
				
				if(middle.distance(loc) > distance) {
					int aX = middle.getBlockX();
					int aY = middle.getBlockY();
					int aZ = middle.getBlockZ();
					
					int bX = loc.getBlockX();
					int bY = loc.getBlockY();
					int bZ = loc.getBlockZ();
					
					int X = aX - bX;
					int Y = aY - bY;
					int Z = aZ - bZ;
					
					Vector vector = new Vector(X, Y, Z).normalize();
					vector.multiply(0.8D);
					vector.setY(0.5D);
					player.setVelocity(vector);
					for(int i = 0; i < 10; i++) {
						player.getWorld().playEffect(loc, Effect.WITCH_MAGIC, 3);	
						player.getWorld().playEffect(loc, Effect.LAVA_POP, 3);	
					}
					player.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 3);
					player.getWorld().playEffect(loc, Effect.SMOKE, 3);
					
					if(!vectorlist.contains(player)) {
						player.sendMessage(Strings.map_worldborder);
						vectorlist.add(player);
						
						Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							@Override
							public void run() {
								vectorlist.remove(player);
							}
						}, 3*20);
					}
				}
			} else {
				if(!happened) {
					System.out.println("[RageMode] WARNING: You haven't set the Map Middle Point and the approximately mapradius!");
					happened = true;
				}
			}
		}
	}
	
	//Player damage
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		
		if(entity instanceof Player) {
			if(Main.status == Main.Status.RESTART || Main.status == Status.WIN || Main.status == Status.WARMUP || Main.status == Status.LOBBY || Main.status == Status.PRE_LOBBY) {
				event.setCancelled(true);
				
			}else if(Main.status == Main.Status.INGAME){
				event.setCancelled(false);
			}
			
			if (event.getCause() == DamageCause.FALL) {
				event.setDamage(0.0);
	
			}
			
			Player player = (Player) entity;
			if(plugin.respawnsafe.contains(player)) {
				event.setCancelled(true);
			}
			
		}
	}
	
	//Status on Ping Server
	@EventHandler
	public void Ping(ServerListPingEvent event) {
		if(Main.isBungee) {
			if (Main.status == Status.LOBBY || Main.status == Status.PRE_LOBBY) {
				if(Bukkit.getOnlinePlayers().size() < Bukkit.getMaxPlayers()){
					event.setMotd("§0[§aLobby§0]");
				
				}else if(Bukkit.getOnlinePlayers().size() >= Bukkit.getMaxPlayers()){
					event.setMotd("§0[§6Lobby§0]");
				}
			} else if (Main.status == Status.WARMUP) {
				event.setMotd("§0[§cWarmup§0]");
			} else if (Main.status == Status.INGAME) {
				event.setMotd("§0[§4Ingame§0]");
	
			} else if (Main.status == Status.WIN || Main.status == Status.RESTART) {
				event.setMotd("§0[§eWin§0]");
			}
			
		} else {
			if (Main.status == Status.LOBBY || Main.status == Status.PRE_LOBBY) {
				if(Bukkit.getOnlinePlayers().size() < Bukkit.getMaxPlayers()){
					event.setMotd("§4RageMode §8- §aLobby");
				
				}else if(Bukkit.getOnlinePlayers().size() >= Bukkit.getMaxPlayers()){
					event.setMotd("§4RageMode §8- §6Lobby");
				}
			} else if (Main.status == Status.WARMUP) {
				event.setMotd("§4RageMode §8- §cWarmup\n"
							+ "§3Played Map§8: §6" + plugin.votedmap);
			} else if (Main.status == Status.INGAME) {
				event.setMotd("§4RageMode §8- §4Ingame\n"
						+ "§3Played Map§8: §6" + plugin.votedmap);
			} else if (Main.status == Status.WIN || Main.status == Status.RESTART) {
				if(plugin.lobbytasks.wm.ig.playerwinner != null) {
					event.setMotd("§4RageMode §8- §eWin\n"
								+ "§3Winner§8: §6" + plugin.lobbytasks.wm.ig.playerwinner.getDisplayName() + " §8- §6" + plugin.playerpoints.get(plugin.lobbytasks.wm.ig.playerwinner));
				} else {
					event.setMotd("§4RageMode §8- §eWin\n"
							+ "§4No §3Winner");
				}
			}
		}
	}
}