package at.dafnik.ragemode.Shop.Creator;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.MySQL.SQLCoins;

public class createAdvancedInventoryMenu_KnockbackAbilityUpgrade extends createAdvancedInventoryMenuBasic{
	
	public static String wantsdisplayname = Strings.inventory_inv_knockbackupgrade;
	public static Material material = Material.BLAZE_POWDER;
	private int upgradecost = 0;
	
	public createAdvancedInventoryMenu_KnockbackAbilityUpgrade(Player player) {
		super(player, wantsdisplayname, material);
		
		this.upgradecost = Main.getInstance().getConfig().getInt("ragemode.shop.knifeknockbackupgradeprice");
	}
	
	@Override
	public void createAdvancedItem() {
		super.chooseitemmeta.setDisplayName(Strings.inventory_inv_knockbackupgrade);
		super.chooseitemlore.add(Strings.inventory_inv_knockbackupgrade_description);
		
		super.bookmetalore.add(Strings.inventory_invmore_description_description + Strings.inventory_invmore_description_blazepowder);
		super.bookmetalore.add(Strings.inventory_invmore_description_blazepowder_2);
		
		if(SQLCoins.getKnockbackUpgrade(player.getUniqueId().toString())) {
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
