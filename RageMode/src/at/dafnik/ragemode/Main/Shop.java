package at.dafnik.ragemode.Main;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.MySQL.SQLCoins;

public class Shop implements Listener{
	
	private int speedupgrade;
	private int powerupgrade;
	
	public Shop(Main main) {
		this.speedupgrade = main.getConfig().getInt("ragemode.shop.knifeupgradeprice");
		this.powerupgrade = main.getConfig().getInt("ragemode.shop.bowpowerupgradeprice");
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		if(entity instanceof Villager) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent event) {
		if(event.getRightClicked() instanceof Villager) {
			openkitchooser(event.getPlayer());
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInteractal(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
			if(player.getItemInHand().getType() == Material.GOLD_NUGGET) {
				openkitchooser(player);
			}
		}
	}
	
	private void openkitchooser(Player player) {
		//Generate Inventory
		Inventory inv = Bukkit.createInventory(null, 9, "§6Shop §8| §6Coins§7: §e" + SQLCoins.getCoins(player.getUniqueId().toString()));
		
		String buyn;
		
		ItemStack feather = new ItemStack(Material.FEATHER);
		ItemMeta feathermeta = feather.getItemMeta();
		if(SQLCoins.getSpeedUpgrade(player.getUniqueId().toString()) == 1) {
			buyn = Strings.inventory_bought;
			feathermeta.addEnchant(Enchantment.WATER_WORKER, 1, true);
		}
		else buyn = Strings.inventory_not_bought;
		feathermeta.setDisplayName(Strings.inventory_inv_speedupgrader + buyn);
		List<String> featherlore = new ArrayList<String>();
		featherlore.add(Strings.inventory_inv_speedupgrader_description);
		feathermeta.setLore(featherlore);
		feather.setItemMeta(feathermeta);
		
		
		ItemStack powder = new ItemStack(Material.SULPHUR);
		ItemMeta powdermeta = powder.getItemMeta();
		if(SQLCoins.getBowPowerUpgrade(player.getUniqueId().toString()) == 1) {
			buyn = Strings.inventory_bought;
			powdermeta.addEnchant(Enchantment.WATER_WORKER, 1, true);
		}
		else buyn = Strings.inventory_not_bought;
		powdermeta.setDisplayName(Strings.inventory_inv_bowpowerupgrader + buyn);
		List<String> powderlore = new ArrayList<String>();
		powderlore.add(Strings.inventory_inv_bowpowerupgrader_description);
		powdermeta.setLore(powderlore);
		powder.setItemMeta(powdermeta);
		
		
		inv.setItem(0, feather);
		inv.setItem(2, powder);
		
		player.openInventory(inv);			
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
							openMore("feather", player);
							break;
						
						case SULPHUR:
							player.closeInventory();
							openMore("powder", player);
							
							break;
							
						default:
							player.closeInventory();
							player.sendMessage(Strings.error_inventory_false_click);
							break;
						}
					}
					
					//Feather Kit Chooser
					if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Speed Upgrade")) {
						event.setCancelled(true);
						
						if(event.getCurrentItem() == null ||event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta()){
							player.closeInventory();
							return;
						}
						
