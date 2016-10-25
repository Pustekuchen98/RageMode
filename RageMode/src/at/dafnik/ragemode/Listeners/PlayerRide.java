package at.dafnik.ragemode.Listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;
import net.minecraft.server.v1_10_R1.PacketPlayOutMount;

public class PlayerRide implements Listener{

	@EventHandler
	public void onPlayerInteract(PlayerInteractEntityEvent event) {
		if(Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) {
			if(event.getRightClicked() instanceof Player && event.getRightClicked() != event.getPlayer()) {
				Player player = event.getPlayer();
				Entity entity = event.getRightClicked();
				Entity ontheTop = getHighestEntity(player);
				
				if(entity != ontheTop) {
					try { 
						if(!(Library.allowedhook.contains(entity) || Library.allowedhook.contains(player))) ontheTop.setPassenger(event.getRightClicked());						
					} catch (Exception ex) {}
					
					reloadPacket();
				}
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) {
			if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
				if(event.getMaterial() == Material.AIR) {
					Player player = event.getPlayer();
					
					if(player.getPassenger() != null) {					
						for(Entity entities : getAllPassangers(player)) {
							if(entities != player && entities instanceof Player) {
								entities.leaveVehicle();
								Random random = new Random();
								switch(random.nextInt(4)) {
								case 0:
									entities.setVelocity(player.getLocation().getDirection().multiply(2D));
									break;
								case 1:
									entities.setVelocity(player.getLocation().getDirection().multiply(2.2D));
									break;
								case 2: 
									entities.setVelocity(player.getLocation().getDirection().multiply(1.7D));
									break;
								case 3: 
									entities.setVelocity(player.getLocation().getDirection().multiply(2.5D));
									break;
								}
								
								reloadPacket();
							}
						}
					}		
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerSneak(PlayerToggleSneakEvent event) {
		if(Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) {
			reloadPacket();
		}
	}

	private Entity getHighestEntity(Player player) {
		Entity ontheTop = player;
		int zaehler = 0;
		while(true) {
			if(ontheTop.getPassenger() != null) {
				ontheTop = player.getPassenger();
			} else {
				ontheTop = player;
				break;
			}
			
			if(zaehler > Bukkit.getOnlinePlayers().size()) break;
			zaehler++;
		}
		return ontheTop;
	}
	
	private List<Entity> getAllPassangers(Player player) {
		List<Entity> passengers = new ArrayList<>();
		Entity ontheTop = player;
		int zaehler = 0;
		while(true) {
			if(ontheTop.getPassenger() != null) {
				ontheTop = ontheTop.getPassenger();
				passengers.add(ontheTop);
			} else {
				passengers.add(ontheTop);
				break;
			}
			
			if(zaehler > Bukkit.getOnlinePlayers().size()) break;
			zaehler++;
		}
		return passengers;
	}
	
	private void reloadPacket() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			PacketPlayOutMount packet = new PacketPlayOutMount(((CraftPlayer) player).getHandle());
			for(Player players : Bukkit.getOnlinePlayers()) {
				((CraftPlayer)players).getHandle().playerConnection.sendPacket(packet);
			}
		}
	}
}
