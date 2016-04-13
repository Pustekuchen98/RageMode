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
import org.bukkit.potion.PotionEffectType;

import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.MySQL.SQLCoins;
import at.dafnik.ragemode.MySQL.SQLStats;
import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.API.TeleportAPI;
import at.dafnik.ragemode.API.Title;
import at.dafnik.ragemode.Commands.LobbyCommands;

public class Ingame {
	
	private LobbyCommands LobbyCommands;
	public Player playerwinner;
	
	public Ingame(){
		this.LobbyCommands = new LobbyCommands();
	}
	
	public int ingameid;
	
	public int wintime = 10;
	public int winid;
	
	private int fadein = 5;
	private int fadeout = 5;
	private int stay = 60;
	
	public void ingame(){
		ingameid = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable(){
			
			public int ingametime = Main.getInstance().getConfig().getInt("ragemode.settings.gametime");
			
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
					
					Main.getInstance().getServer().getScheduler().cancelTask(ingameid);
				}
				
				ingametime--;
			}
		}, 0L, 20L);
	}
	
	public void win() {
		Main.status = Status.WIN;
		winid = Main.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				
				if(wintime > 3) RocketClean();
				
				if(wintime == 10) {	
					getPlayerWinner();
					
					if(playerwinner == null){
						Bukkit.broadcastMessage(Strings.error_no_winner);
					} else {
						if(Main.isMySQL) {
							SQLStats.addWonGames(playerwinner.getUniqueId().toString(), 1);
							SQLStats.addPoints(playerwinner.getUniqueId().toString(), 300);
							SQLCoins.addCoins(playerwinner.getUniqueId().toString(), 200);
						}
						
						String winnername = playerwinner.getDisplayName();
						Bukkit.broadcastMessage(Strings.ragemode_winner + winnername);
						
						for(Player players : Bukkit.getOnlinePlayers()) {
							if(Main.isMySQL) SQLCoins.addCoins(players.getUniqueId().toString(), 10);
							//Title Send
							Title.sendTitle(players, fadein, stay, fadeout, winnername);
							Title.sendSubtitle(players, fadein, stay, fadeout, Strings.tasks_win_is_the_winner);
							
							players.removePotionEffect(PotionEffectType.REGENERATION);
							players.removePotionEffect(PotionEffectType.SPEED);
							players.removePotionEffect(PotionEffectType.JUMP);
							players.removePotionEffect(PotionEffectType.BLINDNESS);
							players.removePotionEffect(PotionEffectType.SLOW);
							players.removePotionEffect(PotionEffectType.INVISIBILITY);
							players.removePotionEffect(PotionEffectType.LEVITATION);
							players.getInventory().setChestplate(null);
							players.getInventory().setHelmet(null);
							
							Library.powerup_doublejump.remove(players);
							Library.powerup_speedeffect.remove(players);
							Library.powerup_flyparticle.remove(players);
							
							Library.ingame.removeEntry(players.getName());
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
					Main.getInstance().getServer().broadcastMessage(Strings.tasks_restart_now);
					Main.getInstance().getServer().getScheduler().cancelTask(winid);
					kickPlayer();
					
					restart();
				}
				wintime--;
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
				
				if(Library.spectatorlist.contains(player)) {
					yourpoints = -10;
				} else {
					yourpoints = Library.playerpoints.get(player);
				}
				
				if(playerwinner == null){
					if(yourpoints == -10) {
						player.kickPlayer(Strings.tasks_ingame_kick_first_spectator
								+ Strings.tasks_ingame_kick_the_winner + Strings.tasks_ingame_kick_winner_nobody
								+ Strings.tasks_ingame_kick_back);
					} else {
						player.kickPlayer(Strings.tasks_ingame_kick_first_playing
								+ Strings.tasks_ingame_kick_the_winner + Strings.tasks_ingame_kick_winner_nobody
								+ "\n" + Strings.kill_your_points + yourpoints 
								+ Strings.tasks_ingame_kick_back);
					}
				}else if(playerwinner == player){	
					player.kickPlayer(Strings.tasks_ingame_kick_first_playing
							+ Strings.tasks_ingame_kick_you_winner
							+ "\n" + Strings.kill_your_points + yourpoints
							+ Strings.tasks_ingame_kick_back);
				 } else {
					 int winnerpoints = 0;
					 if(Library.playerpoints.get(playerwinner) == null) winnerpoints = 0;			 
					 else winnerpoints = Library.playerpoints.get(playerwinner);
					
					 if(yourpoints == -10) {
						 player.kickPlayer(Strings.tasks_ingame_kick_first_spectator
								 + Strings.tasks_ingame_kick_the_winner + playerwinner.getDisplayName() + Strings.tasks_ingame_kick_with + winnerpoints + Strings.tasks_ingame_kick_points
								 + Strings.tasks_ingame_kick_back);
					} else {
						player.kickPlayer(Strings.tasks_ingame_kick_first_playing
								+ Strings.tasks_ingame_kick_the_winner + playerwinner.getDisplayName() + Strings.tasks_ingame_kick_with + winnerpoints + Strings.tasks_ingame_kick_points
								+ "\n" + Strings.kill_your_points + yourpoints
								+ Strings.tasks_ingame_kick_back);
					}	
				}
			}
		}
	}
	
	public void RocketClean() {
		for(Entity entities : Library.powerup_entity) entities.remove();
		for(int i = 0; i < Library.powerup_hashmap.size(); i++) {
			for(Player players : Bukkit.getOnlinePlayers()) if(Library.powerup_hashmap.get(i) != null)Library.powerup_hashmap.get(i).destroy(players);
		}
		
		for(int i = 0; i < 10; i++) {
			Location loc = TeleportAPI.getRandomMapSpawnLocations();
			Firework firework = loc.getWorld().spawn(loc, Firework.class);
			FireworkMeta data = (FireworkMeta) firework.getFireworkMeta();
			data.addEffect(FireworkEffect.builder().withColor(Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.MAROON, Color.NAVY, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.TEAL, Color.WHITE, Color.YELLOW).with(Type.BALL).with(Type.BALL_LARGE).with(Type.BURST).with(Type.CREEPER).with(Type.STAR).withFade(Color.AQUA, Color.BLACK, Color.BLUE, Color.FUCHSIA, Color.GRAY, Color.GREEN, Color.LIME, Color.MAROON, Color.NAVY, Color.NAVY, Color.OLIVE, Color.ORANGE, Color.PURPLE, Color.RED, Color.SILVER, Color.TEAL, Color.WHITE, Color.YELLOW).build());
			data.setPower(7);
			firework.setFireworkMeta(data);	
		}
	}
	
	
	public void getPlayerWinner() {
		int max = 0;
		for(int i : Library.playerpoints.values()){
			if(i > max){
				max = i;
			}
		}
		
		for(Player all : Library.playerpoints.keySet()){
			if(Library.playerpoints.get(all) == max){
				playerwinner = all;
			}
		}
	}
}
