package at.dafnik.ragemode.Shop.Pages;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.MySQL.SQLCoins;
import net.md_5.bungee.api.ChatColor;

public class AdvancedShopPage_SpeedUpgrade extends AdvancedShopPageBasic {
	
	private int upgradecost = 0;
	private static String pagename = "Speed Upgrade";
	
	public AdvancedShopPage_SpeedUpgrade() {
		super(pagename);
		
		this.upgradecost = Main.getInstance().getConfig().getInt("ragemode.shop.knifeupgradeprice");
	}

	@Override
	public void BuyEvent(Player player, InventoryClickEvent event) {
		if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase(super.pagename)) {			
			if(SQLCoins.getSpeedUpgrade(player.getUniqueId().toString())) {
				player.sendMessage(Strings.inventory_buy_already_buy);
			} else {
				if(SQLCoins.getCoins(player.getUniqueId().toString()) >= upgradecost) {
					SQLCoins.removeCoins(player.getUniqueId().toString(), upgradecost);
					SQLCoins.setSpeedUpgrade(player.getUniqueId().toString(), true);
					player.sendMessage(Strings.inventory_buy_succesfull);
					player.sendMessage(Strings.inventory_buy_new_coins + SQLCoins.getCoins(player.getUniqueId().toString()));
				} else {
					int need = upgradecost - SQLCoins.getCoins(player.getUniqueId().toString());
					player.sendMessage(Strings.inventory_buy_not_enough + need);
				}
			}
			
			player.closeInventory();
		}
	}
}
