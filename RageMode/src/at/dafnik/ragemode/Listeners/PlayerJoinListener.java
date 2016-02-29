package at.dafnik.ragemode.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.potion.PotionEffectType;

import at.dafnik.ragemode.API.Holograms;
import at.dafnik.ragemode.API.Manager;
import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.API.TeleportAPI;
import at.dafnik.ragemode.API.Title;
import at.dafnik.ragemode.Items.Items;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.MySQL.SQLCoins;
import at.dafnik.ragemode.MySQL.SQLStats;

public class PlayerJoinListener implements Listener{

	private Main plugin;
	private int power;
	private int fadein = 5;
	private int fadeout = 5;
	private int stay = 20;
	
	public PlayerJoinListener(Main main){
		this.plugin = main;
	}
	
	@EventHandler
	public void Join(PlayerJoinEvent event){
		Player player = event.getPlayer();
		
		player.setHealth(20);
		player.setSaturation(20);
		player.setMaxHealth(20);
		player.setWalkSpeed((float) 0.2);
		player.setExp(0);
		player.setLevel(0);
		player.removePotionEffect(PotionEffectType.REGENERATION);
		player.removePotionEffect(PotionEffectType.SPEED);
		player.removePotionEffect(PotionEffectType.JUMP);
		player.removePotionEffect(PotionEffectType.BLINDNESS);
		player.removePotionEffect(PotionEffectType.SLOW);
		player.removePotionEffect(PotionEffectType.INVISIBILITY);
		player.setFireTicks(0);
		player.getInventory().clear();
		
		if(Main.status == Status.LOBBY || Main.status == Status.PRE_LOBBY) {
			Manager.DisplayNameManagerMethode(player, "normal");
			
			event.setJoinMessage("§8> §r" + player.getDisplayName() + "§3 joined the game");
			
			player.setGameMode(GameMode.SURVIVAL);
			
			Location loc = new TeleportAPI(plugin).getLobbyLocation();
			if(loc == null) System.out.println(Strings.error_not_existing_lobbyspawn);
			else player.teleport(loc);
			
			Manager.HelmetManagerMethode(player);
			 
			Title.sendTabList(player, "§bRageMode");
			Title.sendTitle(player, fadein, stay, fadeout, "§6Welcome in");
			Title.sendSubtitle(player, fadein, stay, fadeout, "§bRageMode");
			
			if(Main.getPower(player) > 0 ) player.sendMessage(Strings.lobby_rotate_your_mouse);
			
			if(Main.isMySQL && Main.isShop) {
				Items.givePlayerShopItem(player);
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
					 public void run(){
						  if(plugin.villager != null && plugin.villagerholo != null) plugin.villagerholo.display(player);
				     }
				 }, 20);
			}
			
