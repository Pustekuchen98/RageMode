package at.dafnik.ragemode.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.MySQL.SQLCoins;
import at.dafnik.ragemode.MySQL.SQLStats;
import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.API.TeleportAPI;
import at.dafnik.ragemode.API.Title;
import at.dafnik.ragemode.Commands.LobbyCommands;

public class Ingame {
	
	private Main plugin;
	private LobbyCommands LobbyCommands;
	public Player playerwinner;
	
	public Ingame(Main main){
		this.plugin = main;
		this.LobbyCommands = new LobbyCommands(plugin);
	}
	
	public int ingameid;
	
	public int wintime = 10;
	public int winid;
	
	private int fadein = 5;
	private int fadeout = 5;
	private int stay = 60;
	
	public void ingame(){
		ingameid = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			
			public int ingametime = plugin.getConfig().getInt("ragemode.settings.gametime");
			
			public void run(){
				
				for(Player player : Bukkit.getOnlinePlayers()){
					player.setLevel(ingametime);
				}
				
				if(ingametime == 10){
					Bukkit.broadcastMessage(Strings.tasks_ingame_countdown_1 + ingametime + Strings.tasks_ingame_countdown_2);
					
				}else if(ingametime == 5){
					Bukkit.broadcastMessage(Strings.tasks_ingame_countdown_1 + ingametime + Strings.tasks_ingame_countdown_2);
				
				}else if(ingametime == 4){
					Bukkit.broadcastMessage(Strings.tasks_ingame_countdown_1 + ingametime + Strings.tasks_ingame_countdown_2);
						
				}else if(ingametime == 3){
					Bukkit.broadcastMessage(Strings.tasks_ingame_countdown_1 + ingametime + Strings.tasks_ingame_countdown_2);
				
				}else if(ingametime == 2){
					Bukkit.broadcastMessage(Strings.tasks_ingame_countdown_1 + ingametime + Strings.tasks_ingame_countdown_2);
					
				}else if(ingametime == 1){
					Bukkit.broadcastMessage(Strings.tasks_ingame_countdown_1 + ingametime + Strings.tasks_ingame_countdown_21);
				
				}else if(ingametime == 0){
					
					win();
					
					plugin.getServer().getScheduler().cancelTask(ingameid);
				}
				
				ingametime--;
			}
		}, 0L, 20L);
	}
	
	public void win() {
		Main.status = Status.WIN;
		winid = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				RocketClean();
				
				if(wintime == 10) {	
					getPlayerWinner();
					
					if(playerwinner == null){
						Bukkit.broadcastMessage(Strings.error_no_winner);
					} else {
						if(Main.isMySQL) {
							SQLStats.addWonGames(playerwinner.getUniqueId().toString(), 1);
							SQLStats.addPoints(playerwinner.getUniqueId().toString(), 300);
							SQLCoins.addCoins(playerwinner.getUniqueId().toString(), 100);
						}
						
						String winnername = playerwinner.getDisplayName();
						Bukkit.broadcastMessage(Strings.ragemode_winner + winnername);
						
						for(Player player : Bukkit.getOnlinePlayers()) {
							if(Main.isMySQL) SQLStats.addPlayedGames(player.getUniqueId().toString(), 1);
							//Title Send
							Title.sendTitle(player, fadein, stay, fadeout, winnername);
							Title.sendSubtitle(player, fadein, stay, fadeout, "§3is the winner");
						}
					}
					Bukkit.broadcastMessage(Strings.tasks_restart_countdown_0 + wintime + Strings.tasks_restart_countdown_01);
					
				} else if (wintime == 5) {
					Bukkit.broadcastMessage(Strings.tasks_restart_countdown_1 + wintime + Strings.tasks_restart_countdown_2);
				} else if (wintime == 4) {
					Bukkit.broadcastMessage(Strings.tasks_restart_countdown_1 + wintime + Strings.tasks_restart_countdown_2);
				} else if (wintime == 3) {
					Bukkit.broadcastMessage(Strings.tasks_restart_countdown_1 + wintime + Strings.tasks_restart_countdown_2);
				} else if (wintime == 2) {
					Bukkit.broadcastMessage(Strings.tasks_restart_countdown_1 + wintime + Strings.tasks_restart_countdown_2);
				} else if (wintime == 1) {
					Bukkit.broadcastMessage(Strings.tasks_restart_countdown_1 + wintime + Strings.tasks_restart_countdown_21);
				} else if (wintime == 0) {
					plugin.getServer().broadcastMessage(Strings.tasks_restart_now);
					plugin.getServer().getScheduler().cancelTask(winid);
					kickPlayer();
					
					restart();
				}
				wintime--;
				RocketClean();
			}
		}, 0L, 20L);
	}

	public void restart() {
		kickPlayer();
		Main.status = Status.RESTART;	
		Bukkit.shutdown();				
	}
	
	public void kickPlayer() {
		
		for(Player player : Bukkit.getOnlinePlayers()){
			
			if(Main.isBungee) {
				player.sendMessage(Strings.ragemode_server_is_back);
				LobbyCommands.doKick(player);
			} else {			
				int yourpoints;
				
				if(plugin.spectatorlist.contains(player)) {
					yourpoints = -10;
				} else {
					yourpoints = plugin.playerpoints.get(player);
				}
				
				if(playerwinner == null){
					if(yourpoints == -10) {
						player.kickPlayer(Main.pre + 
								"\n§3Thanks for watching §4RageMode§8!"
								+ "\n§3The winner of this round is§8: §r" + "§cNobody"
								+ "\n§eThe server is back in few seconds");
					} else {
						player.kickPlayer(Main.pre + 
								"\n§3Thanks for playing §4RageMode§8!"
								+ "\n§3The winner of this round is§8: §r" + "§cNobody"
								+ "\n" + Strings.kill_your_points + yourpoints 
								+ "\n§eThe server is back in few seconds");
					}
				}else if(playerwinner == player){	
					player.kickPlayer(Main.pre + 
							"\n§3Thanks for playing §4RageMode§8!"
							+ "\n§6You are the winner§8!"
							+ "\n" + Strings.kill_your_points + yourpoints
							+ "\n§eThe server is back in few seconds");
				 } else {
					 int winnerpoints = 0;
					 if(plugin.playerpoints.get(playerwinner) == null) winnerpoints = 0;			 
					 else winnerpoints = plugin.playerpoints.get(playerwinner);
					
					 if(yourpoints == -10) {
						 player.kickPlayer(Main.pre + 
								   "\n§3Thanks for watching §4RageMode§8!"
								 + "\n§3The winner of this round is§8: §r" + playerwinner.getDisplayName() + " §3with§8: §e" + winnerpoints + " §3points"
								 + "\n§eThe server is back in few seconds");
					} else {
						player.kickPlayer(Main.pre + 
								  "\n§3Thanks for playing §4RageMode§8!"
								+ "\n§3The winner of this round is§8: §r" + playerwinner.getDisplayName() + " §3with§8: §e" + winnerpoints + " §3points"
								+ "\n" + Strings.kill_your_points + yourpoints
								+ "\n§3eThe server is back in few seconds");
					}	
				}
			}
		}
	}
	
	public void RocketClean() {
		for(Entity entities : plugin.powerupentity) entities.remove();
		
		Location loc = new TeleportAPI(plugin).getRandomMapSpawnLocations();
		Firework firework = loc.getWorld().spawn(loc, Firework.class);
		FireworkMeta data = (FireworkMeta) firework.getFireworkMeta();
		data.addEffect(FireworkEffect.builder().withColor(Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.MAROON, Color.NAVY, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.TEAL, Color.WHITE, Color.YELLOW).with(Type.BALL).with(Type.BALL_LARGE).with(Type.BURST).with(Type.CREEPER).with(Type.STAR).withFade(Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.MAROON, Color.NAVY, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.TEAL, Color.WHITE, Color.YELLOW).build());
		data.setPower(4);
		firework.setFireworkMeta(data);	
	}
	
	
	public void getPlayerWinner() {
		int max = 0;
		for(int i : plugin.playerpoints.values()){
			if(i > max){
				max = i;
			}
		}
		
		for(Player all : plugin.playerpoints.keySet()){
			if(plugin.playerpoints.get(all) == max){
				playerwinner = all;
			}
		}
	}
}
