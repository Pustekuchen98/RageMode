package at.dafnik.ragemode.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class PlayerRide implements Listener{

	@EventHandler
	public void onPlayerInteract(PlayerInteractEntityEvent event) {
		if(Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) {
			if(event.getRightClicked() instanceof Player && event.getRightClicked() != event.getPlayer()) {
				Player player = event.getPlayer();
				
				Player ontheTop = getHighestPlayer(player);
				ontheTop.setPassenger(event.getRightClicked());	
					
			       
				//}
			}
		}
	}

	private Player getHighestPlayer(Player player) {
		Player ontheTop = player;
		int zaehler = 0;
		while(true) {
			if(player.getPassenger() != null) {
				ontheTop = (Player) player.getPassenger();
			} else {
				ontheTop = player;
				break;
			}
			
			if(zaehler > 500) break;
			System.out.println(ontheTop.getName());
			zaehler++;
		}
		return ontheTop;
	}
}
