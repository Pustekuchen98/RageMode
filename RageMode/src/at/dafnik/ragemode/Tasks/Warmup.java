package at.dafnik.ragemode.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.API.TeleportAPI;
import at.dafnik.ragemode.API.Title;
import at.dafnik.ragemode.Items.CompassThread;
import at.dafnik.ragemode.Items.Items;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.PowerUPs.PowerUpper;
import at.dafnik.ragemode.Weapons.KnifeThread;

public class Warmup {
	
	private Main plugin;
	public Ingame ig;
	public PowerUpper pu;
	
	public CompassThread ct;
	public KnifeThread kt;
	
	public Warmup(Main main){
		this.plugin = main;
		this.ig = new Ingame(plugin);
	}
	
	public long warmuptime = 10;
	public int warmupid;
	
	private int fadein = 5;
	private int fadeout = 5;
	private int stay = 20;
	
	public void warmup(){
		Main.status = Status.WARMUP;
		warmupid = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			
			@SuppressWarnings("deprecation")
			public void run(){
				
				if(warmuptime == 10){
					
					kt = new KnifeThread(plugin);
					kt.start();
					ct = new CompassThread(plugin);
					ct.start();
					pu = new PowerUpper(plugin);
					pu.start();
					
					ScoreboardManager manager = Bukkit.getScoreboardManager();
					Scoreboard board = manager.getNewScoreboard();
					Team team = board.registerNewTeam("playeringame");
					team.setNameTagVisibility(NameTagVisibility.NEVER);
					
					String mapauthor = plugin.getConfig().getString("ragemode.mapspawn." +  plugin.votedmap + ".mapauthor");
					if(mapauthor == null) mapauthor = "No author";
					
					for (Player player : Bukkit.getOnlinePlayers()) {
						
						team.addPlayer(player);
						player.setScoreboard(board);
						plugin.bar.addPlayer(player);
						
						//Put in Ingameplayer
						plugin.ingameplayer.add(player);
						
						//Add Player To PlayerPointslist
						plugin.playerpoints.put(player, 0);
						
						player.setAllowFlight(false);
						player.setFlySpeed((float) 0.2);
						player.setLevel(0);
						player.setGameMode(GameMode.SURVIVAL);
						player.getInventory().clear();				
						
						//Player Teleport
						player.teleport(new TeleportAPI(plugin).getRandomMapSpawnLocations());
						
						Title.sendActionBar(player, "§3Choosen Map§8: §e" +  plugin.votedmap + " §8|| §3Author§8: §e" + mapauthor);
					}
					
					Bukkit.broadcastMessage(Main.pre + "§3The peace time ends in §e" + warmuptime + " §3seconds");
					
				}else if(warmuptime == 0){
					if(Main.status != Status.WIN && Main.status != Status.RESTART) {
						Bukkit.broadcastMessage(Strings.tasks_ingame_peacetime_ends);
						for(Player player : Bukkit.getOnlinePlayers()) {
							Title.sendFullTitle(player, fadein, stay, fadeout, "§eThe peace time ends", "§cnow");
							Items.givePlayerItems(player);
						}
						
						//Set Status on Ingame
						Main.status = Status.INGAME;
						
						//Start Ingame Time
						ig.ingame();
						
						plugin.getServer().getScheduler().cancelTask(warmupid);
					}
				}		
				warmuptime--;
			}
		}, 0L, 20L);
	}
}
