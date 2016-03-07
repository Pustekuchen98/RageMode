package at.dafnik.ragemode.Shop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.MySQL.SQLCoins;

public class createShopItems {
	
	public static ItemStack createMainShopInventory(Player player, Material shopItem) {
		String buyn = null;
		
		ItemStack item = new ItemStack(shopItem);
		ItemMeta itemmeta = item.getItemMeta();
		List<String> itemlore = new ArrayList<String>();
		
		if(shopItem == Material.FEATHER) {
			if(SQLCoins.getSpeedUpgrade(player.getUniqueId().toString())) {
				buyn = Strings.inventory_bought;
				itemmeta.addEnchant(Enchantment.WATER_WORKER, 1, true);
			} else buyn = Strings.inventory_not_bought;
			itemmeta.setDisplayName(Strings.inventory_inv_speedupgrader + buyn);
			itemlore.add(Strings.inventory_inv_speedupgrader_description);
			
		} else if(shopItem == Material.SULPHUR) {
			if(SQLCoins.getBowPowerUpgrade(player.getUniqueId().toString())) {
				buyn = Strings.inventory_bought;
				itemmeta.addEnchant(Enchantment.WATER_WORKER, 1, true);
			} else buyn = Strings.inventory_not_bought;
			itemmeta.setDisplayName(Strings.inventory_inv_bowpowerupgrader + buyn);
			itemlore.add(Strings.inventory_inv_bowpowerupgrader_description);
			
		} else if(shopItem == Material.BLAZE_POWDER) {
			if(SQLCoins.getKnockbackUpgrade(player.getUniqueId().toString())) {
				buyn = Strings.inventory_bought;
				itemmeta.addEnchant(Enchantment.WATER_WORKER, 1, true);
			} else buyn = Strings.inventory_not_bought;
			itemmeta.setDisplayName(Strings.inventory_inv_knockbackupgrade + buyn);
			itemlore.add(Strings.inventory_inv_knockbackupgrade_description);
		
		} else if(shopItem == Material.SPECTRAL_ARROW) {
			if(SQLCoins.getSpectralArrowUpgrade(player.getUniqueId().toString())) {
				buyn = Strings.inventory_bought;
				itemmeta.addEnchant(Enchantment.WATER_WORKER, 1, true);
			} else buyn = Strings.inventory_not_bought;
			itemmeta.setDisplayName(Strings.inventory_inv_spectralarrowupgrade);
			itemlore.add(Strings.inventory_inv_spectralarrowupgrade_description);
		
		} else return null;
		
		itemmeta.setLore(itemlore);
		item.setItemMeta(itemmeta);
		
		return item;
	}
	
