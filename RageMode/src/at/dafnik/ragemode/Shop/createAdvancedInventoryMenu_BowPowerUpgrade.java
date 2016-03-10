package at.dafnik.ragemode.Shop;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.MySQL.SQLCoins;

public class createAdvancedInventoryMenu_BowPowerUpgrade extends createAdvancedInventoryMenuBasic{
	
	public static String wantsdisplayname = Strings.inventory_inv_bowpowerupgrader;
	public static Material material = Material.SULPHUR;
	private int upgradecost = 0;
	
	public createAdvancedInventoryMenu_BowPowerUpgrade(Player player) {
		super(player, wantsdisplayname, material);
		
		this.upgradecost = Main.getInstance().getConfig().getInt("ragemode.shop.bowpowerupgradeprice");
	}
	
	@Override
	public void createAdvancedItem() {
		super.chooseitemmeta.setDisplayName(Strings.inventory_inv_bowpowerupgrader);
		super.chooseitemlore.add(Strings.inventory_inv_bowpowerupgrader_description);
		
		super.bookmetalore.add(Strings.inventory_invmore_description_description + Strings.inventory_invmore_description_powder);
		super.bookmetalore.add(Strings.inventory_invmore_description_powder_2);
		
		if(SQLCoins.getBowPowerUpgrade(player.getUniqueId().toString())) {
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
