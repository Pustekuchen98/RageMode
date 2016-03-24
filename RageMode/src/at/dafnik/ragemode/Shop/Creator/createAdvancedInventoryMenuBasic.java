package at.dafnik.ragemode.Shop.Creator;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.dafnik.ragemode.API.Strings;

public abstract class createAdvancedInventoryMenuBasic {
	
	private String wantsdisplayname = "";
	private Material material = null;
	protected Player player = null;
	
	protected Inventory inventory = null;
	protected ItemStack chooseitem = null;
	protected ItemMeta chooseitemmeta = null;
	protected List<String> chooseitemlore = null;
	protected ItemStack bookitem = null;
	protected ItemMeta bookitemmeta = null;
	protected List<String> bookmetalore = null;
	protected ItemStack buyitem = null;
	protected ItemMeta buyitemmeta = null;
	protected List<String> buyitemlore = null;
	
	public createAdvancedInventoryMenuBasic(Player player, String wantsdisplayname, Material material) {
		this.wantsdisplayname = wantsdisplayname;
		this.material = material;
		this.player = player;
	}
	
	public void createBasics() {
		inventory = Bukkit.createInventory(null, 9, wantsdisplayname);
		
		chooseitem = new ItemStack(material);
		chooseitemmeta = chooseitem.getItemMeta();
		chooseitemlore = new ArrayList<String>();
		
		bookitem = new ItemStack(Material.BOOK);
		bookitemmeta = bookitem.getItemMeta();
		bookitemmeta.setDisplayName(Strings.inventory_invmore_description);
		bookmetalore = new ArrayList<String>();
		
		buyitem = new ItemStack(Material.FLINT_AND_STEEL);;
		buyitemmeta = buyitem.getItemMeta();;
		buyitemlore = new ArrayList<String>();
		
		createAdvancedItem();
		
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

	protected void createAdvancedItem() {
		//Some things
	}	
}
