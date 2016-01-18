package at.dafnik.ragemode.Weapons;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.MySQL.SQLCoins;

public class KnifeThread implements Runnable{
	private Thread thread;
	private boolean running;
	
	private Main plugin;
	
	public KnifeThread(Main main){
		this.thread = new Thread(this);
		
		this.plugin = main;
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
					 Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							if(!plugin.spectatorlist.contains(player)) {
								if(!plugin.powerupspeedeffect.contains(player)) {
									if(player.getInventory().getItemInHand().getType() == Material.IRON_SPADE) {
										if(SQLCoins.getSpeedUpgrade(player.getUniqueId().toString())) player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 1));
										else player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 0));
										
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
