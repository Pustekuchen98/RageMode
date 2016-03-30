package at.dafnik.ragemode.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.PowerSystem;
import net.minecraft.server.v1_9_R1.AttributeInstance;
import net.minecraft.server.v1_9_R1.EntityInsentient;
import net.minecraft.server.v1_9_R1.GenericAttributes;
import net.minecraft.server.v1_9_R1.PathEntity;

public class RoundStart implements CommandExecutor{
	
	private Boolean happened = false;
	private Ocelot pet;
	
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
					
					
					if(!happened) {
					pet = (Ocelot) player.getWorld().spawnEntity(player.getLocation(), EntityType.OCELOT);
					pet.setOwner(player);
					PetFollow(player, pet, 0.2);
					happened = true;
					} else {
						pet.remove();
						happened = false;
					}
					player.sendMessage("r ");			
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
	
	public void PetFollow(final Player player , final Entity pet , final double speed){
		new BukkitRunnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				if ((!pet.isValid() || (!player.isOnline()))) {
					this.cancel();
				}
				net.minecraft.server.v1_9_R1.Entity pett = ((CraftEntity) pet).getHandle();
				((EntityInsentient) pett).getNavigation().a(2);
				Object petf = ((CraftEntity) pet).getHandle();
				Location targetLocation = player.getLocation();
				PathEntity path;
				path = ((EntityInsentient) petf).getNavigation().a(targetLocation.getX() + 1, targetLocation.getY(), targetLocation.getZ() + 1);
				if (path != null) {
					((EntityInsentient) petf).getNavigation().a(path, 1.0D);
					((EntityInsentient) petf).getNavigation().a(2.0D);
				}
				
				int distance = (int) Bukkit.getPlayer(player.getName()).getLocation().distance(pet.getLocation());
				if (distance > 10 && !pet.isDead() && player.isOnGround()) {
					pet.teleport(player.getLocation());
				}
				
				AttributeInstance attributes = ((EntityInsentient)((CraftEntity)pet).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
				attributes.setValue(speed);
			}
		}.runTaskTimer(Main.getInstance(), 0L, 20L);
	}
}
