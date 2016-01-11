package at.dafnik.ragemode.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.MySQL.SQLStats;

public class Stats implements CommandExecutor {
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

		if(sender instanceof Player){
			Player player = (Player)sender;
			
			if (cmd.getName().equalsIgnoreCase("stats")){
				if(args.length == 0){
					StatsCommand(player, player);			
				}else if(args.length == 1){
					OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
	                if (!offlinePlayer.hasPlayedBefore()) {
	                    player.sendMessage(Strings.error_player_not_played_before);
	                }else{
	                	StatsCommand(player, offlinePlayer);
	                }
				} else {
					player.sendMessage(Strings.error_enter);
				}
			}
			
			if(cmd.getName().equalsIgnoreCase("statsreset")){
				if(Main.isMySQL) {
					if(PlayedBefore(player, player.getName())){
						int kills = SQLStats.getKills(player.getUniqueId().toString());
						int deaths = SQLStats.getDeaths(player.getUniqueId().toString());
						int playedgames = SQLStats.getPlayedGames(player.getUniqueId().toString());
						int wongames = SQLStats.getWonGames(player.getUniqueId().toString());
						int points = SQLStats.getPoints(player.getUniqueId().toString());
						int bowkills = SQLStats.getBowKills(player.getUniqueId().toString());
						int knifekills = SQLStats.getKnifeKills(player.getUniqueId().toString());
						int axtkills = SQLStats.getAxtKills(player.getUniqueId().toString());
						int suicides = SQLStats.getSuicides(player.getUniqueId().toString());
						
						SQLStats.removeKills(player.getUniqueId().toString(), kills);
						SQLStats.removeDeaths(player.getUniqueId().toString(), deaths);
						SQLStats.removePlayedGames(player.getUniqueId().toString(), playedgames);
						SQLStats.removeWonGames(player.getUniqueId().toString(), wongames);
						SQLStats.removePoints(player.getUniqueId().toString(), points);
						SQLStats.removeBowKills(player.getUniqueId().toString(), bowkills);
						SQLStats.removeKnifeKills(player.getUniqueId().toString(), knifekills);
						SQLStats.removeAxtKills(player.getUniqueId().toString(), axtkills);
						SQLStats.addResets(player.getUniqueId().toString(),  1);
						SQLStats.removeSuicides(player.getUniqueId().toString(), suicides);
						
						player.sendMessage(Strings.stats_reset);
					}
				} else {
					player.sendMessage(Strings.error_not_mysql_enabled);
				}
			}
			
			if(cmd.getName().equalsIgnoreCase("statsadmin")){
				if(Main.isMySQL) {
					if(player.hasPermission("ragemode.admin")) {
						if (args.length == 0) {
							StatsAdminCommands(player);
	
						} else if (args.length == 4) {
							if(args[0].equalsIgnoreCase("add")) {
								if(args[1].equalsIgnoreCase("kills")) {
									if(PlayedBefore(player, args[2])){
										OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[2]);
										SQLStats.addKills(lookedfor.getUniqueId().toString(), Integer.valueOf(args[3]));
										player.sendMessage(Strings.statsadmin_succesfull);
									}
								} else if(args[1].equalsIgnoreCase("deaths")) {
									if(PlayedBefore(player, args[2])) {
										OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[2]);
										SQLStats.addDeaths(lookedfor.getUniqueId().toString(), Integer.valueOf(args[3]));
										player.sendMessage(Strings.statsadmin_succesfull);
									}
								} else if(args[1].equalsIgnoreCase("playedgames")) {
									if(PlayedBefore(player, args[2])) {
										OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[2]);
										SQLStats.addPlayedGames(lookedfor.getUniqueId().toString(), Integer.valueOf(args[3]));
										player.sendMessage(Strings.statsadmin_succesfull);
									}
								} else if(args[1].equalsIgnoreCase("wongames")) {
									if(PlayedBefore(player, args[2])) {
										OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[2]);
										SQLStats.addWonGames(lookedfor.getUniqueId().toString(), Integer.valueOf(args[3]));
										player.sendMessage(Strings.statsadmin_succesfull);
									}
								} else if(args[1].equalsIgnoreCase("points")) {
									if(PlayedBefore(player, args[2])) {
										OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[2]);
										SQLStats.addPoints(lookedfor.getUniqueId().toString(), Integer.valueOf(args[3]));
										player.sendMessage(Strings.statsadmin_succesfull);
									}
								} else if(args[1].equalsIgnoreCase("resets")) {
									if(PlayedBefore(player, args[2])) {
										OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[2]);
										SQLStats.addResets(lookedfor.getUniqueId().toString(), Integer.valueOf(args[3]));
										player.sendMessage(Strings.statsadmin_succesfull);
									}
								} else if(args[1].equalsIgnoreCase("bowkills")) {
									if(PlayedBefore(player, args[2])) {
										OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[2]);
										SQLStats.addBowKills(lookedfor.getUniqueId().toString(), Integer.valueOf(args[3]));
										player.sendMessage(Strings.statsadmin_succesfull);
									}		
								} else if(args[1].equalsIgnoreCase("knifekills")) {
									if(PlayedBefore(player, args[2])) {
										OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[2]);
										SQLStats.addKnifeKills(lookedfor.getUniqueId().toString(), Integer.valueOf(args[3]));
										player.sendMessage(Strings.statsadmin_succesfull);
									}		
								} else if(args[1].equalsIgnoreCase("axtkills")) {
									if(PlayedBefore(player, args[2])) {
										OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[2]);
										SQLStats.addAxtKills(lookedfor.getUniqueId().toString(), Integer.valueOf(args[3]));
										player.sendMessage(Strings.statsadmin_succesfull);
									}	
								} else if(args[1].equalsIgnoreCase("suicides")) {
									if(PlayedBefore(player, args[2])) {
										OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[2]);
										SQLStats.addSuicides(lookedfor.getUniqueId().toString(), Integer.valueOf(args[3]));
										player.sendMessage(Strings.statsadmin_succesfull);
									}
								} else {
									StatsAdminCommands(player);
								}
							} else if(args[0].equalsIgnoreCase("remove")) {
								if(args[1].equalsIgnoreCase("kills")) {
									if(PlayedBefore(player, args[2])) {
										OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[2]);
										SQLStats.removeKills(lookedfor.getUniqueId().toString(), Integer.valueOf(args[3]));
										player.sendMessage(Strings.statsadmin_succesfull);
									}
								} else if(args[1].equalsIgnoreCase("deaths")) {
									if(PlayedBefore(player, args[2])) {
										OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[2]);
										SQLStats.removeDeaths(lookedfor.getUniqueId().toString(), Integer.valueOf(args[3]));
										player.sendMessage(Strings.statsadmin_succesfull);
									}
								} else if(args[1].equalsIgnoreCase("playedgames")) {
									if(PlayedBefore(player, args[2])) {
										OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[2]);
										SQLStats.removePlayedGames(lookedfor.getUniqueId().toString(), Integer.valueOf(args[3]));
										player.sendMessage(Strings.statsadmin_succesfull);
									}
								} else if(args[1].equalsIgnoreCase("wongames")) {
									if(PlayedBefore(player, args[2])) {
										OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[2]);
										SQLStats.removeWonGames(lookedfor.getUniqueId().toString(), Integer.valueOf(args[3]));
										player.sendMessage(Strings.statsadmin_succesfull);
									}
								} else if(args[1].equalsIgnoreCase("points")) {
									if(PlayedBefore(player, args[2])) {
										OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[2]);
										SQLStats.removePoints(lookedfor.getUniqueId().toString(), Integer.valueOf(args[3]));
										player.sendMessage(Strings.statsadmin_succesfull);
									}
								} else if(args[1].equalsIgnoreCase("resets")) {
									if(PlayedBefore(player, args[2])) {
										OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[2]);
										SQLStats.removeResets(lookedfor.getUniqueId().toString(), Integer.valueOf(args[3]));
										player.sendMessage(Strings.statsadmin_succesfull);
									}	
								} else if(args[1].equalsIgnoreCase("bowkills")) {
									if(PlayedBefore(player, args[2])) {
										OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[2]);
										SQLStats.removeBowKills(lookedfor.getUniqueId().toString(), Integer.valueOf(args[3]));
										player.sendMessage(Strings.statsadmin_succesfull);
									}
								} else if(args[1].equalsIgnoreCase("knifekills")) {
									if(PlayedBefore(player, args[2])) {
										OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[2]);
										SQLStats.removeKnifeKills(lookedfor.getUniqueId().toString(), Integer.valueOf(args[3]));
										player.sendMessage(Strings.statsadmin_succesfull);
									}
								} else if(args[1].equalsIgnoreCase("axtkills")) {
									if(PlayedBefore(player, args[2])) {
										OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[2]);
										SQLStats.removeAxtKills(lookedfor.getUniqueId().toString(), Integer.valueOf(args[3]));
										player.sendMessage(Strings.statsadmin_succesfull);
									}
								} else if(args[1].equalsIgnoreCase("suicides")) {
									if(PlayedBefore(player, args[2])) {
										OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[2]);
										SQLStats.removeSuicides(lookedfor.getUniqueId().toString(), Integer.valueOf(args[3]));
										player.sendMessage(Strings.statsadmin_succesfull);
									}		
								} else {
									StatsAdminCommands(player);
								}
							} else {
								StatsAdminCommands(player);
							}
						} else {
							StatsAdminCommands(player);
						}			
					} else {
						player.sendMessage(Strings.error_permission);
					}
				} else {
					player.sendMessage(Strings.error_not_mysql_enabled);
				}
			}
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public Boolean PlayedBefore(Player player, String playername){
		OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playername);
        if (!offlinePlayer.hasPlayedBefore()) {
            player.sendMessage(Strings.error_no_uuid);
            return false;
        } else if (offlinePlayer.hasPlayedBefore()) {
        	return true;
        } else {
        	player.sendMessage(Strings.error_no_uuid);
        	return false;
        }
	}
	
	public void StatsAdminCommands(Player player){
		if(Main.isMySQL) {
			player.sendMessage(Strings.stats_admin);
		} else {
			player.sendMessage(Strings.error_not_mysql_enabled);
		}
	}
	
	public void StatsCommand(Player player, OfflinePlayer wantedPlayer) {
		if(Main.isMySQL) {
			int kills = SQLStats.getKills(wantedPlayer.getUniqueId().toString());
			int deaths = SQLStats.getDeaths(wantedPlayer.getUniqueId().toString());
			int playedgames = SQLStats.getPlayedGames(wantedPlayer.getUniqueId().toString());
			int wongames = SQLStats.getWonGames(wantedPlayer.getUniqueId().toString());
			int points = SQLStats.getPoints(wantedPlayer.getUniqueId().toString());
			int bowkills = SQLStats.getBowKills(player.getUniqueId().toString());
			int knifekills = SQLStats.getKnifeKills(player.getUniqueId().toString());
			int axtkills = SQLStats.getAxtKills(player.getUniqueId().toString());
			int suicides = SQLStats.getSuicides(player.getUniqueId().toString());
			int resets = SQLStats.getResets(wantedPlayer.getUniqueId().toString());
			
			float KD = ((float) kills) / ((float) deaths);
			float rund = (float)(((int)(KD*100))/100.0);
			
			player.sendMessage(Main.pre + "§e" + wantedPlayer.getName() + "§3's Stats§8:");
			player.sendMessage(Main.pre + "§3Points§8: §e" + points);
			player.sendMessage(Main.pre + "§3All Kills§8: §e" + kills);
			player.sendMessage(Main.pre + "§3Bow Kills§8: §e" + bowkills);
			player.sendMessage(Main.pre + "§3Knife Kills§8: §e" + knifekills);
			player.sendMessage(Main.pre + "§3Axt Kills§8: §e" + axtkills);
			player.sendMessage(Main.pre + "§3Deaths§8: §e" + deaths);
			player.sendMessage(Main.pre + "§3Kills/Deaths§8: §e" + rund);
			player.sendMessage(Main.pre + "§3Played game§8: §e" + playedgames);
			player.sendMessage(Main.pre + "§3Won games§8: §e" + wongames);
			player.sendMessage(Main.pre + "§3Suicides§8: §e" + suicides);
			player.sendMessage(Main.pre + "§3Stats resets§8: §e" + resets);
		
		} else {
			player.sendMessage(Strings.error_not_mysql_enabled);
		}
	}
}
