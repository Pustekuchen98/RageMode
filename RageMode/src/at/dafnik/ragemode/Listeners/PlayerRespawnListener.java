package at.dafnik.ragemode.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.dafnik.ragemode.API.Holograms;
import at.dafnik.ragemode.API.Manager;
import at.dafnik.ragemode.API.TeleportAPI;
import at.dafnik.ragemode.API.Title;
import at.dafnik.ragemode.Items.GetItems;
import at.dafnik.ragemode.Main.Main;

public class PlayerRespawnListener implements Listener{
	
	private Main plugin;
	private GetItems getItems;
	
	public PlayerRespawnListener(Main main){
		this.plugin = main;
		this.getItems = new GetItems();
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event){
		Player player = event.getPlayer();
		
		//Tp to Map
		event.setRespawnLocation(new TeleportAPI(plugin).getRandomMapSpawnLocations());
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
			 public void run(){
				 player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 10));
		         player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 10));
		          
		         for(int i = 0; i < plugin.poweruphashmap.size(); i++) {
		        	 Holograms holo = plugin.poweruphashmap.get(i);
		        	 
		        	 if(holo != null) holo.display(player);
		        	 
		         }
		     }
		 }, 1);
		
		if(plugin.spectatorlist.contains(player)) {
			player.setGameMode(GameMode.SPECTATOR);
			
		} else {
			plugin.respawnsafe.add(player);
			
			player.removePotionEffect(PotionEffectType.REGENERATION);
			player.getInventory().setHelmet(null);		
			player.setGameMode(GameMode.SURVIVAL);
			 
			 Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable(){
				 public void run(){	
					 player.removePotionEffect(PotionEffectType.INVISIBILITY);
					 player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
					 plugin.respawnsafe.remove(player);
					 getItems.getItems(player);	
					 Manager.HelmetManagerMethode(player);
			     }
			 }, 60);
		}
		
		player.getInventory().clear();
		player.setFoodLevel(21);
		player.setMaxHealth(20);
		Title.sendTabList(player, "§bRageMode");
	}
}
