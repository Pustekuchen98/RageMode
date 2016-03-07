package at.dafnik.ragemode.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
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
	
	//Villager
	public Villager villager;
	//Villager Holo
	public Holograms villagerholo;
	
	//BossBar
	public BossBar bar = Bukkit.getServer().createBossBar("�6You �3are �cplaying �bRageMode", BarColor.BLUE, BarStyle.SEGMENTED_6, BarFlag.CREATE_FOG);
	
	//--------------------------------------------------------------------
	
	//General Prefix
	public static String pre = "�7[�bRageMode�7] ";
	//Start Status
	public static Status status = Status.PRE_LOBBY;
	//MySQL
	public static MySQL mysql;
	
	//--------------------------------------------------------------------
	//Player List which voted
	public List<String> voted = new ArrayList<>();
	//Maps with votes
	public HashMap<String, Integer> votes = new HashMap<>();
	//Map list
	public List <String> maps = new ArrayList<>();
	//Map to vote
	public List <String> mapstovote = new ArrayList<>();
	//Voted Map
	public String votedmap;
	
	//----------------------------------------------------------------------
	//Player Ingamelist
	public List<Player> ingameplayer = new ArrayList<Player>();
	
	//Player Spectator
	public List<Player> spectatorlist = new ArrayList<Player>();
	
	//Respawnschutz
	public List<Player> respawnsafe = new ArrayList<>();
	
	//Player Win System
	public HashMap<Player, Integer> playerpoints = new HashMap<>();
	
	//Allowed to build
	public List<Player> builder = new ArrayList<>();
	
	//Planted Things
	public List<Location> planted = new ArrayList<>();
	
	//PowerUP
	public List<Entity> powerup_entity = new ArrayList<>();
	public HashMap<Integer, Holograms> powerup_hashmap = new HashMap<>();
	public List<Holograms> powerup_list = new ArrayList<>();
	public static Integer powerup_integer = 0;
	public List<Player> powerup_speedeffect = new ArrayList<>();
	public List<Player> powerup_doublejump = new ArrayList<>();
	public List<Player> powerup_flyparticle = new ArrayList<Player>();
	
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
		
		for(Entity entities : powerup_entity) entities.remove();
		
		for (Player players : Bukkit.getOnlinePlayers()) {
			for (Holograms holo : powerup_list) holo.destroy(players);;		
			players.removeMetadata("killedWith", this);
		}
		
		bar.removeAll();
		
		if(villager != null) {
			villager.remove();
			villager = null;
			
			if(Bukkit.getOnlinePlayers().size() == 0) {
				Location loc = new TeleportAPI(this).getVillagerShopLocation();
				loc.getWorld().getBlockAt(loc).setType(Material.LAVA);
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
					@Override
					public void run() {			
						loc.getWorld().getBlockAt(loc).setType(Material.AIR);
					}
				}, 30);
			}
		}
		
		getServer().getConsoleSender().sendMessage("�7[�bRageMode�7] �cStopped�8!");
	}
	
	//Plugin start
	public void onEnable(){
		//Load Config
		loadConfig();
		
		//Set Config Standart
		new ConfigStandart(this).setStandart();
		
		//Register all Events and Commands
		registerListeners();
		registerCommands();
		
		//All planted Things will be removed.
		PlantedRemover();
		
		//Start Tasks
		lobbytasks = new Lobby(this);
		lobbytasks.lobbywplayers();
		
		getServer().getConsoleSender().sendMessage("�7[�bRageMode�7] �aStarted�8! �fThe most important things started �awell�8!");
		
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
			Ranking ranking = new Ranking(this);
			ranking.set();
		}
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
    
	//Register All Events
	private void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		
		//Items
		pm.registerEvents(new Compass(this), this);
		
		//PowerUP's
		pm.registerEvents(new PowerUPItemListener(this), this);
		pm.registerEvents(new Mine(this), this);
		pm.registerEvents(new Healer(this), this);
		pm.registerEvents(new DoubleJump(this), this);
		pm.registerEvents(new Flash(this), this);
		pm.registerEvents(new Fly(this), this);
		
		//Weapons
		pm.registerEvents(new AxeEvent(this), this);
		pm.registerEvents(new Knife(this), this);
		pm.registerEvents(new Grenade(this), this);
		pm.registerEvents(new Bow(this), this);
		
		//Events - Combat
		pm.registerEvents(new PlayerDeathListener(this), this);
		pm.registerEvents(new PlayerRespawnListener(this), this);
		
		//Events - Listeners
		pm.registerEvents(new PlayerJoinListener(this), this);
		pm.registerEvents(new PlayerQuitListener(this), this);
		pm.registerEvents(new Listeners(this), this);
		pm.registerEvents(new AsyncPlayerChatListener(this), this);
		pm.registerEvents(new FoodWeatherChangeListener(), this);
		pm.registerEvents(new InventoryItemListener(this), this);
		pm.registerEvents(new BlockBedListener(this), this);
		
		//Events - Shop
		pm.registerEvents(new Shop(this), this);
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
		
		this.getCommand("tpmap").setExecutor(new Teleport(this));
		this.getCommand("tplobby").setExecutor(new Teleport(this));
		
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
		Location loc = new TeleportAPI(this).getVillagerShopLocation();
		
		if(loc != null) {
			Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
			villagerholo = new Holograms(new Location(loc.getWorld(), loc.getX(), loc.getY()+1.7, loc.getZ()), "�6Shop");
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