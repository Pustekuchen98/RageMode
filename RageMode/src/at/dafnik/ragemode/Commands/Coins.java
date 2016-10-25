package at.dafnik.ragemode.Commands;

import at.dafnik.ragemode.Items.Items;
import at.dafnik.ragemode.Main.Library;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.MySQL.SQLCoins;

public class Coins implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

		if(sender instanceof Player){
			Player player = (Player)sender;
			
			if (cmd.getName().equalsIgnoreCase("coins")){
				if(Main.isMySQL && Main.isShop) {
					if(args.length == 0) player.sendMessage(Strings.commands_coins_your + SQLCoins.getCoins(player.getUniqueId().toString()) + Strings.commands_coins_your_2);
					else player.sendMessage(Strings.commands_coins_your_usage);
					
				} else player.sendMessage(Strings.error_not_mysql_enabled);
			}
			
			if(cmd.getName().equalsIgnoreCase("coinsadmin")){
				if(Main.isShop && Main.isMySQL) {
					if(player.hasPermission(Strings.permissions_admin)) {
						if (args.length == 0) CoinsAdminCommands(player);	
						 else if (args.length == 3) {
							if(PlayedBefore(player, args[1])) {
								OfflinePlayer lookedfor = Bukkit.getOfflinePlayer(args[1]);
								if(args[0].equalsIgnoreCase("add")) {
									SQLCoins.addCoins(lookedfor.getUniqueId().toString(), Integer.valueOf(args[2]));
									player.sendMessage(Strings.commands_coinsadmin_successful_0 + args[1] + Strings.commands_coinsadmin_successful_1);
								} else if(args[0].equalsIgnoreCase("remove")) {
									SQLCoins.removeCoins(lookedfor.getUniqueId().toString(), Integer.valueOf(args[2]));
									player.sendMessage(Strings.commands_coinsadmin_successful_0 + args[1] + Strings.commands_coinsadmin_successful_1);
								} else CoinsAdminCommands(player);
							}
						} else CoinsAdminCommands(player);
									
					} else player.sendMessage(Strings.error_permission);	
				}
			}

			if(cmd.getName().equalsIgnoreCase("sparcles")) {
				if (Library.sparcleswitch.contains(player)) {
					Library.sparcleswitch.remove(player);
					player.sendMessage(Strings.commands_sparcle_off);
				} else {
					Library.sparcleswitch.add(player);
					player.sendMessage(Strings.commands_sparcle_on);
				}

				if (Main.status == Main.Status.LOBBY || Main.status == Main.Status.PRE_LOBBY) {
					player.getInventory().remove(Material.AIR);
					if (Library.sparcleswitch.contains(player)) {
						Items.givePlayerSparcleSwitcher(player, "§aON");
					} else {
						Items.givePlayerSparcleSwitcher(player, "§cOFF");
					}
				}
			}
		} else System.out.println(Strings.error_only_player_use);
		return true;
	}

	public boolean PlayedBefore(Player player, String playername){
		if(Main.isMySQL) {
			OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playername);
	        if (!offlinePlayer.hasPlayedBefore()) {
	            player.sendMessage(Strings.error_no_uuid);
	            return false;
	        } else {
	        	return true;
	        }
		} else {
			player.sendMessage(Strings.error_not_mysql_enabled);
			return false;
		}
	}
	
	public void CoinsAdminCommands(Player player){
		if(Main.isMySQL && Main.isShop) player.sendMessage(Strings.commands_coinsadmin_usage);
		else player.sendMessage(Strings.error_not_mysql_enabled);
	}
}