						switch(event.getCurrentItem().getType()) {
						case IRON_DOOR:
							player.closeInventory();
							openkitchooser(player);
							break;
							
						
						case BOOK:
							break;
							
						case FEATHER:
							break;
							
						case FLINT_AND_STEEL:
							player.closeInventory();
							if(SQLCoins.getSpeedUpgrade(player.getUniqueId().toString()) == 1) {
								player.getInventory();
								player.sendMessage(Strings.inventory_buy_already_buy);
								break;
							} else {
								if(SQLCoins.getCoins(player.getUniqueId().toString()) >= speedupgrade) {
									SQLCoins.removeCoins(player.getUniqueId().toString(), speedupgrade);
									SQLCoins.setSpeedUpgrade(player.getUniqueId().toString(), 1);
									player.closeInventory();
									player.sendMessage(Strings.inventory_buy_succesfull);
									player.sendMessage(Strings.inventory_buy_new_coins + SQLCoins.getCoins(player.getUniqueId().toString()));
								} else {
									player.closeInventory();
									int need = speedupgrade - SQLCoins.getCoins(player.getUniqueId().toString());
									player.sendMessage(Strings.inventory_buy_not_enough + need);
								}
							}
							break;
						default:
							player.closeInventory();
							player.sendMessage(Strings.error_inventory_false_click);
							break;
						}
					}
					
					//Feather Kit Chooser
					if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Bow Power Upgrade")) {
						event.setCancelled(true);
						
						if(event.getCurrentItem() == null ||event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta()){
							player.closeInventory();
							return;
						}
						
						switch(event.getCurrentItem().getType()) {
						case IRON_DOOR:
							player.closeInventory();
							openkitchooser(player);
							break;
							
						
						case BOOK:
							break;
							
						case FEATHER:
							break;
							
						case FLINT_AND_STEEL:
							player.closeInventory();
							if(SQLCoins.getBowPowerUpgrade(player.getUniqueId().toString()) == 1) {
								player.closeInventory();
								player.sendMessage(Strings.inventory_buy_already_buy);
								break;
							} else {
								if(SQLCoins.getCoins(player.getUniqueId().toString()) >= powerupgrade) {
									SQLCoins.removeCoins(player.getUniqueId().toString(), powerupgrade);
									SQLCoins.setBowPowerUpgrade(player.getUniqueId().toString(), 1);
									player.closeInventory();
									player.sendMessage(Strings.inventory_buy_succesfull);
									player.sendMessage(Strings.inventory_buy_new_coins + SQLCoins.getCoins(player.getUniqueId().toString()));
								} else {
									player.closeInventory();
									int need = powerupgrade - SQLCoins.getCoins(player.getUniqueId().toString());
									player.sendMessage(Strings.inventory_buy_not_enough + need);
								}
							}
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
	
	public void openMore(String wants, Player player) {
		
		String wantsdisplayname = null;
		if(wants == "feather") wantsdisplayname = Strings.inventory_inv_speedupgrader;
		else if(wants == "powder") wantsdisplayname = Strings.inventory_inv_bowpowerupgrader;
		Inventory invmore = Bukkit.createInventory(null, 9, wantsdisplayname);
		
		ItemStack i1 = null;
		ItemMeta im1 = null;
		List<String> ilore = new ArrayList<String>();
		
		ItemStack i2 = new ItemStack(Material.BOOK);
		ItemMeta im2 = i2.getItemMeta();
		im2.setDisplayName(Strings.inventory_invmore_description);
		List<String> i2lore = new ArrayList<String>();
		
		ItemStack i8 = null;
		ItemMeta im8 = null;
		List<String> i8lore = new ArrayList<String>();
		
		if(wants == "feather") {
			i1 = new ItemStack(Material.FEATHER);
			im1 = i1.getItemMeta();
			im1.setDisplayName(Strings.inventory_inv_speedupgrader);
			ilore.add(Strings.inventory_inv_speedupgrader_description);
			
			i2lore.add(Strings.inventory_invmore_description_description + Strings.inventory_invmore_description_feather);
			i2lore.add(Strings.inventory_invmore_description_feather_2);
			
			i8 = new ItemStack(Material.FLINT_AND_STEEL);
			im8 = i8.getItemMeta();
			if(SQLCoins.getSpeedUpgrade(player.getUniqueId().toString()) == 1) {
				im8.setDisplayName(Strings.inventory_flint_bought);
			} else {
				if(SQLCoins.getCoins(player.getUniqueId().toString()) >= speedupgrade) {
					im8.setDisplayName(Strings.inventory_flint_buyable);
				} else {
					im8.setDisplayName(Strings.inventory_flint_not_enough_coins);
					i8lore.add("§a" + speedupgrade + " §6Coins");
				}
			}
			
		} else if(wants == "powder") {
			i1 = new ItemStack(Material.SULPHUR);
			im1 = i1.getItemMeta();
			im1.setDisplayName(Strings.inventory_inv_bowpowerupgrader);
			ilore.add(Strings.inventory_inv_bowpowerupgrader_description);
			
			i2lore.add(Strings.inventory_invmore_description_description + Strings.inventory_invmore_description_powder);
			i2lore.add(Strings.inventory_invmore_description_powder_2);
			
			i8 = new ItemStack(Material.FLINT_AND_STEEL);
			im8 = i8.getItemMeta();
			if(SQLCoins.getBowPowerUpgrade(player.getUniqueId().toString()) == 1) {
				im8.setDisplayName(Strings.inventory_flint_bought);
			} else {
				if(SQLCoins.getCoins(player.getUniqueId().toString()) >= powerupgrade) {
					im8.setDisplayName(Strings.inventory_flint_buyable);
				} else {
					im8.setDisplayName(Strings.inventory_flint_not_enough_coins);
					i8lore.add("§a" + powerupgrade + " §6Coins");
				}
			}
		}
		
		im1.setLore(ilore);
		i1.setItemMeta(im1);
		invmore.setItem(0, i1);	
		
		im2.setLore(i2lore);
		i2.setItemMeta(im2);
		invmore.setItem(1, i2);
		
		im8.setLore(i8lore);
		i8.setItemMeta(im8);
		invmore.setItem(7, i8);
		
		ItemStack i9 = new ItemStack(Material.IRON_DOOR);
		ItemMeta im9 = i9.getItemMeta();
		im9.setDisplayName(Strings.inventory_invmore_back_to_inv);
		i9.setItemMeta(im9);	
		invmore.setItem(8, i9);
		
	
		player.openInventory(invmore);		
	}
}
