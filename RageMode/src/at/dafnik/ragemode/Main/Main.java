package at.dafnik.ragemode.Main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import at.dafnik.ragemode.API.Holograms;
import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.API.TeleportAPI;
import at.dafnik.ragemode.Commands.Coins;
import at.dafnik.ragemode.Commands.LobbyCommands;
import at.dafnik.ragemode.Commands.Mapset;
import at.dafnik.ragemode.Commands.RoundStart;
import at.dafnik.ragemode.Commands.Stats;
import at.dafnik.ragemode.Commands.Teleport;
import at.dafnik.ragemode.Items.Compass;
import at.dafnik.ragemode.Listeners.AsyncPlayerChatListener;
import at.dafnik.ragemode.Listeners.BlockBedListener;
import at.dafnik.ragemode.Listeners.FoodWeatherChangeListener;
import at.dafnik.ragemode.Listeners.InventoryItemListener;
import at.dafnik.ragemode.Listeners.PlayerJoinListener;
import at.dafnik.ragemode.Listeners.PlayerQuitListener;
import at.dafnik.ragemode.Listeners.Listeners;
import at.dafnik.ragemode.Listeners.PlayerDeathListener;
import at.dafnik.ragemode.Listeners.PlayerRespawnListener;
import at.dafnik.ragemode.MySQL.ConfigStandart;
import at.dafnik.ragemode.MySQL.MySQL;
import at.dafnik.ragemode.MySQL.Ranking;
import at.dafnik.ragemode.PowerUPs.DoubleJump;
import at.dafnik.ragemode.PowerUPs.Flash;
import at.dafnik.ragemode.PowerUPs.Fly;
import at.dafnik.ragemode.PowerUPs.Healer;
import at.dafnik.ragemode.PowerUPs.Mine;
import at.dafnik.ragemode.PowerUPs.PowerUPItemListener;
import at.dafnik.ragemode.Shop.AdvancedShopPage_BowPowerUpgrade;
import at.dafnik.ragemode.Shop.AdvancedShopPage_KnockbackAbilityUpgrade;
import at.dafnik.ragemode.Shop.AdvancedShopPage_SpectralArrowUpgrade;
import at.dafnik.ragemode.Shop.AdvancedShopPage_SpeedUpgrade;
import at.dafnik.ragemode.Shop.Shop;
import at.dafnik.ragemode.Tasks.Lobby;
import at.dafnik.ragemode.Weapons.AxeEvent;
import at.dafnik.ragemode.Weapons.Bow;
import at.dafnik.ragemode.Weapons.Grenade;
import at.dafnik.ragemode.Weapons.Knife;

public class Main extends JavaPlugin{
	
	//Register Lobbytask
	public Lobby lobbytasks;
	//Register Mapvote
	public Mapvote mapvote;
	
	//General Prefix
	public static String pre = "§7[§bRageMode§7] ";
	//Start Status
	public static Status status = Status.PRE_LOBBY;
	//MySQL
	public static MySQL mysql;
	
	public static Main instance = null;
	
	//----------------------------------------------------------------------
	//Is MySQL On
	public static boolean isMySQL;
	//Is Bungeecord
	public static boolean isBungee;
	//Is Debug
	public static boolean isDebug;
	//Is Shop
	public static boolean isShop;
	
	//------------------------------------------------------------------------
	
