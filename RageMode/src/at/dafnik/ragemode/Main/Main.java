package at.dafnik.ragemode.Main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import at.dafnik.ragemode.API.Holograms;
import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Commands.Coins;
import at.dafnik.ragemode.Commands.LobbyCommands;
import at.dafnik.ragemode.Commands.Mapset;
import at.dafnik.ragemode.Commands.RoundStart;
import at.dafnik.ragemode.Commands.Stats;
import at.dafnik.ragemode.Commands.Teleport;
import at.dafnik.ragemode.Items.Compass;
import at.dafnik.ragemode.Items.HookSwitcher;
import at.dafnik.ragemode.Items.SparcleSwitcher;
import at.dafnik.ragemode.Listeners.AsyncPlayerChatListener;
import at.dafnik.ragemode.Listeners.BlockBedListener;
import at.dafnik.ragemode.Listeners.BlockListener;
import at.dafnik.ragemode.Listeners.FoodWeatherChangeListener;
import at.dafnik.ragemode.Listeners.InventoryItemListener;
import at.dafnik.ragemode.Listeners.PlayerJoinListener;
import at.dafnik.ragemode.Listeners.PlayerQuitListener;
import at.dafnik.ragemode.Listeners.Listeners;
import at.dafnik.ragemode.Listeners.PlayerDeathListener;
import at.dafnik.ragemode.Listeners.PlayerRespawnListener;
import at.dafnik.ragemode.Listeners.PlayerRide;
import at.dafnik.ragemode.MySQL.ConfigStandart;
import at.dafnik.ragemode.MySQL.MySQL;
import at.dafnik.ragemode.MySQL.Ranking;
import at.dafnik.ragemode.PowerUPs.C4Detonator;
import at.dafnik.ragemode.PowerUPs.DoubleJump;
import at.dafnik.ragemode.PowerUPs.Fly;
import at.dafnik.ragemode.PowerUPs.Healer;
import at.dafnik.ragemode.PowerUPs.Mine;
import at.dafnik.ragemode.PowerUPs.PowerUPItemListener;
import at.dafnik.ragemode.Shop.Shop;
import at.dafnik.ragemode.Shop.Pages.AdvancedShopPage_BowPowerUpgrade;
import at.dafnik.ragemode.Shop.Pages.AdvancedShopPage_DoublePowerUPsUpgrade;
import at.dafnik.ragemode.Shop.Pages.AdvancedShopPage_KnockbackAbilityUpgrade;
import at.dafnik.ragemode.Shop.Pages.AdvancedShopPage_SpectralArrowUpgrade;
import at.dafnik.ragemode.Shop.Pages.AdvancedShopPage_SpeedUpgrade;
import at.dafnik.ragemode.TabCompleter.TabCompleter_CoinsAdmin;
import at.dafnik.ragemode.TabCompleter.TabCompleter_RM;
import at.dafnik.ragemode.TabCompleter.TabCompleter_TpMap;
import at.dafnik.ragemode.Tasks.Lobby;
import at.dafnik.ragemode.Weapons.AxeEvent;
import at.dafnik.ragemode.Weapons.Bow;
import at.dafnik.ragemode.Weapons.Flash;
import at.dafnik.ragemode.Weapons.Grenade;
import at.dafnik.ragemode.Weapons.Knife;

public class Main extends JavaPlugin{
	
	//Register Lobbytask
	public Lobby lobbytasks;
	//Register Mapvote
	public Mapvote mapvote;
	
	//General Prefix
	public static String pre = "§f[§cRageMode§f] ";
	//Start Status
	public static Status status = Status.PRE_LOBBY;
	//MySQL
	public static MySQL mysql;
	//Main
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
	//want update check
	public static boolean wantUpdate;
	
	//------------------------------------------------------------------------
	
	public void onDisable() {
		if(status == Status.WARMUP || status == Status.INGAME || status == Status.WIN || status == Status.RESTART) {
			lobbytasks.wm.kt.stop();
			lobbytasks.wm.ct.stop();
			lobbytasks.wm.pu.stop();
		}
		
		for(Entity entities : Library.powerup_entity) entities.remove();
		
		for (Player players : Bukkit.getOnlinePlayers()) {
			for (Holograms holo : Library.powerup_list) holo.destroy(players);;		
			players.removeMetadata("killedWith", this);
			
			for(Team teams : Library.teams) teams.removeEntry(players.getName());
			Library.ingame.removeEntry(players.getName());
		}
		
		Library.bar.removeAll();
		
		if(Library.villager != null) Library.villager.stop();
		
		getServer().getConsoleSender().sendMessage(pre + "§cStopped§8!");
	}
	
	//Plugin start
	public void onEnable(){
		instance = this;
		
		//Load Config
		loadConfig();
		
		//Register all Events and Commands
		registerListeners();
		createScoreboards();
		registerCommands();
				
		//Set Config Standart
		ConfigStandart.setStandart();
		
		//All planted Things will be removed.
		PlantedRemover();
		
		//Start Tasks
		lobbytasks = new Lobby();
		lobbytasks.lobbywplayers();
		
		//Open MapVote Methode
		mapvote = new Mapvote();
		mapvote.MapsToVoteAdd();
		mapvote.AllMapsAdd();
		mapvote.Mapvotenull();
			
		getServer().getConsoleSender().sendMessage(pre + "§aStarted§8! §fThe most important things started §awell§8!");
		
		//Check on Bungee
		if(isBungee) getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		//Check on MySQL
		if(isMySQL) {	
			//Connect MySQL
			ConnectMySQL();
			
			//MySQL Ranking
			Ranking.set();
		}
		
		//Check on update!
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
		
		try {
			mysql = new MySQL(host, database, username, password);
			mysql.update("CREATE TABLE IF NOT EXISTS Stats(UUID varchar(64), KILLS int, DEATHS int, PLAYEDGAMES int, WONGAMES int, POINTS int, RESETS int, BOWKILLS int, AXTKILLS int, KNIFEKILLS int, SUICIDES int);");
			mysql.update("CREATE TABLE IF NOT EXISTS Coins(UUID varchar(64), COINS int, SPEEDUPGRADE int, BOWPOWERUPGRADE int, KNOCKBACKUPGRADE int, SPECTRALARROWUPGRADE int);");
		
		} catch (Exception ex) {			
			Main.isMySQL = false;
			Main.isShop = false;
			
			if(Library.villager != null) {
				Library.villager.stop();
				Library.villager = null;
			}
			
			System.out.println(Strings.log_pre + "INFO: MySQL disabled!");
		}
	}
    
