package at.dafnik.ragemode.Shop;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.MySQL.SQLCoins;
import at.dafnik.ragemode.Shop.Creator.createAdvancedInventoryMenu_BowPowerUpgrade;
import at.dafnik.ragemode.Shop.Creator.createAdvancedInventoryMenu_DoublePowerUPsUpgrade;
import at.dafnik.ragemode.Shop.Creator.createAdvancedInventoryMenu_KnockbackAbilityUpgrade;
import at.dafnik.ragemode.Shop.Creator.createAdvancedInventoryMenu_SpectralArrowUpgrade;
import at.dafnik.ragemode.Shop.Creator.createAdvancedInventoryMenu_SpeedUpgrade;

public class Shop implements Listener{
	
	public int speedupgrade;
	public int powerupgrade;
	public int knockbackupgrade;
	public int spectralarrowupgrade;
	
	public Shop() {		
		this.speedupgrade = Main.getInstance().getConfig().getInt("ragemode.shop.knifeupgradeprice");
		this.powerupgrade = Main.getInstance().getConfig().getInt("ragemode.shop.bowpowerupgradeprice");
		this.knockbackupgrade = Main.getInstance().getConfig().getInt("ragemode.shop.knifeknockbackupgradeprice");
		this.spectralarrowupgrade = Main.getInstance().getConfig().getInt("ragemode.shop.spectralarrowupgradeprice");
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		if(entity instanceof Villager) {
			if(Library.villager != null) {
				if(Library.villager.getVillager() == ((Villager) entity))
					event.setCancelled(true);
				else 
					event.setCancelled(false);
			}
		}
	}
	
	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent event) {
		if(event.getRightClicked() instanceof Villager) {
			if(Main.isMySQL && Main.isShop){
				createBasicShopMenu(event.getPlayer());
				event.setCancelled(true);
				
			}
		} else {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInteractal(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
			if(player.getInventory().getItemInMainHand().getType() == Material.GOLD_NUGGET) {
				if(Main.isMySQL && Main.isShop) createBasicShopMenu(player);
			}
		}
	}
	
	public static void createBasicShopMenu(Player player) {
		//Generate Inventory
		Inventory inventory = Bukkit.createInventory(null, 9, "§6Shop §8| §6Coins§7: §e" + SQLCoins.getCoins(player.getUniqueId().toString()));	
		
		inventory.setItem(0, createShopItems.createMainShopInventory(player, Material.FEATHER));
		inventory.setItem(2, createShopItems.createMainShopInventory(player, Material.SULPHUR));
		inventory.setItem(4, createShopItems.createMainShopInventory(player, Material.BLAZE_POWDER));
		inventory.setItem(6, createShopItems.createMainShopInventory(player, Material.SPECTRAL_ARROW));
		inventory.setItem(8, createShopItems.createMainShopInventory(player, Material.EMERALD_BLOCK));
		
		player.openInventory(inventory);			
		// |0|1|2|3|4|5|6|7|8| 	
	}
	
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Entity entity = event.getWhoClicked();
		
		if(entity instanceof Player) {
			//Check Main Status
			if(Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) {		
				Player player = (Player) event.getWhoClicked();
				
				if(Main.isMySQL && Main.isShop) {		
					//Check Name of Inf
					if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Shop | Coins: " + SQLCoins.getCoins(player.getUniqueId().toString()))) {
						event.setCancelled(true);
						
						if(event.getCurrentItem() == null ||event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta()){
							player.closeInventory();
							return;
						}
						
						switch(event.getCurrentItem().getType()) {
						case FEATHER:
							player.closeInventory();
							new createAdvancedInventoryMenu_SpeedUpgrade(player).createBasics();				
							break;
						
						case SULPHUR:
							player.closeInventory();
							new createAdvancedInventoryMenu_BowPowerUpgrade(player).createBasics();
							break;
							
						case BLAZE_POWDER:
							player.closeInventory();
							new createAdvancedInventoryMenu_KnockbackAbilityUpgrade(player).createBasics();
							break;
							
						case SPECTRAL_ARROW:
							player.closeInventory();
							new createAdvancedInventoryMenu_SpectralArrowUpgrade(player).createBasics();
							break;
							
						case EMERALD_BLOCK:
							player.closeInventory();
							new createAdvancedInventoryMenu_DoublePowerUPsUpgrade(player).createBasics();
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
}
