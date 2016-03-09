package at.dafnik.ragemode.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import at.dafnik.ragemode.API.Holograms;
import at.dafnik.ragemode.API.Manager;
import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.API.TeleportAPI;
import at.dafnik.ragemode.API.Title;
import at.dafnik.ragemode.Items.Items;
import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;

public class PlayerRespawnListener implements Listener{
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event){
		Player player = event.getPlayer();
		
		event.setRespawnLocation(TeleportAPI.getRandomMapSpawnLocations());
	
		player.getInventory().clear();
		player.setFoodLevel(21);
		player.setVelocity(new Vector(0, 0, 0));
		player.setMaxHealth(20);
		player.setGlowing(false);
		Title.sendTabList(player, "§bRageMode");
		player.removeMetadata("killedWith", Main.getInstance());
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
			 public void run(){
		         for(Holograms holo : Library.powerup_list) {
		        	 holo.display(player);
		         }
		     }
		 }, 20);
		
		if(Library.spectatorlist.contains(player)) {
			player.setGameMode(GameMode.SPECTATOR);
			
		} else if(Library.ingameplayer.contains(player)){
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
				 public void run(){
					 if(Library.ingameplayer.contains(player)) {
						 player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 10));
						 player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 10));
						 player.getInventory().setHelmet(null);	
					 }
			     }
			 }, 1);
			
			Library.respawnsafe.add(player);
			
			if(Library.powerup_speedeffect.contains(player)) Library.powerup_speedeffect.remove(player);
			if(Library.powerup_doublejump.contains(player)) Library.powerup_doublejump.remove(player);
			
			player.removePotionEffect(PotionEffectType.REGENERATION);	
			player.setGameMode(GameMode.SURVIVAL);
			 
			 Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
				 public void run(){	
					 player.removePotionEffect(PotionEffectType.INVISIBILITY);
					 player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
					 Library.respawnsafe.remove(player);
					 Items.givePlayerItems(player);	
					 Manager.HelmetManagerMethode(player);
			     }
			 }, 60);
			 
		} else System.out.println(Strings.error_not_authenticated_player);
	}
}
