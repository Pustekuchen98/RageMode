package at.dafnik.ragemode.Threads;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.dafnik.ragemode.API.Strings;
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

	@Deprecated
	public void stop(){
		this.running = false;
		this.thread.stop();
	}

	@Override
	public void run() {
		while(running){	
			if(Main.status == Status.INGAME) {
				try {
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
						@Override
						public void run() {
							Bukkit.getOnlinePlayers().stream().filter(player -> !Library.spectatorlist.contains(player)).filter(player -> !Library.powerup_speedeffect.contains(player)).forEach(player -> {
								if (player.getInventory().getItemInMainHand().getType() == Material.IRON_SPADE) {
									if (Main.isMySQL && Main.isShop) {
										if (SQLCoins.getSpeedUpgrade(player.getUniqueId().toString()))
											player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 1));
										else player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 0));
									} else player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 0));

								} else player.removePotionEffect(PotionEffectType.SPEED);
							});
						}
					}, 1);
				} catch (Exception ex) {
					System.out.println(Strings.log_pre + "Knife" + Strings.error_thread_exception);
				}
			}

			try{
				Thread.sleep(20);
			}catch (InterruptedException e){
				this.stop();
			}
		}
	}
}
