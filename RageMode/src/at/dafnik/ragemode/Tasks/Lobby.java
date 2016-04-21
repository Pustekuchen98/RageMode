package at.dafnik.ragemode.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.API.Title;
import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.Shop.VillagerShop;

public class Lobby {
	
	public Warmup wm;
	
	public Lobby(){
		this.wm = new Warmup();
	}
	
	public int lobbywplayerstime = 10;
	public int lobbywplayersid;
	
	public int lobbytime = 50;
	public int lobbyid;

	
	
	public void lobbywplayers(){
		Main.status = Status.PRE_LOBBY;
		lobbywplayersid = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable(){
			
			@Override
			public void run(){	
				int needplayers = Main.getInstance().getConfig().getInt("ragemode.settings.neededplayers");
				
				if(lobbywplayerstime <=0){
					lobbywplayerstime = 10;
					Main.getInstance().getServer().getScheduler().cancelTask(lobbywplayersid);
					if(Bukkit.getOnlinePlayers().size() < needplayers){
						Bukkit.broadcastMessage(Strings.error_not_enough_player);
						Bukkit.broadcastMessage(Strings.error_needed_player + needplayers);
						lobbywplayers();
						return;
					}
					
					lobby();
				}
				
				if(Main.isShop && Main.isMySQL) {
					if(Bukkit.getOnlinePlayers().size() > 0 && Library.villager == null) {
						Library.villager = new VillagerShop();	
						Library.villager.start();
						for(Player players : Bukkit.getOnlinePlayers()) Library.villagerholo.display(players);
					}
				}
				
				lobbywplayerstime--;
			}
		}, 0L, 20L);
	}
	
	public void lobby(){
		lobbyid = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable(){
			
			@Override
			public void run(){
				
				Main.status = Status.LOBBY;
				
				int needplayers = Main.getInstance().getConfig().getInt("ragemode.settings.neededplayers");
				
				for(Player player : Bukkit.getOnlinePlayers()){
					player.setLevel(lobbytime);
				}
				
				if(lobbytime <= 0){
					lobbytime = 50;
					Main.getInstance().getServer().getScheduler().cancelTask(lobbyid);
					if (Bukkit.getOnlinePlayers().size() < needplayers) {
						Bukkit.broadcastMessage(Strings.error_not_enough_player);
						Bukkit.broadcastMessage(Strings.error_needed_player + needplayers);
						Main.getInstance().mapvote.Mapvotenull();
						lobbywplayers();
						return;
					}
					
					Main.getInstance().mapvote.getResult();
					
					if(Library.villager != null) {
						Library.villager.stop();
						Library.villager = null;
					}
					
					//Mapname
					Bukkit.broadcastMessage(Strings.tasks_lobby_map_voted + Library.votedmap);
					//Mapauthor
					String mapauthor = Main.getInstance().getConfig().getString("ragemode.mapspawn." + Library.votedmap + ".mapauthor");
					if(mapauthor == null) mapauthor = Strings.tasks_lobby_no_author;
					Bukkit.broadcastMessage(Strings.tasks_lobby_author + mapauthor);
					
					wm.warmup();
				
				}else if(lobbytime == 50){
					Bukkit.broadcastMessage(Strings.tasks_lobby_player_online);
					Bukkit.broadcastMessage(Strings.tasks_lobby_countdown_first + lobbytime + Strings.tasks_lobby_countdown_two);
					doTitle();
					Main.getInstance().mapvote.getListBroadcast();
					
				}else if(lobbytime == 40){
					Bukkit.broadcastMessage(Strings.tasks_lobby_countdown_first + lobbytime + Strings.tasks_lobby_countdown_two);
					
				}else if(lobbytime == 30){
					Bukkit.broadcastMessage(Strings.tasks_lobby_player_online);
					Bukkit.broadcastMessage(Strings.tasks_lobby_countdown_first + lobbytime + Strings.tasks_lobby_countdown_two);
					doTitle();
					Main.getInstance().mapvote.getListBroadcast();
					
				}else if(lobbytime == 20){
					Bukkit.broadcastMessage(Strings.tasks_lobby_countdown_first + lobbytime + Strings.tasks_lobby_countdown_two);
					
				}else if(lobbytime == 10){
					Bukkit.broadcastMessage(Strings.tasks_lobby_player_online);
					Bukkit.broadcastMessage(Strings.tasks_lobby_countdown_first + lobbytime + Strings.tasks_lobby_countdown_two);
					doTitle();
					Main.getInstance().mapvote.getListBroadcast();
					
				}else if(lobbytime == 5){
					Bukkit.broadcastMessage(Strings.tasks_lobby_countdown_first + lobbytime + Strings.tasks_lobby_countdown_two);
					
				}else if(lobbytime == 4){
					Bukkit.broadcastMessage(Strings.tasks_lobby_countdown_first + lobbytime + Strings.tasks_lobby_countdown_two);
					
				}else if(lobbytime == 3){
					Bukkit.broadcastMessage(Strings.tasks_lobby_countdown_first + lobbytime + Strings.tasks_lobby_countdown_two);
					
				}else if(lobbytime == 2){
					Bukkit.broadcastMessage(Strings.tasks_lobby_countdown_first + lobbytime + Strings.tasks_lobby_countdown_two);
					
				}else if(lobbytime == 1){
					Bukkit.broadcastMessage(Strings.tasks_lobby_countdown_first + lobbytime + Strings.tasks_lobby_countdown_two_0);
				}
				
				lobbytime--;	
			}
		}, 0L, 20L);
	}
	
	private void doTitle() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			Title.sendTitle(player, Library.fadein, Library.stay, Library.fadeout, "§cRageMode");
		}
	}
}
