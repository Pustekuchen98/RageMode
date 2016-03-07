package at.dafnik.ragemode.Shop;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public abstract class AdvancedShopPageBasic implements Listener{

	public String pagename = "";
	private Main plugin;
	
	public AdvancedShopPageBasic(Main main, String pagename) {
		this.pagename = pagename;
		this.plugin = main;
		
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) {
			if(event.getWhoClicked() instanceof Player) {
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
					player.closeInventory();
					player.sendMessage(Strings.error_inventory_false_click);
					break;
	
				}
				
				
			//Player check
			}
		//Status check
		}
	}
	
	public void BuyEvent(Player player, InventoryClickEvent event) {
		//Created in the class
	}
}
