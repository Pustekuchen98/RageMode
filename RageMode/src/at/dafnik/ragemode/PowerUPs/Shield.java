package at.dafnik.ragemode.PowerUPs;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Items.Items;
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
                    if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.IRON_DOOR) {
                        Player player = event.getPlayer();

                        Library.powerup_shield.add(player);

                        int amount = event.getItem().getAmount() - 1;
                        player.getInventory().remove(event.getItem());

                        if(amount > 0) {
                            for(int i = 0; i < amount; i++) {
                                Items.givePlayerShield(player);
                            }
                        }

                        ItemStack itemStack = new ItemStack(Material.IRON_DOOR);
                        itemStack.getItemMeta().setDisplayName(Strings.items_shield);
                        player.getInventory().setItemInOffHand(itemStack);

                        new ShieldThread(player).start();
                    }
                }
            }
        }
    }
}