	public static void createAdvancedInventoryMenu(Player player, String name, int upgradecost) {
		String wantsdisplayname = null;
		Material material = null;
		
		if(name == "Speed Upgrade") {
			wantsdisplayname = Strings.inventory_inv_speedupgrader;
			material = Material.FEATHER;
			
		} else if(name == "Bow Power Upgrade") {
			wantsdisplayname = Strings.inventory_inv_bowpowerupgrader;
			material = Material.SULPHUR;
			
		} else if(name == "Knockback ability Upgrade") {
			wantsdisplayname = Strings.inventory_inv_knockbackupgrade;
			material = Material.BLAZE_POWDER;
			
		} else if(name == "Spectral Arrow Upgrade") {
			wantsdisplayname = Strings.inventory_inv_spectralarrowupgrade;
			material = Material.SPECTRAL_ARROW;
			
		} else System.out.println(Strings.error_wrong_inv_string + " | createAdvancedInventoryMenu Wantsdispaly and Material");
		
		Inventory inventory = Bukkit.createInventory(null, 9, wantsdisplayname);
		
		ItemStack chooseitem = new ItemStack(material);
		ItemMeta chooseitemmeta = chooseitem.getItemMeta();
		List<String> chooseitemlore = new ArrayList<String>();
		
		ItemStack bookitem = new ItemStack(Material.BOOK);
		ItemMeta bookitemmeta = bookitem.getItemMeta();
		bookitemmeta.setDisplayName(Strings.inventory_invmore_description);
		List<String> bookmetalore = new ArrayList<String>();
		
		ItemStack buyitem = new ItemStack(Material.FLINT_AND_STEEL);;
		ItemMeta buyitemmeta = buyitem.getItemMeta();;
		List<String> buyitemlore = new ArrayList<String>();
		
		if(name == "Speed Upgrade") {
			chooseitemmeta.setDisplayName(Strings.inventory_inv_speedupgrader);
			chooseitemlore.add(Strings.inventory_inv_speedupgrader_description);
				
			bookmetalore.add(Strings.inventory_invmore_description_description + Strings.inventory_invmore_description_feather);
			bookmetalore.add(Strings.inventory_invmore_description_feather_2);
				
			if(SQLCoins.getSpeedUpgrade(player.getUniqueId().toString())) {
				buyitemmeta.setDisplayName(Strings.inventory_flint_bought);
			} else {
				if(SQLCoins.getCoins(player.getUniqueId().toString()) >= upgradecost) {
					buyitemmeta.setDisplayName(Strings.inventory_flint_buyable);
				} else {
					buyitemmeta.setDisplayName(Strings.inventory_flint_not_enough_coins);
					buyitemlore.add("§a" + upgradecost + " §6Coins");
				}
			}
			
		} else if(name == "Bow Power Upgrade") {
			chooseitemmeta.setDisplayName(Strings.inventory_inv_bowpowerupgrader);
			chooseitemlore.add(Strings.inventory_inv_bowpowerupgrader_description);
			
			bookmetalore.add(Strings.inventory_invmore_description_description + Strings.inventory_invmore_description_powder);
			bookmetalore.add(Strings.inventory_invmore_description_powder_2);
			
			if(SQLCoins.getBowPowerUpgrade(player.getUniqueId().toString())) {
				buyitemmeta.setDisplayName(Strings.inventory_flint_bought);
			} else {
				if(SQLCoins.getCoins(player.getUniqueId().toString()) >= upgradecost) {
					buyitemmeta.setDisplayName(Strings.inventory_flint_buyable);
				} else {
					buyitemmeta.setDisplayName(Strings.inventory_flint_not_enough_coins);
					buyitemlore.add("§a" + upgradecost + " §6Coins");
				}
			}
			
		} else if(name == "Knockback ability Upgrade") {
			chooseitemmeta.setDisplayName(Strings.inventory_inv_knockbackupgrade);
			chooseitemlore.add(Strings.inventory_inv_knockbackupgrade_description);
			
			bookmetalore.add(Strings.inventory_invmore_description_description + Strings.inventory_invmore_description_blazepowder);
			bookmetalore.add(Strings.inventory_invmore_description_blazepowder_2);
			
			if(SQLCoins.getKnockbackUpgrade(player.getUniqueId().toString())) {
				buyitemmeta.setDisplayName(Strings.inventory_flint_bought);
			} else {
				if(SQLCoins.getCoins(player.getUniqueId().toString()) >= upgradecost) {
					buyitemmeta.setDisplayName(Strings.inventory_flint_buyable);
				} else {
					buyitemmeta.setDisplayName(Strings.inventory_flint_not_enough_coins);
					buyitemlore.add("§a" + upgradecost + " §6Coins");
				}
			}
			
		} else if(name == "Spectral Arrow Upgrade") {
			chooseitemmeta.setDisplayName(Strings.inventory_inv_spectralarrowupgrade);
			chooseitemlore.add(Strings.inventory_inv_spectralarrowupgrade_description);
			
			bookmetalore.add(Strings.inventory_invmore_description_description + Strings.inventory_invmore_description_spectralarrow);
			bookmetalore.add(Strings.inventory_invmore_description_spectralarrow_2);
			
			if(SQLCoins.getSpectralArrowUpgrade(player.getUniqueId().toString())) {
				buyitemmeta.setDisplayName(Strings.inventory_flint_bought);
			} else {
				if(SQLCoins.getCoins(player.getUniqueId().toString()) >= upgradecost) {
					buyitemmeta.setDisplayName(Strings.inventory_flint_buyable);
				} else {
					buyitemmeta.setDisplayName(Strings.inventory_flint_not_enough_coins);
					buyitemlore.add("§a" + upgradecost + " §6Coins");
				}
			}
		} else System.out.println(Strings.error_wrong_inv_string + " | createAdvancedInventoryMenu");
		
		chooseitemmeta.setLore(chooseitemlore);
		chooseitem.setItemMeta(chooseitemmeta);
		inventory.setItem(0, chooseitem);	
			
		bookitemmeta.setLore(bookmetalore);
		bookitem.setItemMeta(bookitemmeta);
		inventory.setItem(1, bookitem);
			
		buyitemmeta.setLore(buyitemlore);
		buyitem.setItemMeta(buyitemmeta);
		inventory.setItem(7, buyitem);
			
		ItemStack i9 = new ItemStack(Material.IRON_DOOR);
		ItemMeta im9 = i9.getItemMeta();
		im9.setDisplayName(Strings.inventory_invmore_back_to_inv);
		i9.setItemMeta(im9);	
		inventory.setItem(8, i9);
			
		player.openInventory(inventory);		
	}
}
