package at.dafnik.ragemode.Listeners;

import java.util.HashMap;

import at.dafnik.ragemode.API.Holograms;
import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.API.Title;
import at.dafnik.ragemode.Main.Main;
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
		
		if (killer instanceof Player) {
			if(killer != victim) {
				String killground = null;
				
				String killername = killer.getDisplayName();
				String victimname = victim.getDisplayName();
				
				if (plugin.playerbowlist.contains(victim)) {
					plugin.playerbowlist.remove(victim);
					
					event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with + "§6Bow");
					killer.sendMessage(Main.pre + " §a§l+" + this.bowkill);
					holomaster(new Holograms(victim.getEyeLocation(), "§c+§6" + this.bowkill + Strings.kill_holo_points));
					killground = "bow";
	
				} else if (plugin.playercombataxelist.contains(victim)) {
					plugin.playercombataxelist.remove(victim);
					
					event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with_combat_axe);
					killer.sendMessage("§a§l+" + this.combataxekill);
					holomaster(new Holograms(victim.getEyeLocation(), "§c+§6" + this.combataxekill + Strings.kill_holo_points));
					killground = "combataxe";
					
				} else if (plugin.playergrenadelist.contains(victim)) {
					plugin.playergrenadelist.remove(victim);
					
					event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with_grenade);
					killer.sendMessage("§a§l+" + this.grenadekill);
					holomaster(new Holograms(victim.getEyeLocation(), "§c+§6" + this.grenadekill + Strings.kill_holo_points));
					killground = "grenade";
	
				} else if (plugin.playerminelist.contains(victim)) {
					plugin.playerminelist.remove(victim);
					
					event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with_mine);
					killer.sendMessage("§a§l+" + this.minekill);
					holomaster(new Holograms(victim.getEyeLocation(), "§c+§6" + this.minekill + Strings.kill_holo_points));
					killground = "mine";
	
				} else if (plugin.playerclaymorelist.contains(victim)) {
					plugin.playerclaymorelist.remove(victim);
					
					event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with_claymore);
					killer.sendMessage("§a§l+" + this.claymorekill);
					holomaster(new Holograms(victim.getEyeLocation(), "§c+§6" + this.claymorekill + Strings.kill_holo_points));
					killground = "claymore";
	
				} else if(plugin.playerknifelist.contains(victim)) {
					plugin.playerknifelist.remove(victim);
					
					event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with + "§6Knife");
					killer.sendMessage(Main.pre + " §a§l+" + this.knifekill);
					victim.sendMessage(Main.pre + " §c§l" + this.knifedeath);
					givePlayerPoints(victim, "knife_death");
					holomaster(new Holograms(victim.getEyeLocation(), "§c+§6" + this.knifekill + Strings.kill_holo_points));
					killground = "knife_kill";
	
				} else event.setDeathMessage(Main.pre + victim.getDisplayName() + Strings.kill_unknown_killer); 
				
				if(killground != null) {
					givePlayerPoints(killer, killground);
					
					if(killer != null && !(killer.isDead())){
						killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 1000.0F, 1.0F);
						Title.sendActionBar(killer, Strings.kill_your_points + String.valueOf(this.plugin.playerpoints.get(killer)));
					}
					
					//Killstreaker
					if(killstreak.get(killer) == null) killstreak.put(killer, 1);
					else killstreak.replace(killer, killstreak.get(killer), killstreak.get(killer) + 1);
								
					if(killstreak.get(killer) == 3 || killstreak.get(killer) == 5 || killstreak.get(killer) == 7) {
						givePlayerPoints(killer, "killstreak");
						Bukkit.broadcastMessage(Main.pre + killername + Strings.killstreak);
					}
					
					killer.sendMessage(Main.pre + Strings.kill_your_points + String.valueOf(plugin.playerpoints.get(killer)));
					killer.setPlayerListName(killer.getDisplayName() + " §8- [§6" + plugin.playerpoints.get(killer) + "§8]");
				}
				
			} else {
				event.setDeathMessage(Main.pre + victim.getDisplayName() + Strings.kill_suicide);
				victim.sendMessage("§c§l" + this.suicide);
				givePlayerPoints(victim, "suicide");
				victim.setPlayerListName(victim.getDisplayName() + " §8- [§6" + plugin.playerpoints.get(victim) + "§8]");
				holomaster(new Holograms(victim.getEyeLocation(), "§c-§6" + this.suicide + Strings.kill_holo_points));
			}
			
		} else event.setDeathMessage(Main.pre + victim.getDisplayName() + Strings.kill_unknown_killer);
		
		plugin.killGroundremover(victim);	
		
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
			SQLStats.addBowKills(playeruuid, 1);
			
		} else if(killground == "combataxe") {
			toBePoints = this.combataxekill;
			SQLStats.addAxtKills(playeruuid, 1);
			
		} else if(killground == "knife_kill") {
			toBePoints = this.knifekill;
			SQLStats.addKnifeKills(playeruuid, 1);
			
		} else if(killground == "suicide") {
			toBePoints = this.suicide;
			SQLStats.addSuicides(playeruuid, 1);
		}
		else if(killground == "grenade") toBePoints = this.grenadekill;
		else if(killground == "mine") toBePoints = this.minekill;
		else if(killground == "claymore") toBePoints = this.claymorekill;
		else if(killground == "knife_death") toBePoints = this.knifedeath;
		else if(killground == "killstreak") toBePoints = this.killstreakpoints;
		else System.out.println("[RageMode] ERROR: PlayerDeathListener - givePlayerPoints - Unknown killground exception! Nothing will happen");
		
		if(killground != null && killground != "knife_death") SQLStats.addPoints(player.getUniqueId().toString(), toBePoints);
		
		if (plugin.playerpoints.get(player) == null) {
			plugin.playerpoints.put(player, 0);
		} else {
			int points = plugin.playerpoints.get(player);
			if (points + toBePoints < 0) points = 0;
			else if (points + toBePoints >= 0) points += toBePoints;
			else System.out.println("[RageMode] ERROR: PlayerDeathListener - givePlayerPoints - I don't know why!");
			
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
}