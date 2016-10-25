package at.dafnik.ragemode.PowerUPs;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import at.dafnik.ragemode.API.C4Speicher;
import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class C4Detonator implements Listener {

	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		if(Main.status == Status.INGAME) {
			if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if(Library.ingameplayer.contains(event.getPlayer())) {
					if(event.getItem() != null) {
						if(event.getItem().getType() == Material.FLINT_AND_STEEL) {
							for(C4Speicher c4s : Library.plantedc4) {
								if(c4s.getPlayer() == event.getPlayer()) {
									c4s.detonateAll();
									
									if(!(event.getPlayer().getInventory().contains(Material.STONE_BUTTON))) event.getPlayer().getInventory().remove(Material.FLINT_AND_STEEL);
									
									Library.plantedc4.remove(c4s);
									break;
								}
							}
						}
					}
				}
			}
		}
	}
}
