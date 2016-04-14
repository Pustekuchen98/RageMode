package at.dafnik.ragemode.Shop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
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
			itemmeta.setDisplayName(Strings.inventory_inv_spectralarrowupgrade + buyn);
			itemlore.add(Strings.inventory_inv_spectralarrowupgrade_description);
		
		} else return null;
		
		itemmeta.setLore(itemlore);
		item.setItemMeta(itemmeta);
		
		return item;
	}
}
