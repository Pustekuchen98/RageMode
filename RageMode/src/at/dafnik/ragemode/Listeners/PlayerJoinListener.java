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
import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.Main.PowerSystem;
import at.dafnik.ragemode.MySQL.SQLCoins;
import at.dafnik.ragemode.MySQL.SQLStats;

public class PlayerJoinListener implements Listener {
	
	private int power;
	
	@EventHandler
	public void Join(PlayerJoinEvent event){
		Player player = event.getPlayer();
		
		player.setHealth(20);
		player.setSaturation(20);
		player.setMaxHealth(20);
		player.setWalkSpeed((float) 0.2);
		player.setExp(0);
		player.setLevel(0);
		player.setGlowing(false);
		player.removePotionEffect(PotionEffectType.REGENERATION);
		player.removePotionEffect(PotionEffectType.SPEED);
		player.removePotionEffect(PotionEffectType.JUMP);
		player.removePotionEffect(PotionEffectType.BLINDNESS);
		player.removePotionEffect(PotionEffectType.SLOW);
		player.removePotionEffect(PotionEffectType.INVISIBILITY);
		player.removePotionEffect(PotionEffectType.LEVITATION);
		player.setFireTicks(0);
		player.getInventory().clear();
		
		if(Main.status == Status.LOBBY || Main.status == Status.PRE_LOBBY) {			
			Manager.DisplayNameManagerMethode(player, "normal");
			
			event.setJoinMessage("§7» §r" + player.getDisplayName() + " §7joined the game");
			
			player.setGameMode(GameMode.SURVIVAL);
			
			Location loc = TeleportAPI.getLobbyLocation();
			if(loc == null) System.out.println(Strings.error_not_existing_lobbyspawn);
			else player.teleport(loc);
			
			Manager.HelmetManagerMethode(player);
			 
			Title.sendTabList(player, "§cRageMode");
			Title.sendTitle(player, Library.fadein, Library.stay, Library.fadeout, "§6Welcome in");
			Title.sendSubtitle(player, Library.fadein, Library.stay, Library.fadeout, "§bRageMode");
			
			if(PowerSystem.getPower(player) > 0 ) player.sendMessage(Strings.lobby_rotate_your_mouse);
			
			if(Main.isMySQL && Main.isShop) {
				Items.givePlayerShopItem(player);
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
					 public void run(){
						  if(Library.villager != null && Library.villagerholo != null) Library.villagerholo.display(player);
				     }
				 }, 20);
			}
			
			if(Main.isMySQL) {
				SQLStats.createPlayer(player.getUniqueId().toString());
				
				Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable(){
					 public void run(){
						
						String w = Main.getInstance().getConfig().getString("ragemode.hologram.world");
						double x = Main.getInstance().getConfig().getDouble("ragemode.hologram.x");
						double y = Main.getInstance().getConfig().getDouble("ragemode.hologram.y");
						double z = Main.getInstance().getConfig().getDouble("ragemode.hologram.z");
						
						Location loc = new Location(Bukkit.getWorld(w), x, y, z);
					 	List<String> lines = new ArrayList<String>();
						
						int kills = SQLStats.getKills(player.getUniqueId().toString());
						int deaths = SQLStats.getDeaths(player.getUniqueId().toString());
						int playedgames = SQLStats.getPlayedGames(player.getUniqueId().toString());
						int wongames = SQLStats.getWonGames(player.getUniqueId().toString());
						int points = SQLStats.getPoints(player.getUniqueId().toString());
						int bowkills = SQLStats.getBowKills(player.getUniqueId().toString());
						int knifekills = SQLStats.getKnifeKills(player.getUniqueId().toString());
						int axtkills = SQLStats.getAxtKills(player.getUniqueId().toString());
						int suicides = SQLStats.getSuicides(player.getUniqueId().toString());
						int coins = SQLCoins.getCoins(player.getUniqueId().toString());
						int resets = SQLStats.getResets(player.getUniqueId().toString());

						float KD;
						try {
							KD = ((float) kills) / ((float) deaths);
						} catch (ArithmeticException ex) {
							KD = kills;
						}
						float rund = (float) (((int) (KD * 100)) / 100.0);

						float siegwahrscheinlichkeit;
						try {
							siegwahrscheinlichkeit = (((float) wongames) / ((float) playedgames)) * 100;
						} catch (ArithmeticException ex) {
							siegwahrscheinlichkeit = 0;
						}
						int rundsieg = (int) siegwahrscheinlichkeit;

						lines.add(Strings.stats_your_name_first + player.getDisplayName() + Strings.stats_your_name_two);
						lines.add(Strings.stats_points + points);
						lines.add(Strings.stats_coins + coins);
						lines.add(Strings.stats_allkills + kills);
						lines.add(Strings.stats_explosivekills + bowkills);
						lines.add(Strings.stats_knifekills + knifekills);
						lines.add(Strings.stats_axtkills + axtkills);
						lines.add(Strings.stats_deaths + deaths);
						lines.add(Strings.stats_suicides + suicides);
						lines.add(Strings.stats_kd + rund);
						lines.add(Strings.stats_playedgames + playedgames);
						lines.add(Strings.stats_wongames + wongames);
						lines.add(Strings.stats_winningchances + rundsieg + "§7%");
						lines.add(Strings.stats_statsreset + resets);
						
						Holograms holo = new Holograms(loc, lines);
						holo.display(player);	
				     }
				 }, 20*2);
			}    	    
		    	   
		} else if(Main.status == Status.INGAME){
			Manager.DisplayNameManagerMethode(player, "spectator");
			
			Library.spectatorlist.add(player);
			Library.bar.addPlayer(player);
			
			event.setJoinMessage(null);
			
			player.teleport(TeleportAPI.getRandomMapSpawnLocations());
			player.setGameMode(GameMode.SPECTATOR);		
			player.setAllowFlight(true);
			player.setFlying(true);
			
			Title.sendTabList(player , "§bRageMode");
		    Title.sendTitle(player, Library.fadein, Library.stay, Library.fadeout, "§6Spectator");
		    Title.sendSubtitle(player, Library.fadein, 40, Library.fadeout, "§bmode");
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
				 public void run(){
			         for(Holograms holo : Library.powerup_list) {
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
			if(PowerSystem.getPower(player) > 0){
				player.setAllowFlight(true);
				player.setFlying(true);
				
				int slot = event.getNewSlot()+1;
				float speed = (float) slot / 10;
				player.setFlySpeed(speed);
			} else {
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
			power = PowerSystem.getPower(event.getPlayer());
		
			if(Main.getInstance().getServer().getOnlinePlayers().size() >= Bukkit.getMaxPlayers()){
				if(power == 0){
					event.disallow(Result.KICK_OTHER, Strings.error_game_is_full);
					return;
				}else{
					int ind = 0;
					while(ind < power){					
						for(Player player : Bukkit.getOnlinePlayers()){
							int kpower = PowerSystem.getPower(player);						
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