	private void makeUpdate() {
		if(getConfig().getString("ragemode.settings.version") == null) getConfig().set("ragemode.settings.version", "NOP_LEL");
			
		if(!(getConfig().getString("ragemode.settings.version").equalsIgnoreCase(getDescription().getVersion()))) {
			getConfig().set("ragemode.settings.version", "1.4.1");
			getConfig().set("ragemode.settings.updatecheck", true);
				
			if(isMySQL) mysql.update("ALTER TABLE Coins ADD DOUBLEPOWERUPGRADE int DEFAULT 0");
			
			getConfig().set("ragemode.shop.doublepowerupsprice", Integer.valueOf(20000));
			
			saveConfig();
			System.out.println(Strings.ragemode_updated_succesful);	
		}
		
		if(Main.wantUpdate) new Updater().start();
	}
	
	private void createScoreboards() {
		//Register Scorebords
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Library.scoreboard = manager.getNewScoreboard();

		Team admin = Library.scoreboard.registerNewTeam("players_admin");
		admin.setPrefix("§4");

		Team moderator = Library.scoreboard.registerNewTeam("players_mod");
		moderator.setPrefix("§c");

		Team youtuber = Library.scoreboard.registerNewTeam("players_yout");
		youtuber.setPrefix("§5");

		Team premium = Library.scoreboard.registerNewTeam("players_pre");
		premium.setPrefix("§6");

		Team user = Library.scoreboard.registerNewTeam("players_user");
		user.setPrefix("§a");

		Library.ingame = Library.scoreboard.registerNewTeam("players_ingame");
		Library.ingame.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);

		Library.teams.add(admin);
		Library.teams.add(moderator);
		Library.teams.add(youtuber);
		Library.teams.add(premium);
		Library.teams.add(user);
	}

	//Register All Events
	private void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		
		//Items
		pm.registerEvents(new Compass(), this);
		
		//PowerUPs
		pm.registerEvents(new PowerUPItemListener(), this);
		pm.registerEvents(new Mine(), this);
		pm.registerEvents(new Healer(), this);
		pm.registerEvents(new DoubleJump(), this);
		pm.registerEvents(new Flash(), this);
		pm.registerEvents(new Fly(), this);
		pm.registerEvents(new C4Detonator(), this);
		
		//Weapons
		pm.registerEvents(new AxeEvent(), this);
		pm.registerEvents(new Knife(), this);
		pm.registerEvents(new Grenade(), this);
		pm.registerEvents(new Bow(), this);
		pm.registerEvents(new SparcleSwitcher(), this);
		pm.registerEvents(new HookSwitcher(), this);
		
		//Events - Combat
		pm.registerEvents(new PlayerDeathListener(), this);
		pm.registerEvents(new PlayerRespawnListener(), this);
		
		//Events - Listeners
		pm.registerEvents(new PlayerJoinListener(), this);
		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(new PlayerQuitListener(), this);
		pm.registerEvents(new Listeners(), this);
		pm.registerEvents(new AsyncPlayerChatListener(), this);
		pm.registerEvents(new FoodWeatherChangeListener(), this);
		pm.registerEvents(new InventoryItemListener(), this);
		pm.registerEvents(new BlockBedListener(), this);
		pm.registerEvents(new PlayerRide(), this);
		
		//Events - Shop
		pm.registerEvents(new Shop(), this);
		new AdvancedShopPage_SpeedUpgrade();
		new AdvancedShopPage_KnockbackAbilityUpgrade();
		new AdvancedShopPage_SpectralArrowUpgrade();
		new AdvancedShopPage_BowPowerUpgrade();
		new AdvancedShopPage_DoublePowerUPsUpgrade();
	}
	
	//Register all Commands
	private void registerCommands(){
		this.getCommand("rm").setExecutor(new Mapset());
		this.getCommand("rm").setTabCompleter(new TabCompleter_RM());
		
		this.getCommand("coins").setExecutor(new Coins());
		this.getCommand("coinsadmin").setExecutor(new Coins());
		this.getCommand("coinsadmin").setTabCompleter(new TabCompleter_CoinsAdmin());
		
		this.getCommand("stats").setExecutor(new Stats());
		this.getCommand("statsadmin").setExecutor(new Stats());
		this.getCommand("statsreset").setExecutor(new Stats());
		
		this.getCommand("forcestart").setExecutor(new RoundStart());
		this.getCommand("latestart").setExecutor(new RoundStart());
		this.getCommand("test").setExecutor(new RoundStart());
		
		this.getCommand("tpmap").setExecutor(new Teleport());
		this.getCommand("tpmap").setTabCompleter(new TabCompleter_TpMap());
		this.getCommand("tplobby").setExecutor(new Teleport());
		
		this.getCommand("hub").setExecutor(new LobbyCommands());
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
}