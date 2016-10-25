package at.dafnik.ragemode.Items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.MySQL.SQLCoins;

public class Items {
	
	public static void givePlayerDoubleHeart(Player player) {
		ItemStack i = new ItemStack(Material.REDSTONE, 1);
		ItemMeta imd = i.getItemMeta();
		imd.setDisplayName(Strings.items_doubleheart);
		i.setItemMeta(imd);
		player.getInventory().addItem(i);
	}
	
	public static void givePlayerMine(Player player) {
		ItemStack i = new ItemStack(Material.STONE_PLATE, 2);
		ItemMeta imd = i.getItemMeta();
		imd.setDisplayName(Strings.items_mine);
		i.setItemMeta(imd);
		player.getInventory().addItem(i);
	}
	
	public static void giverPlayerClaymore(Player player) {
		ItemStack i = new ItemStack(Material.FLOWER_POT_ITEM, 4);
		ItemMeta imd = i.getItemMeta();
		imd.setDisplayName(Strings.items_claymore);
		i.setItemMeta(imd);
		player.getInventory().addItem(i);
	}
	
	public static void givePlayerFlash(Player player) {
		ItemStack i = new ItemStack(Material.SNOW_BALL, 4);
		ItemMeta imd = i.getItemMeta();
		imd.setDisplayName(Strings.items_flash);
		i.setItemMeta(imd);
		player.getInventory().addItem(i);
	}
	
	public static void givePlayerFly(Player player) {
		ItemStack i = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta imd = i.getItemMeta();
		imd.setDisplayName(Strings.items_fly);
		i.setItemMeta(imd);
		player.getInventory().addItem(i);
	}
	
	public static void givePlayerC4(Player player) {
		ItemStack i = new ItemStack(Material.STONE_BUTTON, 4);
		ItemMeta imd = i.getItemMeta();
		imd.setDisplayName(Strings.items_c4);
		i.setItemMeta(imd);
		player.getInventory().addItem(i);
	}
	
	public static void givePlayerC4Detonator(Player player) {
		if(!player.getInventory().contains(Material.FLINT_AND_STEEL)) {
			ItemStack i2 = new ItemStack(Material.FLINT_AND_STEEL, 1);
			ItemMeta imd2 = i2.getItemMeta();
			imd2.setDisplayName(Strings.items_c4_detonator);
			i2.setItemMeta(imd2);
			player.getInventory().addItem(i2);
		}
	}

	public static void givePlayerShield(Player player) {
		ItemStack i = new ItemStack(Material.IRON_DOOR, 1);
		ItemMeta imd = i.getItemMeta();
		imd.setDisplayName(Strings.items_shield);
		i.setItemMeta(imd);
		player.getInventory().addItem(i);
	}
	
	public static void givePlayerShopItem(Player player) {
		ItemStack i = new ItemStack(Material.GOLD_NUGGET);
		ItemMeta imd = i.getItemMeta();
		imd.setDisplayName("§6Shop");
		List<String> ilore = new ArrayList<String>();
		ilore.add("§7Right click to use");
		imd.setLore(ilore);
		i.setItemMeta(imd);
		player.getInventory().setItem(2, i);
	}
	
	public static void givePlayerSparcleSwitcher(Player player, String switcher) {
		ItemStack i = new ItemStack(Material.NETHER_STAR);
		ItemMeta imd = i.getItemMeta();
		imd.setDisplayName("§5Sparcles §8- " + switcher);
		List<String> ilore = new ArrayList<String>();
		ilore.add("§7Right click to use");
		imd.setLore(ilore);
		i.setItemMeta(imd);
		player.getInventory().setItem(0, i);
	}
	
	public static void givePlayerHookSwitcher(Player player, String switcher) {
		ItemStack i = new ItemStack(Material.STICK);
		ItemMeta imd = i.getItemMeta();
		imd.setDisplayName("§bHook §8- " + switcher);
		List<String> ilore = new ArrayList<String>();
		ilore.add("§7Right click to use");
		imd.setLore(ilore);
		i.setItemMeta(imd);
		player.getInventory().setItem(1, i);
	}
	
	public static void givePlayerItems(Player player){
		ItemStack i = new ItemStack(Material.BOW);
		ItemMeta imd = i.getItemMeta();
		imd.setDisplayName("§6Bow");
		i.setItemMeta(imd);
		i.addEnchantment(Enchantment.ARROW_INFINITE, 1);
		i.addEnchantment(Enchantment.DURABILITY, 3);
		player.getInventory().setItem(0, i);
		
		if(Main.isMySQL && Main.isShop) {
			if(SQLCoins.getSpectralArrowUpgrade(player.getUniqueId().toString())) {
				ItemStack i2 = new ItemStack(Material.SPECTRAL_ARROW, 64);
				ItemMeta imd2 = i2.getItemMeta();
				imd2.setDisplayName("§6Spectral Arrow");
				i2.setItemMeta(imd2);
				player.getInventory().setItem(35, i2);
				player.getInventory().setItem(34, i2);
				player.getInventory().setItem(33, i2);
				player.getInventory().setItem(32, i2);
				player.getInventory().setItem(31, i2);
			} else {
				ItemStack i2 = new ItemStack(Material.ARROW);
				ItemMeta imd2 = i2.getItemMeta();
				imd2.setDisplayName("§6Arrow");
				i2.setItemMeta(imd2);
				player.getInventory().setItem(35, i2);
			}
		} else {
			ItemStack i2 = new ItemStack(Material.ARROW);
			ItemMeta imd2 = i2.getItemMeta();
			imd2.setDisplayName("§6Arrow");
			i2.setItemMeta(imd2);
			player.getInventory().setItem(35, i2);
		}
		
		ItemStack i3 = new ItemStack(Material.IRON_SPADE);
		ItemMeta imd3 = i3.getItemMeta();
		imd3.setDisplayName("§cKnife §8[§6Ready§8]");
		i3.setItemMeta(imd3);
		player.getInventory().setItem(1, i3);

		ItemStack i4 = new ItemStack(Material.DIAMOND_AXE);
		ItemMeta imd4 = i4.getItemMeta();
		imd4.setDisplayName("§aCombat Axt");
		i4.setItemMeta(imd4);
		player.getInventory().setItem(2, i4);
		
		ItemStack i5 = new ItemStack(Material.EGG);
		ItemMeta imd5 = i5.getItemMeta();
		imd5.setDisplayName("§eCluster Bomb");
		i5.setItemMeta(imd5);
		player.getInventory().setItem(3, i5);
		
		ItemStack i6 = new ItemStack(Material.SNOW_BALL, 1);
		ItemMeta imd6 = i6.getItemMeta();
		imd6.setDisplayName(Strings.items_flash);
		i6.setItemMeta(imd6);
		player.getInventory().setItem(4, i6);
		
		ItemStack i9 = new ItemStack(Material.COMPASS);
		ItemMeta imd9 = i9.getItemMeta();
		imd9.setDisplayName("§8Tracker");
		i9.setItemMeta(imd9);
		player.getInventory().setItem(8, i9);
	}
}
