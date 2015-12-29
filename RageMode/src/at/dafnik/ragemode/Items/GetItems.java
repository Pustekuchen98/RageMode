package at.dafnik.ragemode.Items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GetItems {
	
	public void getItems(Player player){
		
		ItemStack i = new ItemStack(Material.BOW);
		ItemMeta imd = i.getItemMeta();
		imd.setDisplayName("§6Bow");
		i.setItemMeta(imd);
		i.addEnchantment(Enchantment.ARROW_INFINITE, 1);
		i.addEnchantment(Enchantment.DURABILITY, 3);
		player.getInventory().setItem(0, i);
		
		ItemStack i2 = new ItemStack(Material.ARROW);
		ItemMeta imd2 = i2.getItemMeta();
		imd2.setDisplayName("§6Arrow");
		i2.setItemMeta(imd2);
		player.getInventory().setItem(35, i2);
		
		ItemStack i3 = new ItemStack(Material.IRON_SPADE);
		ItemMeta imd3 = i3.getItemMeta();
		imd3.setDisplayName("§cKnife §8[§6Ready§8]");
		//imd3.addEnchant(Enchantment.DAMAGE_ALL, 15, true);
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
		
		ItemStack i9 = new ItemStack(Material.COMPASS);
		ItemMeta imd9 = i9.getItemMeta();
		imd9.setDisplayName("§8Tracker");
		i9.setItemMeta(imd9);
		player.getInventory().setItem(8, i9);
		
	}
}
