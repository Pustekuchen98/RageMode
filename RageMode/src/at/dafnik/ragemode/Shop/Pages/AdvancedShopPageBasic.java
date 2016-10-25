package at.dafnik.ragemode.Shop.Pages;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.Shop.Shop;
import net.md_5.bungee.api.ChatColor;

public abstract class AdvancedShopPageBasic implements Listener{

	public String pagename = "";
	
	public AdvancedShopPageBasic(String pagename) {
		this.pagename = pagename;
		
		Main.getInstance().getServer().getPluginManager().registerEvents(this, Main.getInstance());
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) {
			if(event.getWhoClicked() instanceof Player) {
				if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase(pagename)) {
					Player player = (Player) event.getWhoClicked();
					event.setCancelled(true);
					
					switch(event.getCurrentItem().getType()) {
					case IRON_DOOR:
						player.closeInventory();
						Shop.createBasicShopMenu(player);
						break;			
					
					case BOOK:
						break;
						
					case FEATHER:
						break;
						
					case SULPHUR:
						break;
						
					case SPECTRAL_ARROW:
						break;
						
					case BLAZE_POWDER:
						break;
						
					case FLINT_AND_STEEL:
						BuyEvent(player, event);
						break;
						
					default:
						break;
		
					}
								
				//Player check
				}
			}
		//Status check
		}
	}
	
	public void BuyEvent(Player player, InventoryClickEvent event) {
		//Created in the class
	}
}
