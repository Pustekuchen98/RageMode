package at.dafnik.ragemode.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import at.dafnik.ragemode.API.Holograms;
import at.dafnik.ragemode.API.Manager;
import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.API.TeleportAPI;
import at.dafnik.ragemode.API.Title;
import at.dafnik.ragemode.Holograms.HologramsScheduler;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;
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
		if(Main.status == Status.LOBBY || Main.status == Status.PRE_LOBBY) {
			Player player = event.getPlayer();
			
			Manager manager = new Manager(plugin);
			manager.DisplayNameManagerMethode(player);
			
			//Set standart Things
			event.setJoinMessage(ChatColor.GRAY + "> §r" + player.getDisplayName() + ChatColor.DARK_AQUA + " joined the game");
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
			player.getInventory().clear();
			player.setGameMode(GameMode.SURVIVAL);
			
			//Chat Message
			if(plugin.getPower(player)> 0 ){
				player.sendMessage(Strings.lobby_rotate_your_mouse);
			}
			
			//Tp to Lobby
			Location loc = new TeleportAPI(plugin).getLobbyLocation();
			if(loc == null) System.out.println("[RageMode] ERROR: You haven't set a spawnpoint!");
			else player.teleport(loc);
			
			if(Main.isMySQL && Main.isShop) {
				ItemStack i = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta imd = i.getItemMeta();
				imd.setDisplayName("§6Shop");
				List<String> ilore = new ArrayList<String>();
				ilore.add("§7Right click to use");
				imd.setLore(ilore);
				i.setItemMeta(imd);
				player.getInventory().setItem(0, i);
			}
		    
		    //Tablist Header and Footer
		    Title.sendTabList(player , "§bRageMode");
		   
		    Manager.HelmetManagerMethode(player);
		    
		    //Title Send
		    Title.sendTitle(player, fadein, stay, fadeout, "§6Welcome in");
		    Title.sendSubtitle(player, fadein, stay, fadeout, "§bRageMode");
		    
		    if(Main.isMySQL) {
		    	SQLStats.createPlayer(player.getUniqueId().toString());
		    	//Create Hologramm		    	
		    	new HologramsScheduler(plugin, player);
		    }
		    
		    if(Main.isShop) {
			    if(plugin.villager == null) {
				    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.villager = plugin.VillagerShopSpawner();
						}
					}, 40);
			    }
		    }
		    	   
		} else if(Main.status == Status.INGAME){
			
			Player player = event.getPlayer();
			
			//Tp to Map
			player.teleport(new TeleportAPI(plugin).getRandomMapSpawnLocations());
			
			event.setJoinMessage(null);
			player.setGameMode(GameMode.SPECTATOR);
			player.getInventory().clear();
			plugin.spectatorlist.add(player);
			
			Title.sendTabList(player , "§bRageMode");
			//Title Send
		    Title.sendTitle(player, fadein, stay, fadeout, "§6Spectator");
		    Title.sendSubtitle(player, fadein, 40, fadeout, "§bmode");
		    
		    Manager manager = new Manager(plugin);
			manager.DisplayNameManagerMethode(player);
			
			for(int i = 0; i < plugin.poweruphashmap.size(); i++) {
				Holograms holo = plugin.poweruphashmap.get(i);
				
				if(holo != null) holo.display(player);
			}
			
		} else {
			System.out.println("[RageMode] ERROR: PlayerJoinListener! Unknown Game Status!");
		}
	}
	
	//Fly Speed Control
	@EventHandler
	public void onFlySpeed(PlayerItemHeldEvent event){
		Player player = event.getPlayer();
		
		if(Main.status == Status.LOBBY || Main.status == Status.PRE_LOBBY){
			if(plugin.getPower(player) > 0){
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
			power = plugin.getPower(event.getPlayer());
		
			if(plugin.getServer().getOnlinePlayers().size() >= Bukkit.getMaxPlayers()){
				if(power == 0){
					event.disallow(Result.KICK_OTHER, Strings.error_game_is_full);
					return;
				}else{
					int ind = 0;
					while(ind < power){					
						for(Player player : Bukkit.getOnlinePlayers()){
							int kpower = plugin.getPower(player);						
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
			System.out.println("[RageMode] ERROR: Unknown Game State! PlayerPreLoginEvent!");
		}
	}
}
