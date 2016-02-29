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
				if(args.length == 0) StatsCommand(player, player);			
				else if(args.length == 1){
					if(PlayedBefore(player, args[0])) StatsCommand(player, Bukkit.getOfflinePlayer(args[0]));
					
				} else player.sendMessage(Strings.error_enter);
				
			}
			
			if(cmd.getName().equalsIgnoreCase("statsreset")){	
					if(PlayedBefore(player, player.getName())){	
						SQLStats.setKills(player.getUniqueId().toString(), 0);
						SQLStats.setDeaths(player.getUniqueId().toString(), 0);
						SQLStats.setPlayedGames(player.getUniqueId().toString(), 0);
						SQLStats.setWonGames(player.getUniqueId().toString(), 0);
						SQLStats.setPoints(player.getUniqueId().toString(), 0);
						SQLStats.setBowKills(player.getUniqueId().toString(), 0);
						SQLStats.setKnifeKills(player.getUniqueId().toString(), 0);
						SQLStats.setAxtKills(player.getUniqueId().toString(), 0);
						SQLStats.setSuicides(player.getUniqueId().toString(), 0);
						SQLStats.addResets(player.getUniqueId().toString(),  1);			
						
						player.sendMessage(Strings.stats_reset);	
				}
			}
			
			if(cmd.getName().equalsIgnoreCase("statsadmin")){
				if (player.hasPermission("ragemode.admin")) {
					if (args.length == 0)
						StatsAdminCommands(player);
					else if (args.length == 4) {
						if(PlayedBefore(player, args[2])) {
							String lookedfor = Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString();
							int howmany = 0;
							try {
								howmany = Integer.valueOf(args[3]);
							} catch (ClassCastException e) {
								player.sendMessage(Strings.error_enter);
								return true;
							}
							
							if(args[0].equalsIgnoreCase("add")) {
								if(args[1].equalsIgnoreCase("kills")) SQLStats.addKills(lookedfor, howmany);
								else if(args[1].equalsIgnoreCase("deaths")) SQLStats.addDeaths(lookedfor, howmany);
								else if(args[1].equalsIgnoreCase("playedgames")) SQLStats.addPlayedGames(lookedfor, howmany);
								else if(args[1].equalsIgnoreCase("wongames")) SQLStats.addWonGames(lookedfor, howmany);
								else if(args[1].equalsIgnoreCase("points")) SQLStats.addPoints(lookedfor, howmany);
								else if(args[1].equalsIgnoreCase("resets")) SQLStats.addResets(lookedfor, howmany);
								else if(args[1].equalsIgnoreCase("bowkills")) SQLStats.addBowKills(lookedfor, howmany);
								else if(args[1].equalsIgnoreCase("knifekills")) SQLStats.addKnifeKills(lookedfor, howmany);
								else if(args[1].equalsIgnoreCase("axtkills")) SQLStats.addAxtKills(lookedfor, howmany);
								else if(args[1].equalsIgnoreCase("suicides")) SQLStats.addSuicides(lookedfor, howmany);
								else StatsAdminCommands(player);
								
							} else if(args[0].equalsIgnoreCase("remove")) {
								if(args[1].equalsIgnoreCase("kills")) SQLStats.removeKills(lookedfor, howmany);
								else if(args[1].equalsIgnoreCase("deaths")) SQLStats.removeDeaths(lookedfor, howmany);
								else if(args[1].equalsIgnoreCase("playedgames")) SQLStats.removePlayedGames(lookedfor, howmany);
								else if(args[1].equalsIgnoreCase("wongames")) SQLStats.removeWonGames(lookedfor, howmany);
								else if(args[1].equalsIgnoreCase("points")) SQLStats.removePoints(lookedfor, howmany);
								else if(args[1].equalsIgnoreCase("resets")) SQLStats.removeResets(lookedfor, howmany);
								else if(args[1].equalsIgnoreCase("bowkills")) SQLStats.removeBowKills(lookedfor, howmany);
								else if(args[1].equalsIgnoreCase("knifekills")) SQLStats.removeKnifeKills(lookedfor, howmany);
								else if(args[1].equalsIgnoreCase("axtkills")) SQLStats.removeAxtKills(lookedfor, howmany);
								else if(args[1].equalsIgnoreCase("suicides")) SQLStats.removeSuicides(lookedfor, howmany);
								else StatsAdminCommands(player);
								
							} else StatsAdminCommands(player);
						}
					} else StatsAdminCommands(player);
					
				} else player.sendMessage(Strings.error_permission);
			}
		} else System.out.println(Strings.error_only_player_use);
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public Boolean PlayedBefore(Player player, String playername){
		if(Main.isMySQL) {
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
		} else {
			player.sendMessage(Strings.error_not_mysql_enabled);
			return false;
		}
	}
	
	public void StatsAdminCommands(Player player){
		if(Main.isMySQL) player.sendMessage(Strings.stats_admin);
		else player.sendMessage(Strings.error_not_mysql_enabled);
	}
	
	public void StatsCommand(Player player, OfflinePlayer wantedPlayer) {
		if(Main.isMySQL) {
			int kills = SQLStats.getKills(wantedPlayer.getUniqueId().toString());
			int deaths = SQLStats.getDeaths(wantedPlayer.getUniqueId().toString());
			int playedgames = SQLStats.getPlayedGames(wantedPlayer.getUniqueId().toString());
			int wongames = SQLStats.getWonGames(wantedPlayer.getUniqueId().toString());
			int points = SQLStats.getPoints(wantedPlayer.getUniqueId().toString());
			int bowkills = SQLStats.getBowKills(wantedPlayer.getUniqueId().toString());
			int knifekills = SQLStats.getKnifeKills(wantedPlayer.getUniqueId().toString());
			int axtkills = SQLStats.getAxtKills(wantedPlayer.getUniqueId().toString());
			int suicides = SQLStats.getSuicides(wantedPlayer.getUniqueId().toString());
			int resets = SQLStats.getResets(wantedPlayer.getUniqueId().toString());
			
			float KD;
			try {
				KD = ((float) kills) / ((float) deaths);
			} catch (ArithmeticException ex) {
				KD = kills;
			}
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
