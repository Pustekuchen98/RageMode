package at.dafnik.ragemode.Shop;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.MySQL.SQLCoins;

public class createAdvancedInventoryMenu_SpeedUpgrade extends createAdvancedInventoryMenuBasic{
	
	private static String wantsdisplayname = Strings.inventory_inv_speedupgrader;
	private static Material material = Material.FEATHER;
	private int upgradecost = 0;
	
	public createAdvancedInventoryMenu_SpeedUpgrade(Main main, Player player) {
		super(player, wantsdisplayname, material);
		
		this.upgradecost = main.getConfig().getInt("ragemode.shop.knifeupgradeprice");
	}
	
	@Override
	public void createAdvancedItem() {
		super.chooseitemmeta.setDisplayName(Strings.inventory_inv_speedupgrader);
		super.chooseitemlore.add(Strings.inventory_inv_speedupgrader_description);
			
		super.bookmetalore.add(Strings.inventory_invmore_description_description + Strings.inventory_invmore_description_feather);
		super.bookmetalore.add(Strings.inventory_invmore_description_feather_2);
			
		if(SQLCoins.getSpeedUpgrade(player.getUniqueId().toString())) {
			super.buyitemmeta.setDisplayName(Strings.inventory_flint_bought);
		} else {
			if(SQLCoins.getCoins(player.getUniqueId().toString()) >= upgradecost) {
				super.buyitemmeta.setDisplayName(Strings.inventory_flint_buyable);
			} else {
				super.buyitemmeta.setDisplayName(Strings.inventory_flint_not_enough_coins);
				super.buyitemlore.add("§a" + upgradecost + " §6Coins");
			}
		}
	}
}