	public void onDisable() {
		if(status == Status.WARMUP || status == Status.INGAME || status == Status.WIN || status == Status.RESTART) {
			lobbytasks.wm.pu.stop();
			lobbytasks.wm.kt.stop();
			lobbytasks.wm.ct.stop();
		}
		
		for(Entity entities : Library.powerup_entity) entities.remove();
		
		for (Player players : Bukkit.getOnlinePlayers()) {
			for (Holograms holo : Library.powerup_list) holo.destroy(players);;		
			players.removeMetadata("killedWith", this);
		}
		
		Library.bar.removeAll();
		
		if(Library.villager != null) {
			Library.villager.remove();
			Library.villager = null;
			
			if(Bukkit.getOnlinePlayers().size() == 0) {
				Location loc = TeleportAPI.getVillagerShopLocation();
				loc.getWorld().getBlockAt(loc).setType(Material.LAVA);
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					@Override
					public void run() {			
						loc.getWorld().getBlockAt(loc).setType(Material.AIR);
					}
				}, 30);
			}
		}
		
		getServer().getConsoleSender().sendMessage("§7[§bRageMode§7] §cStopped§8!");
	}
	
	//Plugin start
	public void onEnable(){
		instance = this;
		
		//Load Config
		loadConfig();
		
		//Register all Events and Commands
		registerListeners();
		registerCommands();
		
		//Set Config Standart
		ConfigStandart.setStandart();
		
		//All planted Things will be removed.
		PlantedRemover();
		
		//Start Tasks
		lobbytasks = new Lobby();
		lobbytasks.lobbywplayers();
		
		getServer().getConsoleSender().sendMessage("§7[§bRageMode§7] §aStarted§8! §fThe most important things started §awell§8!");
		
		//Open MapVote Methode
		mapvote = new Mapvote(this);
		mapvote.MapsToVoteAdd();
		mapvote.AllMapsAdd();
		mapvote.Mapvotenull();
		
		//Check on Bungee
		if(isBungee) getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		//Check on MySQL
		if(isMySQL) {	
			//Connect MySQL
			ConnectMySQL();
			
			//MySQL Ranking
			Ranking.set();
		}
		
		makeUpdate();
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	//Connect to MySQL
	private void ConnectMySQL() {
		String host = getConfig().getString("ragemode.settings.mysql.host");
		String database = getConfig().getString("ragemode.settings.mysql.database");
		String username = getConfig().getString("ragemode.settings.mysql.username");
		String password = getConfig().getString("ragemode.settings.mysql.password");
		
		mysql = new MySQL(host, database, username, password);
		mysql.update("CREATE TABLE IF NOT EXISTS Stats(UUID varchar(64), KILLS int, DEATHS int, PLAYEDGAMES int, WONGAMES int, POINTS int, RESETS int, BOWKILLS int, AXTKILLS int, KNIFEKILLS int, SUICIDES int);");
		mysql.update("CREATE TABLE IF NOT EXISTS Coins(UUID varchar(64), COINS int, SPEEDUPGRADE int, BOWPOWERUPGRADE int, KNOCKBACKUPGRADE int, SPECTRALARROWUPGRADE int);");
	}
    
	private void makeUpdate() {
		if(getConfig().getString("ragemode.settings.version") == null) getConfig().set("ragemode.settings.version", "1.3.3d");
			
		if(!(getConfig().getString("ragemode.settings.version").equalsIgnoreCase(getDescription().getVersion()))) {
			getConfig().set("ragemode.settings.version", "1.4.0");
				
			if(isMySQL) mysql.update("ALTER TABLE Coins ADD SPECTRALARROWUPGRADE int DEFAULT 0");
			
			getConfig().set("ragemode.shop.spectralarrowupgradeprice", Integer.valueOf(20000));
			
			saveConfig();
			System.out.println(Strings.ragemode_updated_mysql_succesful);
			
		}
	}

	//Register All Events
	private void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		
		//Items
		pm.registerEvents(new Compass(), this);
		
		//PowerUP's
		pm.registerEvents(new PowerUPItemListener(), this);
		pm.registerEvents(new Mine(), this);
		pm.registerEvents(new Healer(), this);
		pm.registerEvents(new DoubleJump(), this);
		pm.registerEvents(new Flash(), this);
		pm.registerEvents(new Fly(), this);
		
		//Weapons
		pm.registerEvents(new AxeEvent(), this);
		pm.registerEvents(new Knife(), this);
		pm.registerEvents(new Grenade(), this);
		pm.registerEvents(new Bow(), this);
		
		//Events - Combat
		pm.registerEvents(new PlayerDeathListener(), this);
		pm.registerEvents(new PlayerRespawnListener(), this);
		
		//Events - Listeners
		pm.registerEvents(new PlayerJoinListener(), this);
		pm.registerEvents(new PlayerQuitListener(), this);
		pm.registerEvents(new Listeners(), this);
		pm.registerEvents(new AsyncPlayerChatListener(), this);
		pm.registerEvents(new FoodWeatherChangeListener(), this);
		pm.registerEvents(new InventoryItemListener(), this);
		pm.registerEvents(new BlockBedListener(), this);
		
		//Events - Shop
		pm.registerEvents(new Shop(), this);
		new AdvancedShopPage_SpeedUpgrade(this);
		new AdvancedShopPage_KnockbackAbilityUpgrade(this);
		new AdvancedShopPage_SpectralArrowUpgrade(this);
		new AdvancedShopPage_BowPowerUpgrade(this);
		
		//Map classes
		new Mapset(this);
		new Mapvote(this);
	}
	
	//Register all Commands
	private void registerCommands(){
		this.getCommand("rm").setExecutor(new Mapset(this));
		
		this.getCommand("coins").setExecutor(new Coins());
		this.getCommand("coinsadmin").setExecutor(new Coins());
		
		this.getCommand("stats").setExecutor(new Stats());
		this.getCommand("statsadmin").setExecutor(new Stats());
		this.getCommand("statsreset").setExecutor(new Stats());
		
		this.getCommand("forcestart").setExecutor(new RoundStart(this));
		this.getCommand("latestart").setExecutor(new RoundStart(this));
		this.getCommand("test").setExecutor(new RoundStart(this));
		
		this.getCommand("tpmap").setExecutor(new Teleport());
		this.getCommand("tplobby").setExecutor(new Teleport());
		
		this.getCommand("hub").setExecutor(new LobbyCommands(this));
	}
	
	//Load Config
	public void loadConfig(){
		FileConfiguration cfg = getConfig();
		cfg.options().copyDefaults(true);
		
		saveConfig();
	}
	
	//Status
	public enum Status{
		PRE_LOBBY, LOBBY, WARMUP, INGAME, WIN, RESTART;
	}
	
	private void PlantedRemover() {
		int numbertoset = getConfig().getInt("ragemode.placedblocks.number");
		
		for(int i = 0; i < numbertoset; i++) {
			String w = getConfig().getString("ragemode.placedblocks." + i + ".world");
			int x = getConfig().getInt("ragemode.placedblocks." + i + ".x");
			int y = getConfig().getInt("ragemode.placedblocks." + i + ".y");
			int z = getConfig().getInt("ragemode.placedblocks." + i + ".z");
			
			Location loc = new Location(Bukkit.getWorld(w), x, y, z);
			loc.getBlock().setType(Material.AIR);
		}
		
		getConfig().set("ragemode.placedblocks", null);
		saveConfig();
	}
	
	public Villager VillagerShopSpawner() {
		Location loc = TeleportAPI.getVillagerShopLocation();
		
		if(loc != null) {
			Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
			Library.villagerholo = new Holograms(new Location(loc.getWorld(), loc.getX(), loc.getY()+1.7, loc.getZ()), "§6Shop");
			villager.setAgeLock(true);
			villager.setCanPickupItems(false);
			new VillagerThread(villager, loc).start();
			return villager;
		} else {
			System.out.println(Strings.error_not_existing_villagerspawn);
			return null;
		}
	}
}