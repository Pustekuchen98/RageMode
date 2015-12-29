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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerDeathListener
  implements Listener
{
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
		
		//[Check] if MySQL     add Death to Victim everytime he dies
		if (Main.isMySQL) SQLStats.addDeaths(victim.getUniqueId().toString(), Integer.valueOf(1));
		
		//Check if Victim is killer
		if(victim == killer) {
			suicided(killer, event);
		} else {
			//[Open] Check if Killer is Player 
			if (killer instanceof Player) {
					
				//[Open] [Check] If killed with Bow
				if (plugin.playerbowlist.contains(victim)) {
					Player killer_bow = plugin.playerbow.get(victim);
					plugin.playerbowlist.remove(victim);
					plugin.playerbow.remove(victim);
						
					BetterDeath(killer_bow, victim, "bow", event);
				
				//[Else] if killed with knife
				} else if (plugin.playerknifelist.contains(victim)) {
					Player killer_knife = plugin.playerknife.get(victim);
					plugin.playerknifelist.remove(victim);
					plugin.playerknife.remove(victim);
						
					BetterDeath(killer_knife, victim, "knife", event);
					
				//[Else] Never will happen but when Killer was Player and nothing found
				} else {
					event.setDroppedExp(0);
					event.getDrops().clear();
					event.setDeathMessage(Main.pre + victim.getDisplayName() + Strings.kill_unknown_killer);
					if(Main.isDebug) System.out.println("[Debug]> " + victim.getName() + "'s unknown death state: Killer is a Player but nothing found which killed him!");
				}
			
			//[Check] If killed with Bow
			} else if (plugin.playerbowlist.contains(victim)) {	
				Player killer_bow = plugin.playerbow.get(victim);
				plugin.playerbowlist.remove(victim);
				plugin.playerbow.remove(victim);
					
				BetterDeath(killer_bow, victim, "bow", event);
				
			//When killer wasn't Player but combat axe
			} else if (this.plugin.playercombataxelist.contains(victim)) {
				Player killer_combat_axe = plugin.playercombataxe.get(victim);
				plugin.playercombataxe.remove(victim);
				plugin.playercombataxelist.remove(victim);
	
				BetterDeath(killer_combat_axe, victim, "combat_axe", event);	
			
			//[Else] if killed with grenade
			}else if(plugin.playergrenadelist.contains(victim)) {
				Player grenade_killer = plugin.playergrenade.get(victim);
				plugin.playergrenade.remove(victim);
				plugin.playergrenadelist.remove(killer);
	
				BetterDeath(grenade_killer, victim, "grenade", event);
				
			//When killer wasn't Player but Mine
			} else if (this.plugin.playerminelist.contains(victim)) {
				Player killer_mine = plugin.playermine.get(victim);
				plugin.playerminelist.remove(victim);
				plugin.playermine.remove(victim);
	
				BetterDeath(killer_mine, victim, "mine", event);
			
			//When killer wasn't Player but claymore
			} else if (this.plugin.playerclaymorelist.contains(victim)) {
				Player killer_clay_more = plugin.playerclaymore.get(victim);
				plugin.playerclaymorelist.remove(victim);
				plugin.playerclaymore.remove(victim);
	
				BetterDeath(killer_clay_more, victim, "clay_more", event);
				
			//When really really nothing found which killed the Player
			} else {
				event.setDroppedExp(0);
				event.getDrops().clear();
				event.setDeathMessage(Main.pre + victim.getDisplayName() + Strings.kill_unknown_killer);
				if(Main.isDebug) System.out.println("[Debug]> " + victim.getName() + "'s unknown death state: Killer wasn't a player and really nothing found which killed him!");
			}
		}
		
		plugin.killGroundremover(victim);
		
		//Auto Respawn
		Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
			public void run() {
				victim.spigot().respawn();
			}
		}, 30);
		
		//Killstreak
		if(killstreak.get(victim) == null) {
			killstreak.put(victim, 0);
		} else {
			killstreak.replace(victim, killstreak.get(victim), 0);
		}
	}
	
	private void BetterDeath(Player killer, Player victim, String killground, PlayerDeathEvent event) {
		if(killer != null) {
			if(killer == victim) {
				suicided(killer, event);
			} else {
				String killername = killer.getDisplayName();
				String victimname = victim.getDisplayName();
				String killeruuid = killer.getUniqueId().toString();	
				
				//[Check] if MySQL     add Kill
				if (Main.isMySQL) SQLStats.addKills(killeruuid, 1);
				if(Main.isMySQL) SQLCoins.addCoins(killeruuid, 10);
				
				if(killground == "bow") {
					
					event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with + "§6Bow");
					killer.sendMessage(Main.pre + " §a§l+" + this.bowkill);
					PlayerPoints(killer, this.bowkill);
					if (Main.isMySQL) SQLStats.addPoints(killeruuid, this.bowkill);
					if (Main.isMySQL) SQLStats.addBowKills(killeruuid, 1);
					holomaster(new Holograms(victim.getEyeLocation(), "§a+" + this.bowkill + Strings.kill_holo_points));
					
				} else if(killground == "knife") {
					
					event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with + "§6Knife");
					killer.sendMessage(Main.pre + " §a§l+" + this.knifekill);
					victim.sendMessage(Main.pre + " §c§l" + this.knifedeath);
					PlayerPoints(killer, this.knifekill);
					PlayerPoints(victim, this.knifedeath);
					if(Main.isMySQL) SQLStats.addPoints(killeruuid, this.knifekill);
					if(Main.isMySQL) SQLStats.addKnifeKills(killeruuid, 1);
					holomaster(new Holograms(victim.getEyeLocation(), "§a+" + this.knifekill + Strings.kill_holo_points));
					
				} else if(killground == "combat_axe") {
					
					event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with_combat_axe);
					killer.sendMessage("§a§l+" + this.combataxekill);
					PlayerPoints(killer, this.combataxekill);
					if (Main.isMySQL) SQLStats.addPoints(killeruuid, this.combataxekill);
					if(Main.isMySQL) SQLStats.addAxtKills(killeruuid, 1);
					holomaster(new Holograms(victim.getEyeLocation(), "§a+" + this.combataxekill + Strings.kill_holo_points));
					
				} else if(killground == "clay_more") {
					
					event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with_claymore);
					killer.sendMessage("§a§l+" + this.claymorekill);
					PlayerPoints(killer, this.claymorekill);
					if (Main.isMySQL) SQLStats.addPoints(killeruuid, this.claymorekill);
					holomaster(new Holograms(victim.getEyeLocation(), "§a+" + this.claymorekill + Strings.kill_holo_points));	
				
				} else if(killground == "grenade") {
					
					event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with_grenade);
					killer.sendMessage("§a§l+" + this.grenadekill);
					PlayerPoints(killer, this.grenadekill);
					if (Main.isMySQL) SQLStats.addPoints(killeruuid, this.grenadekill);
					holomaster(new Holograms(victim.getEyeLocation(), "§a+" + this.grenadekill + Strings.kill_holo_points));
					
				} else if(killground == "mine") {
					
					event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with_mine);
					killer.sendMessage("§a§l+" + this.minekill);
					PlayerPoints(killer, this.minekill);
					if (Main.isMySQL) SQLStats.addPoints(killeruuid, this.minekill);
					holomaster(new Holograms(victim.getEyeLocation(), "§a+" + this.minekill + Strings.kill_holo_points));
					
				} else {
					Bukkit.broadcastMessage("§cSomething went terrible wrong");
				}
						
				if(killer != null && !(killer.isDead())){
					killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 1000.0F, 1.0F);
					killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 35, 6));
					Title.sendActionBar(killer, Strings.kill_your_points + String.valueOf(this.plugin.playerpoints.get(killer)));
				}
				
				//Killstreaker
				if(killstreak.get(killer) == null) {
					killstreak.put(killer, 1);
				} else {
					killstreak.replace(killer, killstreak.get(killer), killstreak.get(killer) + 1);
				}
				
				if(killstreak.get(killer) == 3) {
					killer.sendMessage("§a§l+" + this.killstreakpoints);
					PlayerPoints(killer, this.killstreakpoints);
					SQLStats.addPoints(killeruuid, this.killstreakpoints);
					Bukkit.broadcastMessage(Main.pre + killername + Strings.killstreak_3);
				
				} else if(killstreak.get(killer) == 5) {
					killer.sendMessage("§a§l+" + this.killstreakpoints);
					PlayerPoints(killer, this.killstreakpoints);
					SQLStats.addPoints(killeruuid, this.killstreakpoints);
					Bukkit.broadcastMessage(Main.pre + killername + Strings.killstreak_5);
					
				} else if(killstreak.get(killer) == 7) {
					killer.sendMessage("§a§l+" + this.killstreakpoints);
					PlayerPoints(killer, this.killstreakpoints);
					SQLStats.addPoints(killeruuid, this.killstreakpoints);
					Bukkit.broadcastMessage(Main.pre + killername + Strings.killstreak_7);
					
				}
				
				killer.sendMessage(Main.pre + Strings.kill_your_points + String.valueOf(this.plugin.playerpoints.get(killer)));
				if(this.plugin.playerpoints.get(victim) == null) {
					victim.sendMessage(Main.pre + Strings.kill_your_points + "0");
				} else {
					victim.sendMessage(Main.pre + Strings.kill_your_points + String.valueOf(this.plugin.playerpoints.get(victim)));
				}
				
				killer.setPlayerListName(killer.getDisplayName() + " §8- [§6" + plugin.playerpoints.get(killer) + "§8]");
				victim.setPlayerListName(victim.getDisplayName() + " §8- [§6" + plugin.playerpoints.get(victim) + "§8]");
				
				event.setDroppedExp(0);
				event.getDrops().clear();
			}
			
		} else {
			event.setDroppedExp(0);
			event.getDrops().clear();
			event.setDeathMessage(Main.pre + victim.getDisplayName() + Strings.kill_unknown_killer);
			if(Main.isDebug) System.out.println("[Debug]> " + victim.getName() + "'s unknown death state: Killer was a player and killer was identified but then something went wrong!");
		}	
	}

	private void PlayerPoints(Player player, int killground) {
		if (this.plugin.playerpoints.get(player) == null) {
			this.plugin.playerpoints.put(player, 0);
		} else {
			int points = plugin.playerpoints.get(player);
			if (points + killground < 0) {
				points = 0;
			} else if (points + killground >= 0) {
				points += killground;
			} else {
				System.out.println("[RageMode] ERROR: Something went terrible wrong by the PlayerPoints! Nothing will happen!");
			}
			plugin.playerpoints.put(player, points);
		}
	}
	
	private void suicided(Player killer, PlayerDeathEvent event) {
		event.setDeathMessage(Main.pre + killer.getDisplayName() + Strings.kill_suicide);
		event.setDroppedExp(0);
		event.getDrops().clear();	
		killer.sendMessage("§c§l" + this.suicide);
		//Remove Playerpoints
		PlayerPoints(killer, this.suicide);
		killer.setPlayerListName(killer.getDisplayName() + " §8- [§6" + plugin.playerpoints.get(killer) + "§8]");
		SQLStats.addSuicides(killer.getUniqueId().toString(), 1);
		holomaster(new Holograms(killer.getEyeLocation(), "§c" + this.suicide + Strings.kill_holo_points));
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