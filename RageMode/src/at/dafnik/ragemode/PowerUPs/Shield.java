package at.dafnik.ragemode.PowerUPs;

import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Threads.ShieldThread;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Shield implements Listener{

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(Main.status == Main.Status.INGAME) {
                if(event.getPlayer().getInventory().getItemInMainHand() != null) {
                    if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.SHIELD
                            && event.getPlayer().getInventory().getItemInOffHand().getType() != Material.SHIELD) {

                        Player player = event.getPlayer();

                        Library.powerup_shield.add(player);
                        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                        player.getInventory().setItemInOffHand(new ItemStack(Material.SHIELD));

                        new ShieldThread(player).start();
                    }
                }
            }
        }
    }
}
