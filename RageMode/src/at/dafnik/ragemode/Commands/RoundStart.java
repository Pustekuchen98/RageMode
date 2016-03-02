package at.dafnik.ragemode.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Items.Items;
import at.dafnik.ragemode.Main.Main;

public class RoundStart implements CommandExecutor{
	
	private Main plugin;
	
	public RoundStart(Main main){
		this.plugin = main;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(sender instanceof Player){
			Player player = (Player)sender;
			
			 if (cmd.getName().equalsIgnoreCase("forcestart")){
				 	if(!(Main.getPower(player) >= 3)){
				 		player.sendMessage(Strings.error_permission);
				 	}else{
				 		plugin.lobbytasks.lobbytime = 10;
				 	}
				 }
				 	 
			if (cmd.getName().equalsIgnoreCase("latestart")) {
				if (!(Main.getPower(player) >= 3)) {
					player.sendMessage(Strings.error_permission);
				} else {
					plugin.lobbytasks.lobbytime = 50;
				}
			}
			
			if(cmd.getName().equalsIgnoreCase("test")) {
				if (!player.hasPermission("ragemode.admin")) {
					player.sendMessage(Strings.error_permission);
				} else {
					
					/**List<Location> locs = new ArrayList<Location>();
					locs.addAll(createCircle(player.getLocation(), 10));
					
					for(Location loc : locs) {
						loc.getWorld().strikeLightningEffect(loc);
					
						player.spawnParticle(Particle.DRAGON_BREATH, loc, 200);
						player.spawnParticle(Particle.FIREWORKS_SPARK, loc, 200);
					}**/
					
					//plugin.hasParticle.add(player);
	
					Items.givePlayerFly(player);
					
					player.sendMessage("r ");
				
				}
			}
		} else System.out.println(Strings.error_only_player_use);
		return true;
	}
	
	public List<Location> createCircle( Location middle, int radius ) {
		List<Location> locations = new ArrayList<Location>();
		for ( double i = 0.0; i < 360.0; i += 5 ) {
			double angle = i * Math.PI / 180;
			int x = (int)( middle.getX() + radius * Math.cos(angle) );
			int y = middle.getBlockY();
			int z = (int)( middle.getZ() + radius * Math.sin(angle) );
			locations.add( new Location( middle.getWorld(), x, y, z ) );
		}
		return locations;
	}
}
