package at.dafnik.ragemode.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class Lobby {
	
	private String pre;
	private Main plugin;
	public Warmup wm;
	
	public Lobby(Main main){
		this.plugin = main;
		this.pre = Main.pre;
		this.wm = new Warmup(plugin);
	}
	
	public int lobbywplayerstime = 10;
	public int lobbywplayersid;
	
	public int lobbytime = 50;
	public int lobbyid;

	
	
	public void lobbywplayers(){
		Main.status = Status.PRE_LOBBY;
		lobbywplayersid = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			
			@Override
			public void run(){
				
				int needplayers = plugin.getConfig().getInt("ragemode.settings.neededplayers");
				
				if(lobbywplayerstime <=0){
					lobbywplayerstime = 10;
					plugin.getServer().getScheduler().cancelTask(lobbywplayersid);
					if(Bukkit.getOnlinePlayers().size() < needplayers){
						Bukkit.broadcastMessage(Strings.error_not_enough_player);
						Bukkit.broadcastMessage(Strings.error_needed_player + needplayers);
						lobbywplayers();
						return;
					}
					
					lobby();
				}
				
				lobbywplayerstime--;
			}
		}, 0L, 20L);
	}
	
	public void lobby(){
		lobbyid = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable(){
			
			@Override
			public void run(){
				
				Main.status = Status.LOBBY;
				
				int needplayers = plugin.getConfig().getInt("ragemode.settings.neededplayers");
				
				for(Player player : Bukkit.getOnlinePlayers()){
					player.setLevel(lobbytime);
				}
				
				if(lobbytime <= 0){
					lobbytime = 50;
					plugin.getServer().getScheduler().cancelTask(lobbyid);
					if (Bukkit.getOnlinePlayers().size() < needplayers) {
						Bukkit.broadcastMessage(Strings.error_not_enough_player);
						Bukkit.broadcastMessage(Strings.error_needed_player + needplayers);
						plugin.mapvote.Mapvotenull();
						lobbywplayers();
						return;
					}
					
					plugin.mapvote.getResult();
					
					if(plugin.villager != null) plugin.villager.remove();
					
					//Mapname
					Bukkit.broadcastMessage(pre + "§eMap§8: §6" + plugin.votedmap);
					//Mapauthor
					String mapauthor = plugin.getConfig().getString("ragemode.mapspawn." + plugin.votedmap + ".mapauthor");
					if(mapauthor == null) mapauthor = "No author";
					Bukkit.broadcastMessage(pre + "§eAuthor§8: §6" + mapauthor);
					
					wm.warmup();
				
				}else if(lobbytime == 50){
					Bukkit.broadcastMessage(pre + "§ePlayers online§8: §b" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());
					Bukkit.broadcastMessage(pre + "§3The round starts in §e" + lobbytime + " §3seconds");
					plugin.mapvote.getListBroadcast();
					
				}else if(lobbytime == 40){
					Bukkit.broadcastMessage(pre + "§3The round starts in §e" + lobbytime + " §3seconds");
					
				}else if(lobbytime == 30){
					Bukkit.broadcastMessage(pre + "§ePlayers online§8: §b" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());
					Bukkit.broadcastMessage(pre + "§3The round starts in §e" + lobbytime + " §3seconds");
					plugin.mapvote.getListBroadcast();
					
				}else if(lobbytime == 20){
					Bukkit.broadcastMessage(pre + "§3The round starts in §e" + lobbytime + " §3seconds");
					
				}else if(lobbytime == 10){
					Bukkit.broadcastMessage(pre + "§ePlayers online§8: §b" + Bukkit.getOnlinePlayers().size() + "§8/§b" + Bukkit.getMaxPlayers());		
					Bukkit.broadcastMessage(pre + "§3The round starts in §e" + lobbytime + " §3seconds");
					plugin.mapvote.getListBroadcast();
					
				}else if(lobbytime == 5){
					Bukkit.broadcastMessage(pre + "§3The round starts in §e" + lobbytime + " §3seconds");
					
				}else if(lobbytime == 4){
					Bukkit.broadcastMessage(pre + "§3The round starts in §e" + lobbytime + " §3seconds");
					
				}else if(lobbytime == 3){
					Bukkit.broadcastMessage(pre + "§3The round starts in §e" + lobbytime + " §3seconds");
					
				}else if(lobbytime == 2){
					Bukkit.broadcastMessage(pre + "§3The round starts in §e" + lobbytime + " §3seconds");
					
				}else if(lobbytime == 1){
					Bukkit.broadcastMessage(pre + "§3The round starts in §e" + lobbytime + " §3second");
				}
				
				lobbytime--;	
			}
		}, 0L, 20L);
	}
}
