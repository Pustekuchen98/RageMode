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
import at.dafnik.ragemode.API.TeleportAPI;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class Listeners implements Listener {
	
	private Main plugin;
	
	public Listeners(Main main) {
		this.plugin = main;
	}
	
	List<Player> vectorlist = new ArrayList<Player>();
	Boolean happened = false;
	
	Location mapmiddle = null;
	
	@EventHandler
	public void onMove(PlayerMoveEvent event){
		Player player = event.getPlayer();
		Location loc = player.getLocation();
		
		if(Main.status == Status.INGAME){	
			if(event.getTo().getY() <= 0.0D){
				player.teleport(new TeleportAPI(plugin).getRandomMapSpawnLocations());
				
				if(plugin.ingameplayer.contains(player)) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
						 public void run(){
							 player.setHealth(0.0D);
						  }
					 }, 2);
					
					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 10, 1);
					
				} else if(plugin.spectatorlist.contains(player)) player.sendMessage(Strings.map_worldborder);
					
				else System.out.println(Strings.error_not_authenticated_player);
				
				return;
			}
			
			if(!happened) {
				if(mapmiddle == null) {
					mapmiddle = new TeleportAPI(plugin).getMapMiddleLocation();
					if(mapmiddle == null) {
						System.out.println(Strings.error_not_existing_map_middle_point);
						happened = true;
						return;
					}
				}
				
				Integer distance = plugin.getConfig().getInt("ragemode.mapspawn." + plugin.votedmap + ".mapradius");
				
				if(mapmiddle.distance(loc) > distance) {
					int aX = mapmiddle.getBlockX();
					int aY = mapmiddle.getBlockY();
					int aZ = mapmiddle.getBlockZ();
					
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
			}
		}
	}
	
	//Player damage
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		
		if(entity instanceof Player) {
			if(Main.status == Main.Status.RESTART || Main.status == Status.WIN || Main.status == Status.WARMUP || Main.status == Status.LOBBY 
					|| Main.status == Status.PRE_LOBBY) event.setCancelled(true);
				
			else if(Main.status == Main.Status.INGAME) event.setCancelled(false);
			
			if (event.getCause() == DamageCause.FALL) event.setDamage(0.0);
	
			if(entity instanceof Player) {
				Player player = (Player) entity;
				if(plugin.respawnsafe.contains(player)) {
					event.setCancelled(true);
				}
			}
		}
	}
	
	//Status on Ping Server
	@EventHandler
	public void Ping(ServerListPingEvent event) {
		if(Main.isBungee) {
			if (Main.status == Status.LOBBY || Main.status == Status.PRE_LOBBY) {
				if(Bukkit.getOnlinePlayers().size() < Bukkit.getMaxPlayers()) event.setMotd("§0[§aLobby§0]");
				else if(Bukkit.getOnlinePlayers().size() >= Bukkit.getMaxPlayers()) event.setMotd("§0[§6Lobby§0]");
			}	
			else if (Main.status == Status.WARMUP) event.setMotd("§0[§cWarmup§0]");
			else if (Main.status == Status.INGAME) event.setMotd("§0[§4Ingame§0]");
			else if (Main.status == Status.WIN || Main.status == Status.RESTART) event.setMotd("§0[§eWin§0]");		
			
		} else {
			if (Main.status == Status.LOBBY || Main.status == Status.PRE_LOBBY) {
				if(Bukkit.getOnlinePlayers().size() < Bukkit.getMaxPlayers()) event.setMotd("§4RageMode §8- §aLobby");	
				else if(Bukkit.getOnlinePlayers().size() >= Bukkit.getMaxPlayers()) event.setMotd("§4RageMode §8- §6Lobby");
				
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
			} else System.out.println(Strings.error_not_authenticated_player);
		}
	}
}