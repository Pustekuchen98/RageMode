package at.dafnik.ragemode.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
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
import at.dafnik.ragemode.Threads.PowerUpperThread;

public class Warmup {
	
	public Ingame ig;
	public PowerUpperThread pu;
	
	public CompassThread ct;
	public KnifeThread kt;
	
	public Warmup(){
		this.ig = new Ingame();
	}
	
	public long warmuptime = 10;
	public int warmupid;
	
	public static Team team;
	
	public void warmup(){
		Main.status = Status.WARMUP;
		warmupid = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable(){
			
			public void run(){
				
				if(warmuptime == 10){			
					kt = new KnifeThread();
					kt.start();
					ct = new CompassThread(Main.getInstance());
					ct.start();
					pu = new PowerUpperThread();
					pu.start();
					
					ScoreboardManager manager = Bukkit.getScoreboardManager();
					Scoreboard board = manager.getNewScoreboard();
					Warmup.team = board.registerNewTeam("playeringame");
					Warmup.team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
					
					String mapauthor = Main.getInstance().getConfig().getString("ragemode.mapspawn." +  Library.votedmap + ".mapauthor");
					if(mapauthor == null) mapauthor = "No author";
					
					Bukkit.broadcastMessage(Strings.tasks_warmup_teleport_to_map);
					
					for (Player players : Bukkit.getOnlinePlayers()) {
						if(Main.isMySQL) SQLStats.addPlayedGames(players.getUniqueId().toString(), 1);
						
						Warmup.team.addEntry(players.getName());
						players.setScoreboard(board);
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
						
						//Player Teleport
						players.teleport(TeleportAPI.getRandomMapSpawnLocations());
						
						Title.sendActionBar(players, Strings.tasks_warmup_voted_map +  Library.votedmap + Strings.tasks_warmup_voted_map_1 + mapauthor);
					}
					
					Bukkit.broadcastMessage(Strings.tasks_warmup_peacetime_ends_in + warmuptime + Strings.tasks_warmup_peacetime_ends_in_0);
					
				}else if(warmuptime == 0){
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
