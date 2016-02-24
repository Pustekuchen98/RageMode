package at.dafnik.ragemode.Commands;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;

public class LobbyCommands implements CommandExecutor{
	
	public Main plugin;
	
	public LobbyCommands(Main main){
		this.plugin = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		if(!(sender instanceof Player)){
			System.out.println(Strings.error_only_player_use);
		}else{
			Player player = (Player)sender;
			
			if(cmd.getName().equalsIgnoreCase("hub")) {
				doKick(player);
			}
		}	
		return true;
	}
	
	public void doKick(Player player) {
		if (Main.isBungee) {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
			try {
				out.writeUTF("Connect");
				out.writeUTF(plugin.getConfig().getString("ragemode.settings.bungee.lobbyserver"));
			} catch (IOException eee) {
				
			}
			player.sendPluginMessage(this.plugin, "BungeeCord", b.toByteArray());
		} else {
			player.sendMessage(Strings.error_not_on_a_bungee);
		}
	}
}