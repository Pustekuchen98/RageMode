package at.dafnik.ragemode.Weapons;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.MySQL.SQLCoins;

public class KnifeThread implements Runnable{
	private Thread thread;
	private boolean running;
	
	public KnifeThread(){
		this.thread = new Thread(this);
	}
	
	public void start(){
		this.running = true;
		if(running) {
			this.thread.start();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void stop(){
		this.running = false;
		this.thread.stop();
	}

	@Override
	public void run() {
		while(running){
			
			if(Main.status == Status.INGAME) {
				for(Player player : Bukkit.getOnlinePlayers()) {
					 Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
						@Override
						public void run() {
							if(!Library.spectatorlist.contains(player)) {
								if(!Library.powerup_speedeffect.contains(player)) {
									if(player.getInventory().getItemInMainHand().getType() == Material.IRON_SPADE) {
										if(Main.isMySQL && Main.isShop) {
											if(SQLCoins.getSpeedUpgrade(player.getUniqueId().toString())) player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 1));
											else player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 0));
										} else player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 0));
									} else {
										player.removePotionEffect(PotionEffectType.SPEED);
									}
								}
							}
						}
					}, 1);
				}
			}

			try{
				Thread.sleep(20);
			}catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}
