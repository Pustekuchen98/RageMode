package at.dafnik.ragemode.Shop.Pages;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.MySQL.SQLCoins;
import net.md_5.bungee.api.ChatColor;

public class AdvancedShopPage_SpectralArrowUpgrade extends AdvancedShopPageBasic{

	private int upgradecost = 0;
	private static String pagename = "Spectral Arrow Upgrade";
	
	public AdvancedShopPage_SpectralArrowUpgrade() {
		super(pagename);
		
		this.upgradecost = Main.getInstance().getConfig().getInt("ragemode.shop.spectralarrowupgradeprice");
	}
	
	@Override
	public void BuyEvent(Player player, InventoryClickEvent event) {
		if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Spectral Arrow Upgrade")) {		
			if(SQLCoins.getSpectralArrowUpgrade(player.getUniqueId().toString())) {
				player.sendMessage(Strings.inventory_buy_already_buy);
			} else {
				if(SQLCoins.getCoins(player.getUniqueId().toString()) >= upgradecost) {
					SQLCoins.removeCoins(player.getUniqueId().toString(), upgradecost);
					SQLCoins.setSpectralArrowUpgrade(player.getUniqueId().toString(), true);
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
