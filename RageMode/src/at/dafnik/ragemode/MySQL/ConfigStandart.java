package at.dafnik.ragemode.MySQL;

import at.dafnik.ragemode.Main.Main;

public class ConfigStandart {
	
	private Main plugin;
	
	public ConfigStandart(Main main) {
		this.plugin = main;
	}
	
	//Standart Config set
	public void setStandart() {
		String test = "test";
		
		if(!plugin.getConfig().getBoolean("ragemode.settings.safe")) {
			plugin.getConfig().set("ragemode.settings.safe", true);
			
			//MySQL
			plugin.getConfig().set("ragemode.settings.mysql.host", test);
			plugin.getConfig().set("ragemode.settings.mysql.database", test);
			plugin.getConfig().set("ragemode.settings.mysql.username", test);
			plugin.getConfig().set("ragemode.settings.mysql.password", test);
			plugin.getConfig().set("ragemode.settings.mysql.switch", false);
			
			//BungeeCord
			plugin.getConfig().set("ragemode.settings.bungee.switch", false);
			plugin.getConfig().set("ragemode.settings.bungee.lobbyserver", test);
			
			//Debuger
			plugin.getConfig().set("ragemode.settings.debug.switch", false);
			
			//Settings
			plugin.getConfig().set("ragemode.settings.neededplayers", Integer.valueOf(2));
			 
			plugin.getConfig().set("ragemode.settings.gametime", Integer.valueOf(360));
			
			//Shop
			plugin.getConfig().set("ragemode.shop.switch", false);
			plugin.getConfig().set("ragemode.shop.knifeupgradeprice", Integer.valueOf(20000));
			plugin.getConfig().set("ragemode.shop.bowpowerupgradeprice", Integer.valueOf(20000));
			plugin.getConfig().set("ragemode.shop.knifeknockbackupgradeprice", Integer.valueOf(20000));
			plugin.getConfig().set("ragemode.shop.spectralarrowupgradeprice", Integer.valueOf(20000));
			
			//To Kill
			plugin.getConfig().set("ragemode.points.bowkill", Integer.valueOf(30));
			plugin.getConfig().set("ragemode.points.knifekill", Integer.valueOf(25));
			plugin.getConfig().set("ragemode.points.knifedeath", Integer.valueOf(-20));
			plugin.getConfig().set("ragemode.points.suicide", Integer.valueOf(-30));
			plugin.getConfig().set("ragemode.points.combat_axekill", Integer.valueOf(25));
			plugin.getConfig().set("ragemode.points.grenadekill", Integer.valueOf(10));
			plugin.getConfig().set("ragemode.points.clay_morekill", Integer.valueOf(15));
			plugin.getConfig().set("ragemode.points.minekill", Integer.valueOf(15));
			plugin.getConfig().set("ragemode.points.killstreakpoints", Integer.valueOf(15));
			
			//Mapsettings
			plugin.getConfig().set("ragemode.ranking.rankingnumber", Integer.valueOf(0));
			
			plugin.getConfig().set("ragemode.mapnames.mapnamenumber", Integer.valueOf(0));
			
			plugin.getConfig().set("ragemode.lobbyspawn.world", test);
			plugin.getConfig().set("ragemode.lobbyspawn.x", Double.valueOf(0));
			plugin.getConfig().set("ragemode.lobbyspawn.y", Double.valueOf(0));
			plugin.getConfig().set("ragemode.lobbyspawn.z", Double.valueOf(0));
			plugin.getConfig().set("ragemode.lobbyspawn.yaw", Double.valueOf(0));
			plugin.getConfig().set("ragemode.lobbyspawn.pitch", Double.valueOf(0));
			
			plugin.getConfig().set("ragemode.hologram.world", test);
			plugin.getConfig().set("ragemode.hologram.x", Double.valueOf(0));
			plugin.getConfig().set("ragemode.hologram.y", Double.valueOf(0));
			plugin.getConfig().set("ragemode.hologram.z", Double.valueOf(0));
			
			plugin.saveConfig();
		}
		
		System.out.println("[RageMode]Settings:");
			
		//is Mysql or not
		if(plugin.getConfig().getBoolean("ragemode.settings.mysql.switch")) {
			Main.isMySQL = true;
				
			System.out.println("[RageMode]MySQL: ON");
		} else {
			Main.isMySQL = false;
				
			System.out.println("[RageMode]MySQL: OFF");
		}
			
			
		//Is Bungee true or false
		if(plugin.getConfig().getBoolean("ragemode.settings.bungee.switch")) {
			Main.isBungee = true;
				
			System.out.println("[RageMode]BungeeCord-Network-Support: ON");
		} else {
			Main.isBungee = false;
			
			System.out.println("[RageMode]BungeeCord-Network-Support: OFF");
		}
			
		//Is Bungee true or false
		if(plugin.getConfig().getBoolean("ragemode.settings.debug.switch")) {
			Main.isDebug = true;
						
			System.out.println("[RageMode]Debug-Modus: ON");
		} else {
			Main.isDebug = false;
						
			System.out.println("[RageMode]Debug-Modus: OFF");
		}
		
		//Is Bungee true or false
		if(plugin.getConfig().getBoolean("ragemode.shop.switch")) {
			Main.isShop = true;
								
			System.out.println("[RageMode]Shop-Modus: ON");
		} else {
			Main.isShop = false;
								
			System.out.println("[RageMode]Shop-Modus: OFF");
		}
	}
}
