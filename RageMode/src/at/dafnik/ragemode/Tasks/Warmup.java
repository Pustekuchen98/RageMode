package at.dafnik.ragemode.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.API.TeleportAPI;
import at.dafnik.ragemode.API.Title;
import at.dafnik.ragemode.Items.Items;
import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.MySQL.SQLStats;
import at.dafnik.ragemode.Threads.CompassThread;
import at.dafnik.ragemode.Threads.KnifeThread;
import at.dafnik.ragemode.Threads.PowerUpThread;

public class Warmup {
	
	public Ingame ig;
	
	public PowerUpThread pu;
	public KnifeThread kt;
	public CompassThread ct;
	
	public Warmup(){
		this.ig = new Ingame();
	}
	
	public long warmuptime = 10;
	public int warmupid;
	
	
	public void warmup(){
		Main.status = Status.WARMUP;
		
		String mapauthor1 = Main.getInstance().getConfig().getString("ragemode.mapspawn." +  Library.votedmap + ".mapauthor");
		final String mapauthor;
		if(mapauthor1 == null) mapauthor = "No author";
		else mapauthor = mapauthor1;
		
		warmupid = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable(){
	
			public void run() {
				for(Player players : Bukkit.getOnlinePlayers()) Title.sendActionBar(players, Strings.tasks_warmup_voted_map +  Library.votedmap + Strings.tasks_warmup_voted_map_1 + mapauthor);
				
				if(warmuptime == 10){			
					kt = new KnifeThread();
					kt.start();
					
					ct = new CompassThread();
					ct.start();
					
					pu = new PowerUpThread();
					pu.start();
					
					Bukkit.broadcastMessage(Strings.tasks_warmup_teleport_to_map);
					
					for (Player players : Bukkit.getOnlinePlayers()) {
						if(Main.isMySQL) SQLStats.addPlayedGames(players.getUniqueId().toString(), 1);
						
						for(Team teams : Library.teams) teams.removeEntry(players.getName());
						
						Library.ingame.addEntry(players.getName());
						players.setScoreboard(Library.scoreboard);
						
						//Add Bossbar
						Library.bar.addPlayer(players);
						
						//Put in Ingameplayer
						Library.ingameplayer.add(players);
						
						//Add Player To PlayerPointslist
						Library.playerpoints.put(players, 0);
						
						players.setAllowFlight(false);
						players.setFlySpeed((float) 0.2);
						players.setLevel(0);
						players.setGameMode(GameMode.SURVIVAL);
						players.getInventory().clear();			
						players.setPlayerListName(players.getDisplayName() + " §8- [§6" + 0 + "§8]");
						
						//Player Teleport
						players.teleport(TeleportAPI.getRandomMapSpawnLocations());
					}
					
					Bukkit.broadcastMessage(Strings.tasks_warmup_peacetime_ends_in + warmuptime + Strings.tasks_warmup_peacetime_ends_in_0);
					
				} else if(warmuptime == 0){
					if(Main.status != Status.WIN && Main.status != Status.RESTART) {
						Bukkit.broadcastMessage(Strings.tasks_warmup_peacetime_ends);
						for(Player player : Bukkit.getOnlinePlayers()) {
							Title.sendFullTitle(player, Library.fadein, Library.stay, Library.fadeout, Strings.tasks_warmup_peactime_ends_now, Strings.tasks_warmup_peactime_ends_now_0);
							Items.givePlayerItems(player);
						}
						
						//Set Status on Ingame
						Main.status = Status.INGAME;
						
						//Start Ingame Time
						ig.ingame();
						
						Main.getInstance().getServer().getScheduler().cancelTask(warmupid);
					}
				}		
				warmuptime--;
			}
		}, 0L, 20L);
	}
}
