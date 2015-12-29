package at.dafnik.ragemode.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.MySQL.SQLCoins;

public class Coins implements CommandExecutor{
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

		if(sender instanceof Player){
			Player player = (Player)sender;
			
			if (cmd.getName().equalsIgnoreCase("coins")){
				if(Main.isMySQL && Main.isShop) {
					if(args.length == 0){
						player.sendMessage(Strings.coins_your + SQLCoins.getCoins(player.getUniqueId().toString()) + Strings.coins_your_2);
					} else {
						player.sendMessage(Strings.error_enter);
					}
				} else {
					player.sendMessage(Strings.error_not_mysql_enabled);
				}
			}
			
			
			if(cmd.getName().equalsIgnoreCase("coinsadmin")){
				if(Main.isMySQL && Main.isShop) {
					if(player.hasPermission("ragemode.admin")) {
						if (args.length == 0) {
							CoinsAdminCommands(player);
							
						} else if (args.length == 3) {
							if (args[0].equals("add")) {
								if(PlayedBefore(player, args[1])){
									OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[1]);
									SQLCoins.addCoins(lookedfor.getUniqueId().toString(), Integer.valueOf(args[2]));
									player.sendMessage(Strings.statsadmin_succesfull);
								}
							} else if (args[0].equals("remove")){
								if(PlayedBefore(player, args[1])){
									OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[1]);
									SQLCoins.removeCoins(lookedfor.getUniqueId().toString(), Integer.valueOf(args[2]));
									player.sendMessage(Strings.statsadmin_succesfull);
								}
							} else {
								CoinsAdminCommands(player);
							}
						} else {
							CoinsAdminCommands(player);
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
	
	public void CoinsAdminCommands(Player player){
		if(Main.isMySQL) {
			player.sendMessage(Main.pre + "/coinsadmin §8<§aadd §8| §aremove§8> <§aplayername§8> <§anumber§8>");
		} else {
			player.sendMessage(Strings.error_not_mysql_enabled);
		}
	}
}