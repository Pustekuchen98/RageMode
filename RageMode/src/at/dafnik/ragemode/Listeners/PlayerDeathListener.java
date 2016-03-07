package at.dafnik.ragemode.Listeners;

import java.util.HashMap;

import at.dafnik.ragemode.API.Holograms;
import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.API.Title;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.MySQL.SQLCoins;
import at.dafnik.ragemode.MySQL.SQLStats;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
  private Main plugin;
  
  private int hololong = 2 * 20;
  
  private int bowkill;
  private int knifekill;
  private int knifedeath;
  private int suicide;
  private int combataxekill;
  private int grenadekill;
  private int claymorekill;
  private int minekill;
  private int killstreakpoints;
  
  private HashMap<Player, Integer> killstreak = new HashMap<>();

  public PlayerDeathListener(Main main)
  {
    this.plugin = main;
    
	this.bowkill = main.getConfig().getInt("ragemode.points.bowkill");
	this.knifekill = main.getConfig().getInt("ragemode.points.knifekill");
	this.knifedeath = main.getConfig().getInt("ragemode.points.knifedeath");
	this.suicide = main.getConfig().getInt("ragemode.points.suicide");
	this.combataxekill = main.getConfig().getInt("ragemode.points.combat_axekill");
	this.grenadekill = main.getConfig().getInt("ragemode.points.grenadekill");
	this.claymorekill = main.getConfig().getInt("ragemode.points.clay_morekill");
	this.minekill = main.getConfig().getInt("ragemode.points.minekill");
	this.killstreakpoints = main.getConfig().getInt("ragemode.points.killstreakpoints");
  }

	@EventHandler
	public void PlayDeathEvent(PlayerDeathEvent event) {
		Player victim = event.getEntity();
		Player killer = victim.getKiller();

		//[Check] if MySQL add Death to Victim everytime he dies
		if (Main.isMySQL) SQLStats.addDeaths(victim.getUniqueId().toString(), Integer.valueOf(1));
		
		//[Check] if Killer is a Player
		if (killer instanceof Player) {
			//[Check] if Killer is Victim
			if(killer != victim) {
				String killground = null;
				
				String killername = killer.getDisplayName();
				String victimname = victim.getDisplayName();
				
				//[Check] if there is a killground
				if(victim.getMetadata("killedWith") != null && !victim.getMetadata("killedWith").isEmpty()) {

					killground = victim.getMetadata("killedWith").get(0).asString();
					
					//[Check] what killground is
					if (killground == "bow") {
						
						event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_bow);
						plugin.bar.setTitle(killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_bow);
						resetBossBar();
						killer.sendMessage(Strings.kill_points_plus + this.bowkill);
						holomaster(new Holograms(victim.getEyeLocation(), "§c+§6" + this.bowkill + Strings.kill_holo_points));
		
					} else if (killground == "combataxe") {
						
						event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_combat_axe);
						plugin.bar.setTitle(killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_combat_axe);
						resetBossBar();
						killer.sendMessage(Strings.kill_points_plus + this.combataxekill);
						holomaster(new Holograms(victim.getEyeLocation(), "§c+§6" + this.combataxekill + Strings.kill_holo_points));
						
					} else if (killground == "grenade") {
						
						event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname +Strings.kill_with + Strings.kill_with_grenade);
						plugin.bar.setTitle(killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_grenade);
						resetBossBar();
						killer.sendMessage(Strings.kill_points_plus + this.grenadekill);
						holomaster(new Holograms(victim.getEyeLocation(), "§c+§6" + this.grenadekill + Strings.kill_holo_points));
		
					} else if (killground == "mine") {
						
						event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_mine);
						plugin.bar.setTitle(killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_mine);
						resetBossBar();
						killer.sendMessage(Strings.kill_points_plus + this.minekill);
						holomaster(new Holograms(victim.getEyeLocation(), "§c+§6" + this.minekill + Strings.kill_holo_points));
		
					} else if (killground == "claymore") {
						
						event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_claymore);
						plugin.bar.setTitle(killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_claymore);
						resetBossBar();
						killer.sendMessage(Strings.kill_points_plus + this.claymorekill);
						holomaster(new Holograms(victim.getEyeLocation(), "§c+§6" + this.claymorekill + Strings.kill_holo_points));
		
					} else if(killground == "knife") {
						
						event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_knife);
						plugin.bar.setTitle(killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_knife);
						resetBossBar();
						killer.sendMessage(Strings.kill_points_plus + this.knifekill);
						victim.sendMessage(Strings.kill_points_negative + this.knifedeath);
						givePlayerPoints(victim, "knife_death");
						holomaster(new Holograms(victim.getEyeLocation(), "§c+§6" + this.knifekill + Strings.kill_holo_points));
					
					//[Check] what killground is. - Closed
					} else event.setDeathMessage(Main.pre + victim.getDisplayName() + Strings.kill_unknown_killer); 
				
				//[Check] if there is a killground. - Closed
				} else event.setDeathMessage(Main.pre + victim.getDisplayName() + Strings.kill_unknown_killer); 
				
				//[Check] if killground != null
				if(killground != null) {
					givePlayerPoints(killer, killground);
					
					//[Check] if killground isn't dead
					if(killer != null && !(killer.isDead())){
						killer.playSound(killer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1000.0F, 1.0F);
						Title.sendActionBar(killer, Strings.kill_your_points + String.valueOf(this.plugin.playerpoints.get(killer)));
					}
					
					//Killstreaker
					if(killstreak.get(killer) == null) killstreak.put(killer, 1);
					else killstreak.replace(killer, killstreak.get(killer), killstreak.get(killer) + 1);
								
					if(killstreak.get(killer) == 3 || killstreak.get(killer) == 5 || killstreak.get(killer) == 7) {
						givePlayerPoints(killer, "killstreak");
						if(Main.isMySQL) SQLCoins.addCoins(killer.getUniqueId().toString(), 10);
						Bukkit.broadcastMessage(Main.pre + killername + Strings.killstreak);
					}
					
					killer.sendMessage(Main.pre + Strings.kill_your_points + String.valueOf(plugin.playerpoints.get(killer)));
					killer.setPlayerListName(killer.getDisplayName() + " §8- [§6" + plugin.playerpoints.get(killer) + "§8]");
					
				//[Check] if killground != null. - Closed
				}
			
			//[Check] if Player is Victim - Closed
			} else {
				event.setDeathMessage(Main.pre + victim.getDisplayName() + Strings.kill_suicide);
				plugin.bar.setTitle(victim.getDisplayName() + Strings.kill_suicide);
				resetBossBar();
				victim.sendMessage("§c§l" + this.suicide);
				givePlayerPoints(victim, "suicide");
				holomaster(new Holograms(victim.getEyeLocation(), "§c" + this.suicide + Strings.kill_holo_points));
			}
		
		//[Check] if Killer is a Player
		} else event.setDeathMessage(Main.pre + victim.getDisplayName() + Strings.kill_unknown_killer);
		
		victim.removeMetadata("killedWith", plugin);
		
		event.setDroppedExp(0);
		event.getDrops().clear();
		
		//Auto Respawn
		Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
			public void run() {
				victim.spigot().respawn();
			}
		}, 30);
		
		//Killstreak
		if(killstreak.get(victim) == null) killstreak.put(victim, 0);
		else killstreak.replace(victim, killstreak.get(victim), 0);
		
		if(this.plugin.playerpoints.get(victim) == null) victim.sendMessage(Main.pre + Strings.kill_your_points + "0");
		else victim.sendMessage(Main.pre + Strings.kill_your_points + String.valueOf(plugin.playerpoints.get(victim)));
		
		victim.setPlayerListName(victim.getDisplayName() + " §8- [§6" + plugin.playerpoints.get(victim) + "§8]");
	}
	

	private void givePlayerPoints(Player player, String killground) {
		String playeruuid = player.getUniqueId().toString();
		int toBePoints = 0;
		
		if(killground == "bow") {
			toBePoints = this.bowkill;
			if(Main.isMySQL) SQLStats.addBowKills(playeruuid, 1);
			
		} else if(killground == "combataxe") {
			toBePoints = this.combataxekill;
			if(Main.isMySQL) SQLStats.addAxtKills(playeruuid, 1);
			
		} else if(killground == "knife") {
			toBePoints = this.knifekill;
			if(Main.isMySQL) SQLStats.addKnifeKills(playeruuid, 1);
			
		} else if(killground == "suicide") {
			toBePoints = this.suicide;
			if(Main.isMySQL) SQLStats.addSuicides(playeruuid, 1);
		}
		else if(killground == "grenade") toBePoints = this.grenadekill;
		else if(killground == "mine") toBePoints = this.minekill;
		else if(killground == "claymore") toBePoints = this.claymorekill;
		else if(killground == "knife_death") toBePoints = this.knifedeath;
		else if(killground == "killstreak") toBePoints = this.killstreakpoints;
		else System.out.println(Strings.error_playerdeath_points);
		
		if(killground != null && killground != "knife_death" && killground != "suicide" && killground != "killstreak") {
			if(Main.isMySQL) {
				SQLStats.addPoints(playeruuid, toBePoints);
				SQLStats.addKills(playeruuid, 1);
				SQLCoins.addCoins(playeruuid, 20);
			}
		}
		
		if (plugin.playerpoints.get(player) == null) {
			plugin.playerpoints.put(player, 0);
		} else {
			int points = plugin.playerpoints.get(player);
			if (points + toBePoints < 0) points = 0;
			else if (points + toBePoints >= 0) points += toBePoints;
			else System.out.println(Strings.error_playerdeath_points);
			
			plugin.playerpoints.put(player, points);
		}
	}
	
	private void holomaster(Holograms holo) {
		for(Player players : Bukkit.getOnlinePlayers()) {
			holo.display(players);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					holo.destroy(players);		
				}
			}, hololong);
		}
	}
	
	private void resetBossBar() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {			
				plugin.bar.setTitle("§6You §3are §cplaying §bRageMode");
			}
		}, 5*20);
	}
}