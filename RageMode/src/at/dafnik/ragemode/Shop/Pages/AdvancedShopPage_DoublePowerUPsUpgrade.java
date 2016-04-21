package at.dafnik.ragemode.Shop.Pages;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.MySQL.SQLCoins;
import net.md_5.bungee.api.ChatColor;

public class AdvancedShopPage_DoublePowerUPsUpgrade extends AdvancedShopPageBasic{
	
	private int upgradecost = 0;
	private static String pagename = "Double PowerUPs Upgrade";
	
	public AdvancedShopPage_DoublePowerUPsUpgrade() {
		super(pagename);
		
		this.upgradecost = Main.getInstance().getConfig().getInt("ragemode.shop.doublepowerupsprice");
	}
	
	@Override
	public void BuyEvent(Player player, InventoryClickEvent event) {
		if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Double PowerUPs Upgrade")) {
			if(SQLCoins.getDoublePowerUP(player.getUniqueId().toString())) {
				player.sendMessage(Strings.inventory_buy_already_buy);
			} else {
				if(SQLCoins.getCoins(player.getUniqueId().toString()) >= upgradecost) {
					SQLCoins.removeCoins(player.getUniqueId().toString(), upgradecost);
					SQLCoins.setDoublePowerUP(player.getUniqueId().toString(), true);
					player.sendMessage(Strings.inventory_buy_successful);
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
