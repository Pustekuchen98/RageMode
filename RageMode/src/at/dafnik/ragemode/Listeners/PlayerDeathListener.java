package at.dafnik.ragemode.Listeners;

import java.util.HashMap;

import at.dafnik.ragemode.API.Holograms;
import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.API.Title;
import at.dafnik.ragemode.Main.Library;
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
  
  private int hololong = 2 * 20;
  
  private int bowkill;
  private int knifekill;
  private int knifedeath;
  private int suicide;
  private int combataxekill;
  private int grenadekill;
  private int claymorekill;
  private int minekill;
  private int c4kill;
  private int killstreakpoints;
  
  private HashMap<Player, Integer> killstreak = new HashMap<>();

  public PlayerDeathListener()
  {   
	this.bowkill = Main.getInstance().getConfig().getInt("ragemode.points.bowkill");
	this.knifekill = Main.getInstance().getConfig().getInt("ragemode.points.knifekill");
	this.knifedeath = Main.getInstance().getConfig().getInt("ragemode.points.knifedeath");
	this.suicide = Main.getInstance().getConfig().getInt("ragemode.points.suicide");
	this.combataxekill = Main.getInstance().getConfig().getInt("ragemode.points.combat_axekill");
	this.grenadekill = Main.getInstance().getConfig().getInt("ragemode.points.grenadekill");
	this.claymorekill = Main.getInstance().getConfig().getInt("ragemode.points.clay_morekill");
	this.minekill = Main.getInstance().getConfig().getInt("ragemode.points.minekill");
	this.c4kill = Main.getInstance().getConfig().getInt("ragemode.points.c4");
	this.killstreakpoints = Main.getInstance().getConfig().getInt("ragemode.points.killstreakpoints");
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
				String killreason = null;
				
				String killername = killer.getDisplayName();
				String victimname = victim.getDisplayName();
				
				//[Check] if there is a killreason
				if(victim.getMetadata("killedWith") != null && !victim.getMetadata("killedWith").isEmpty()) {

					killreason = victim.getMetadata("killedWith").get(0).asString();
					
					//[Check] what killreason is
					if (killreason.contains("bow")) {
						
						event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_bow);
						Library.bar.setTitle(killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_bow);
						resetBossBar();
						killer.sendMessage(Strings.kill_points_plus + this.bowkill);
						createHologram(new Holograms(victim.getEyeLocation(), "§c+§6" + this.bowkill + Strings.kill_holo_points));
		
					} else if (killreason.contains("combataxe")) {
						
						event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_combat_axe);
						Library.bar.setTitle(killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_combat_axe);
						resetBossBar();
						killer.sendMessage(Strings.kill_points_plus + this.combataxekill);
						createHologram(new Holograms(victim.getEyeLocation(), "§c+§6" + this.combataxekill + Strings.kill_holo_points));
						
					} else if (killreason.contains("grenade")) {
						
						event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname +Strings.kill_with + Strings.kill_with_grenade);
						Library.bar.setTitle(killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_grenade);
						resetBossBar();
						killer.sendMessage(Strings.kill_points_plus + this.grenadekill);
						createHologram(new Holograms(victim.getEyeLocation(), "§c+§6" + this.grenadekill + Strings.kill_holo_points));
		
					} else if (killreason.contains("mine")) {
						
						event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_mine);
						Library.bar.setTitle(killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_mine);
						resetBossBar();
						killer.sendMessage(Strings.kill_points_plus + this.minekill);
						createHologram(new Holograms(victim.getEyeLocation(), "§c+§6" + this.minekill + Strings.kill_holo_points));
		
					} else if (killreason.contains("claymore")) {
						
						event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_claymore);
						Library.bar.setTitle(killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_claymore);
						resetBossBar();
						killer.sendMessage(Strings.kill_points_plus + this.claymorekill);
						createHologram(new Holograms(victim.getEyeLocation(), "§c+§6" + this.claymorekill + Strings.kill_holo_points));
					
					} else if(killreason.contains("c4")) {
						
						event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_c4);
						Library.bar.setTitle(killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_c4);
						resetBossBar();
						killer.sendMessage(Strings.kill_points_plus + this.c4kill);
						createHologram(new Holograms(victim.getEyeLocation(), "§c+§6" + this.c4kill + Strings.kill_holo_points));
						
					} else if(killreason.contains("knife")) {
						
						event.setDeathMessage(Main.pre + killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_knife);
						Library.bar.setTitle(killername + Strings.kill_killed + victimname + Strings.kill_with + Strings.kill_with_knife);
						resetBossBar();
						killer.sendMessage(Strings.kill_points_plus + this.knifekill);
						victim.sendMessage(Strings.kill_points_negative + this.knifedeath);
						givePlayerPoints(victim, "knife_death");
						createHologram(new Holograms(victim.getEyeLocation(), "§c+§6" + this.knifekill + Strings.kill_holo_points));
					
					//[Check] what killreason is. - Closed
					} else event.setDeathMessage(Main.pre + victim.getDisplayName() + Strings.kill_unknown_killer); 
				
				//[Check] if there is a killreason. - Closed
				} else event.setDeathMessage(Main.pre + victim.getDisplayName() + Strings.kill_unknown_killer); 
				
				//[Check] if killreason != null
				if(killreason != null) {
					givePlayerPoints(killer, killreason);
					
					//[Check] if killreason isn't dead
					if(killer != null && !(killer.isDead())){
						killer.playSound(killer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1000.0F, 1.0F);
						Title.sendActionBar(killer, Strings.kill_your_points + String.valueOf(Library.playerpoints.get(killer)));
					}
					
					//Killstreaker
					if(killstreak.get(killer) == null) killstreak.put(killer, 1);
					else killstreak.replace(killer, killstreak.get(killer), killstreak.get(killer) + 1);
								
					if(killstreak.get(killer) == 3 || killstreak.get(killer) == 5 || killstreak.get(killer) == 7) {
						givePlayerPoints(killer, "killstreak");
						if(Main.isMySQL) SQLCoins.addCoins(killer.getUniqueId().toString(), 10);
						Bukkit.broadcastMessage(Main.pre + killername + Strings.killstreak);
					}
					
					killer.sendMessage(Main.pre + Strings.kill_your_points + String.valueOf(Library.playerpoints.get(killer)));
					killer.setPlayerListName(killer.getDisplayName() + " §8- [§6" + Library.playerpoints.get(killer) + "§8]");
					
				//[Check] if killreason != null. - Closed
				}
			
			//[Check] if Killer is Victim - Closed
			} else {
				event.setDeathMessage(Main.pre + victim.getDisplayName() + Strings.kill_suicide);
				Library.bar.setTitle(victim.getDisplayName() + Strings.kill_suicide);
				resetBossBar();
				victim.sendMessage("§c§l" + this.suicide);
				givePlayerPoints(victim, "suicide");
				createHologram(new Holograms(victim.getEyeLocation(), "§c" + this.suicide + Strings.kill_holo_points));
			}
		
		//[Check] if Killer is a Player
		} else event.setDeathMessage(Main.pre + victim.getDisplayName() + Strings.kill_unknown_killer);
		
		victim.removeMetadata("killedWith", Main.getInstance());
		
		event.setDroppedExp(0);
		event.getDrops().clear();
		
		//Auto Respawn
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			public void run() {
				victim.spigot().respawn();
			}
		}, 30);
		
		//Killstreak
		if(killstreak.get(victim) == null) killstreak.put(victim, 0);
		else killstreak.replace(victim, killstreak.get(victim), 0);
		
		if(Library.playerpoints.get(victim) == null) victim.sendMessage(Main.pre + Strings.kill_your_points + "0");
		else victim.sendMessage(Main.pre + Strings.kill_your_points + String.valueOf(Library.playerpoints.get(victim)));
		
		victim.setPlayerListName(victim.getDisplayName() + " §8- [§6" + Library.playerpoints.get(victim) + "§8]");
	}
	

	private void givePlayerPoints(Player player, String killreason) {
		String playeruuid = player.getUniqueId().toString();
		int toBePoints = 0;
		
		if(killreason.contains("bow")) {
			toBePoints = this.bowkill;
			if(Main.isMySQL) SQLStats.addBowKills(playeruuid, 1);
			
		} else if(killreason.contains("combataxe")) {
			toBePoints = this.combataxekill;
			if(Main.isMySQL) SQLStats.addAxtKills(playeruuid, 1);
			
		} else if(killreason.contains("knife")) {
			toBePoints = this.knifekill;
			if(Main.isMySQL) SQLStats.addKnifeKills(playeruuid, 1);
			
		} else if(killreason.contains("suicide")) {
			toBePoints = this.suicide;
			if(Main.isMySQL) SQLStats.addSuicides(playeruuid, 1);
		}
		else if(killreason == "grenade") toBePoints = this.grenadekill;
		else if(killreason == "mine") toBePoints = this.minekill;
		else if(killreason == "claymore") toBePoints = this.claymorekill;
		else if(killreason == "c4") toBePoints = this.c4kill;
		else if(killreason == "knife_death") toBePoints = this.knifedeath;
		else if(killreason == "killstreak") toBePoints = this.killstreakpoints;
		else System.out.println(Strings.error_playerdeath_points);
		
		if(killreason != null && !killreason.contains("knife_death") && !killreason.contains("suicide") && killreason.contains("killstreak")) {
			if(Main.isMySQL) {
				SQLStats.addPoints(playeruuid, toBePoints);
				SQLStats.addKills(playeruuid, 1);
				SQLCoins.addCoins(playeruuid, 20);
				player.sendMessage(Strings.commands_coins_added_0 + 20 + Strings.commands_coins_added_1);
			}
		
		} else if(killreason == "killstreak") {
			if(Main.isMySQL) {
				SQLStats.addPoints(playeruuid, toBePoints);
				SQLCoins.addCoins(playeruuid, 30);
				player.sendMessage(Strings.commands_coins_added_0 + 30 + Strings.commands_coins_added_1);
			}
		}
		
		if (Library.playerpoints.get(player) == null) {
			Library.playerpoints.put(player, 0);
		} else {
			int points = Library.playerpoints.get(player);
			if (points + toBePoints < 0) points = 0;
			else if (points + toBePoints >= 0) points += toBePoints;
			else System.out.println(Strings.error_playerdeath_points);
			
			Library.playerpoints.put(player, points);
		}
	}
	
	private void createHologram(Holograms holo) {
		for(Player players : Bukkit.getOnlinePlayers()) {
			holo.display(players);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				@Override
				public void run() {
					holo.destroy(players);		
				}
			}, hololong);
		}
	}
	
	private void resetBossBar() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {			
				Library.bar.setTitle(Strings.bossbar);
			}
		}, 6*20);
	}
}