			if(Main.isMySQL) {
				SQLStats.createPlayer(player.getUniqueId().toString());
				
				Bukkit.getScheduler().runTaskLater(this.plugin, new Runnable(){
					 public void run(){
						 String w = plugin.getConfig().getString("ragemode.hologram.world");
							double x = plugin.getConfig().getDouble("ragemode.hologram.x");
							double y = plugin.getConfig().getDouble("ragemode.hologram.y");
							double z = plugin.getConfig().getDouble("ragemode.hologram.z");
							
							Location loc = new Location(Bukkit.getWorld(w), x, y, z);
							List<String> lines = new ArrayList<String>();
							lines.add("§bRageMode §6Stats");
							
							int kills = SQLStats.getKills(player.getUniqueId().toString());
							int deaths = SQLStats.getDeaths(player.getUniqueId().toString());
							int playedgames = SQLStats.getPlayedGames(player.getUniqueId().toString());
							int wongames = SQLStats.getWonGames(player.getUniqueId().toString());
							int points = SQLStats.getPoints(player.getUniqueId().toString());
							int coins = SQLCoins.getCoins(player.getUniqueId().toString());
							int bowkills = SQLStats.getBowKills(player.getUniqueId().toString());
							int knifekills = SQLStats.getKnifeKills(player.getUniqueId().toString());
							int axtkills = SQLStats.getAxtKills(player.getUniqueId().toString());
							int resets = SQLStats.getResets(player.getUniqueId().toString());
							int suicides = SQLStats.getSuicides(player.getUniqueId().toString());
							
							float KD;
							try {
								KD = ((float) kills) / ((float) deaths);
							} catch (ArithmeticException ex) {
								KD = kills;
							}
							float rund = (float)(((int)(KD*100))/100.0);
							
							lines.add("§3Points§8: §e" + points);
							lines.add("§3Coins§8: §e" + coins);
							lines.add("§3Kills§8: §e" + kills);
							lines.add("§3Bow Kills§8: §e" + bowkills);
							lines.add("§3Knife Kills§8: §e" + knifekills);
							lines.add("§3Axt Kills§8: §e" + axtkills);
							lines.add("§3Suicides§8: §e" + suicides);
							lines.add("§3Deaths§8: §e" + deaths);
							lines.add("§3Kills/Deaths§8: §e" + rund);
							lines.add("§3Played games§8: §e" + playedgames);
							lines.add("§3Won games§8: §e" + wongames);
							lines.add("§3Stats resets§8: §e" + resets);
							
							Holograms holo = new Holograms(loc, lines);
							holo.display(player);	
				     }
				 }, 20*2);
			}    	    
		    	   
		} else if(Main.status == Status.INGAME){
			Manager.DisplayNameManagerMethode(player, "spectator");
			
			plugin.spectatorlist.add(player);
			
			event.setJoinMessage(null);
			
			player.teleport(new TeleportAPI(plugin).getRandomMapSpawnLocations());
			player.setGameMode(GameMode.SPECTATOR);		
			
			Title.sendTabList(player , "§bRageMode");
		    Title.sendTitle(player, fadein, stay, fadeout, "§6Spectator");
		    Title.sendSubtitle(player, fadein, 40, fadeout, "§bmode");
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
				 public void run(){
			         for(Holograms holo : plugin.poweruplist) {
			        	 holo.display(player);
			         }
			     }
			 }, 20);
			
		} else System.out.println(Strings.error_unkown_gamestate);
	}
	
	//Fly Speed Control
	@EventHandler
	public void onFlySpeed(PlayerItemHeldEvent event){
		Player player = event.getPlayer();
		
		if(Main.status == Status.LOBBY || Main.status == Status.PRE_LOBBY){
			if(Main.getPower(player) > 0){
				player.setAllowFlight(true);
				player.setFlying(true);
				
				int slot = event.getNewSlot()+1;
				float speed = (float) slot / 10;
				player.setFlySpeed(speed);
			}else{
				player.setAllowFlight(false);
			}
		}
	}
	
	//Kick Full Event
	@EventHandler
	public void PlayerPreLogin(PlayerLoginEvent event){		
		if(Main.status == Status.WARMUP || Main.status == Status.WIN || Main.status == Status.RESTART){
			event.disallow(Result.KICK_OTHER, Strings.error_cant_join_at_the_moment);
			
		} else if(Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) {
			power = Main.getPower(event.getPlayer());
		
			if(plugin.getServer().getOnlinePlayers().size() >= Bukkit.getMaxPlayers()){
				if(power == 0){
					event.disallow(Result.KICK_OTHER, Strings.error_game_is_full);
					return;
				}else{
					int ind = 0;
					while(ind < power){					
						for(Player player : Bukkit.getOnlinePlayers()){
							int kpower = Main.getPower(player);						
							if(kpower == ind){
								if(kpower == 0){
									player.kickPlayer(Strings.error_you_kicked);
									event.allow();
									return;
								}
								player.kickPlayer(Strings.error_you_kicked);
								event.allow();
								return;
							}
						}
						ind++;
					}					
					event.disallow(Result.KICK_OTHER, Strings.error_game_is_full);
					return;
				}
			} else {
				event.allow();
			}
		} else if(Main.status == Status.INGAME) {
			event.allow();
		} else {
			event.disallow(Result.KICK_OTHER, Strings.error_game_is_full);
			System.out.println(Strings.error_unkown_gamestate);
		}
	}
}
