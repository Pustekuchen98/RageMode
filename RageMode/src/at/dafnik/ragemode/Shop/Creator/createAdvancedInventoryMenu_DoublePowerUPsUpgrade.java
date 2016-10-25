package at.dafnik.ragemode.Shop.Creator;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.MySQL.SQLCoins;

public class createAdvancedInventoryMenu_DoublePowerUPsUpgrade extends createAdvancedInventoryMenuBasic{
	
	public static String wantsdisplayname = Strings.shop_item_doublepowerup_name;
	public static Material material = Material.EMERALD_BLOCK;
	private int upgradecost = 0;
	
	public createAdvancedInventoryMenu_DoublePowerUPsUpgrade(Player player) {
		super(player, wantsdisplayname, material);
		
		this.upgradecost = Main.getInstance().getConfig().getInt("ragemode.shop.doublepowerupsprice");
	}
	
	@Override
	public void createAdvancedItem() {
		super.chooseitemmeta.setDisplayName(Strings.shop_item_doublepowerup_name);
		super.chooseitemlore.add(Strings.shop_item_doublepowerup_usage);
		
		super.bookmetalore.add(Strings.inventory_invmore_description_description + Strings.shop_item_doublepowerup_advanced_usage_0);
		
		if(SQLCoins.getDoublePowerUP(player.getUniqueId().toString())) {
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
