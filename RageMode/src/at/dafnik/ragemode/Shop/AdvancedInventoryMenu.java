package at.dafnik.ragemode.Shop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.MySQL.SQLCoins;
import net.md_5.bungee.api.ChatColor;

public class AdvancedInventoryMenu implements Listener{
	
	private int speedupgrade;
	private int powerupgrade;
	private int knockbackupgrade;
	private int spectralarrowupgrade;
	
	public AdvancedInventoryMenu(Main main) {
		this.speedupgrade = main.getConfig().getInt("ragemode.shop.knifeupgradeprice");
		this.powerupgrade = main.getConfig().getInt("ragemode.shop.bowpowerupgradeprice");
		this.knockbackupgrade = main.getConfig().getInt("ragemode.shop.knifeknockbackupgradeprice");
		this.spectralarrowupgrade = main.getConfig().getInt("ragemode.shop.spectralarrowupgradeprice");
	}
	
	
	
	@EventHandler
	public void AdvancedInventoryMenuListener(InventoryClickEvent event) {
		if(Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) {
			if(event.getWhoClicked() instanceof Player) {				
				if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Speed Upgrade") || 
						ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Bow Power Upgrade") ||
						ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Knockback ability Upgrade") ||
						ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Spectral Arrow Upgrade")) {
					
					Player player = (Player) event.getWhoClicked();
					
					event.setCancelled(true);
					
					if(event.getCurrentItem() == null ||event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta()){
						player.closeInventory();
						return;
					}
					
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
						int upgradecost = 0;
						
						if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Speed Upgrade")) {
							upgradecost = this.speedupgrade;
							
							if(SQLCoins.getSpeedUpgrade(player.getUniqueId().toString())) {
								player.sendMessage(Strings.inventory_buy_already_buy);
							} else {
								if(SQLCoins.getCoins(player.getUniqueId().toString()) >= upgradecost) {
									SQLCoins.removeCoins(player.getUniqueId().toString(), upgradecost);
									SQLCoins.setSpeedUpgrade(player.getUniqueId().toString(), true);
									player.sendMessage(Strings.inventory_buy_succesfull);
									player.sendMessage(Strings.inventory_buy_new_coins + SQLCoins.getCoins(player.getUniqueId().toString()));
								} else {
									int need = upgradecost - SQLCoins.getCoins(player.getUniqueId().toString());
									player.sendMessage(Strings.inventory_buy_not_enough + need);
								}
							}
							
						} else if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Bow Power Upgrade")) {
							upgradecost = this.powerupgrade;
							
							if(SQLCoins.getBowPowerUpgrade(player.getUniqueId().toString())) {
								player.sendMessage(Strings.inventory_buy_already_buy);
							} else {
								if(SQLCoins.getCoins(player.getUniqueId().toString()) >= upgradecost) {
									SQLCoins.removeCoins(player.getUniqueId().toString(), upgradecost);
									SQLCoins.setBowPowerUpgrade(player.getUniqueId().toString(), true);
									player.sendMessage(Strings.inventory_buy_succesfull);
									player.sendMessage(Strings.inventory_buy_new_coins + SQLCoins.getCoins(player.getUniqueId().toString()));
								} else {
									int need = upgradecost - SQLCoins.getCoins(player.getUniqueId().toString());
									player.sendMessage(Strings.inventory_buy_not_enough + need);
								}
							}
							
						} else if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Knockback ability Upgrade")) {
							upgradecost = this.knockbackupgrade;
							
							if(SQLCoins.getSpectralArrowUpgrade(player.getUniqueId().toString())) {
								player.sendMessage(Strings.inventory_buy_already_buy);
							} else {
								if(SQLCoins.getCoins(player.getUniqueId().toString()) >= upgradecost) {
									SQLCoins.removeCoins(player.getUniqueId().toString(), upgradecost);
									SQLCoins.setSpectralArrowUpgrade(player.getUniqueId().toString(), true);
									player.sendMessage(Strings.inventory_buy_succesfull);
									player.sendMessage(Strings.inventory_buy_new_coins + SQLCoins.getCoins(player.getUniqueId().toString()));
								} else {
									int need = upgradecost - SQLCoins.getCoins(player.getUniqueId().toString());
									player.sendMessage(Strings.inventory_buy_not_enough + need);
								}
							}
							
						} else if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Spectral Arrow Upgrade")) {
							upgradecost = this.spectralarrowupgrade;
							
							if(SQLCoins.getSpectralArrowUpgrade(player.getUniqueId().toString())) {
								player.sendMessage(Strings.inventory_buy_already_buy);
							} else {
								if(SQLCoins.getCoins(player.getUniqueId().toString()) >= upgradecost) {
									SQLCoins.removeCoins(player.getUniqueId().toString(), upgradecost);
									SQLCoins.setSpectralArrowUpgrade(player.getUniqueId().toString(), true);
									player.sendMessage(Strings.inventory_buy_succesfull);
									player.sendMessage(Strings.inventory_buy_new_coins + SQLCoins.getCoins(player.getUniqueId().toString()));
								} else {
									int need = upgradecost - SQLCoins.getCoins(player.getUniqueId().toString());
									player.sendMessage(Strings.inventory_buy_not_enough + need);
								}
							}
							
						} else System.out.println(Strings.error_wrong_inv_string + " | AdvancedInventoryMenuListener Upgradecost check lel");
						
						player.closeInventory();
						break;
					default:
						player.closeInventory();
						player.sendMessage(Strings.error_inventory_false_click);
						break;
					}
				}
			}
		}
	}
}
