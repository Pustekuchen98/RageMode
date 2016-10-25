package at.dafnik.ragemode.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.PowerSystem;

public class RoundStart implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(sender instanceof Player){
			Player player = (Player)sender;
			
			 if (cmd.getName().equalsIgnoreCase("forcestart")){
				 	if(!(PowerSystem.getPower(player) >= 3)){
				 		player.sendMessage(Strings.error_permission);
				 	}else{
				 		Main.getInstance().lobbytasks.lobbytime = 10;
				 	}
				 }
				 	 
			if (cmd.getName().equalsIgnoreCase("latestart")) {
				if (!(PowerSystem.getPower(player) >= 3)) {
					player.sendMessage(Strings.error_permission);
				} else {
					Main.getInstance().lobbytasks.lobbytime = 50;
				}
			}
			
			if(cmd.getName().equalsIgnoreCase("test")) {
				if (!player.hasPermission("ragemode.admin")) {
					player.sendMessage(Strings.error_permission);
				} else {
					
					/*List<Location> locs = new ArrayList<Location>();
					locs.addAll(createCircle(player.getLocation(), 10));
					
					for(Location loc : locs) {
						loc.getWorld().strikeLightningEffect(loc);
					
						player.spawnParticle(Particle.DRAGON_BREATH, loc, 200);
						player.spawnParticle(Particle.FIREWORKS_SPARK, loc, 200);
					}
					
					
					Location loc = player.getLocation();
					Item item = loc.getWorld().dropItem(loc, new ItemStack(Material.FIREBALL));
					Vector vector = new Vector(0, 2.2, 0);
					item.setVelocity(vector);
					for(Player players : Bukkit.getOnlinePlayers()) players.playSound(loc, Sound.ENTITY_FIREWORK_LAUNCH, 1000, 1);
					//new OwnFireworkThread(player, item).start();*/
					
					player.sendMessage("Test was successful");
				}
			}
		} else System.out.println(Strings.error_only_player_use);
		return true;
	}
	
	/*private List<Location> createCircle( Location middle, int radius ) {
		List<Location> locations = new ArrayList<Location>();
		for ( double i = 0.0; i < 360.0; i += 5 ) {
			double angle = i * Math.PI / 180;
			int x = (int)( middle.getX() + radius * Math.cos(angle) );
			int y = middle.getBlockY();
			int z = (int)( middle.getZ() + radius * Math.sin(angle) );
			locations.add( new Location( middle.getWorld(), x, y, z ) );
		}
		return locations;
	}*/
	
}
