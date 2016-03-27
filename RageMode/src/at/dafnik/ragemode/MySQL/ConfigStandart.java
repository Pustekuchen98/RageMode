package at.dafnik.ragemode.MySQL;

import at.dafnik.ragemode.Main.Main;

public class ConfigStandart {
	
	//Standart Config set
	public static void setStandart() {
		String test = "test";
		
		if(!Main.getInstance().getConfig().getBoolean("ragemode.settings.safe")) {
			Main.getInstance().getConfig().set("ragemode.settings.safe", true);				
			
			//Version
			Main.getInstance().getConfig().set("ragemode.settings.version", Main.getInstance().getDescription().getVersion());
			
			//Update check
			Main.getInstance().getConfig().set("ragemode.settings.updatecheck", true);
			
			//MySQL
			Main.getInstance().getConfig().set("ragemode.settings.mysql.host", test);
			Main.getInstance().getConfig().set("ragemode.settings.mysql.database", test);
			Main.getInstance().getConfig().set("ragemode.settings.mysql.username", test);
			Main.getInstance().getConfig().set("ragemode.settings.mysql.password", test);
			Main.getInstance().getConfig().set("ragemode.settings.mysql.switch", false);
			
			//BungeeCord
			Main.getInstance().getConfig().set("ragemode.settings.bungee.switch", false);
			Main.getInstance().getConfig().set("ragemode.settings.bungee.lobbyserver", test);
			
			//Debuger
			Main.getInstance().getConfig().set("ragemode.settings.debug.switch", false);
			
			//Settings
			Main.getInstance().getConfig().set("ragemode.settings.neededplayers", Integer.valueOf(2));
			 
			Main.getInstance().getConfig().set("ragemode.settings.gametime", Integer.valueOf(360));
			
			//Shop
			Main.getInstance().getConfig().set("ragemode.shop.switch", false);
			Main.getInstance().getConfig().set("ragemode.shop.knifeupgradeprice", Integer.valueOf(20000));
			Main.getInstance().getConfig().set("ragemode.shop.bowpowerupgradeprice", Integer.valueOf(20000));
			Main.getInstance().getConfig().set("ragemode.shop.knifeknockbackupgradeprice", Integer.valueOf(20000));
			Main.getInstance().getConfig().set("ragemode.shop.spectralarrowupgradeprice", Integer.valueOf(20000));
			
			//To Kill
			Main.getInstance().getConfig().set("ragemode.points.bowkill", Integer.valueOf(30));
			Main.getInstance().getConfig().set("ragemode.points.knifekill", Integer.valueOf(25));
			Main.getInstance().getConfig().set("ragemode.points.knifedeath", Integer.valueOf(-20));
			Main.getInstance().getConfig().set("ragemode.points.suicide", Integer.valueOf(-30));
			Main.getInstance().getConfig().set("ragemode.points.combat_axekill", Integer.valueOf(25));
			Main.getInstance().getConfig().set("ragemode.points.grenadekill", Integer.valueOf(10));
			Main.getInstance().getConfig().set("ragemode.points.clay_morekill", Integer.valueOf(15));
			Main.getInstance().getConfig().set("ragemode.points.minekill", Integer.valueOf(15));
			Main.getInstance().getConfig().set("ragemode.points.killstreakpoints", Integer.valueOf(15));
			
			//Mapsettings
			Main.getInstance().getConfig().set("ragemode.ranking.rankingnumber", Integer.valueOf(0));
		
			Main.getInstance().getConfig().set("ragemode.mapnames.mapnamenumber", Integer.valueOf(0));
			
			Main.getInstance().getConfig().set("ragemode.lobbyspawn.world", test);
			Main.getInstance().getConfig().set("ragemode.lobbyspawn.x", Double.valueOf(0));
			Main.getInstance().getConfig().set("ragemode.lobbyspawn.y", Double.valueOf(0));
			Main.getInstance().getConfig().set("ragemode.lobbyspawn.z", Double.valueOf(0));
			Main.getInstance().getConfig().set("ragemode.lobbyspawn.yaw", Double.valueOf(0));
			Main.getInstance().getConfig().set("ragemode.lobbyspawn.pitch", Double.valueOf(0));
			
			Main.getInstance().getConfig().set("ragemode.hologram.world", test);
			Main.getInstance().getConfig().set("ragemode.hologram.x", Double.valueOf(0));
			Main.getInstance().getConfig().set("ragemode.hologram.y", Double.valueOf(0));
			Main.getInstance().getConfig().set("ragemode.hologram.z", Double.valueOf(0));	
			
			Main.getInstance().saveConfig();
		}
		
		System.out.println("[RageMode] Settings: -----------------------");
			
		//is Mysql or not
		if(Main.getInstance().getConfig().getBoolean("ragemode.settings.mysql.switch")) {
			Main.isMySQL = true;
				
			System.out.println("[RageMode] MySQL: ON");
		} else {
			Main.isMySQL = false;
				
			System.out.println("[RageMode] MySQL: OFF");
		}
					
		//Is Bungee true or false
		if(Main.getInstance().getConfig().getBoolean("ragemode.settings.bungee.switch")) {
			Main.isBungee = true;
				
			System.out.println("[RageMode] BungeeCord-Network-Support: ON");
		} else {
			Main.isBungee = false;
			
			System.out.println("[RageMode] BungeeCord-Network-Support: OFF");
		}
			
		//Is Bungee true or false
		if(Main.getInstance().getConfig().getBoolean("ragemode.settings.debug.switch")) {
			Main.isDebug = true;
						
			System.out.println("[RageMode] Debug-Modus: ON");
		} else {
			Main.isDebug = false;
						
			System.out.println("[RageMode] Debug-Modus: OFF");
		}
		
		//Is Bungee true or false
		if(Main.getInstance().getConfig().getBoolean("ragemode.shop.switch")) {
			Main.isShop = true;
								
			System.out.println("[RageMode] Shop-Modus: ON");
		} else {
			Main.isShop = false;
								
			System.out.println("[RageMode] Shop-Modus: OFF");
		}
		
		//Is Bungee true or false
		if(Main.getInstance().getConfig().getBoolean("ragemode.settings.updatecheck")) {
			Main.wantUpdate = true;
								
			System.out.println("[RageMode] Update-Check: ON");
		} else {
			Main.wantUpdate = false;
								
			System.out.println("[RageMode] Update-Check: OFF");
		}
		
		System.out.println("[RageMode] ---------------------------------");
	}
}
