package at.dafnik.ragemode.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import at.dafnik.ragemode.API.Holograms;
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
import at.dafnik.ragemode.PowerUPs.Healer;
import at.dafnik.ragemode.PowerUPs.Mine;
import at.dafnik.ragemode.PowerUPs.PowerUPItemListener;
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
	//Set Config Standarts
	public ConfigStandart configstandart;
	//Villager
	public Villager villager = null;
	
	//--------------------------------------------------------------------
	
	//General Prefix
	public static String pre = "�7[�bRageMode�7] ";
	//Start Status
	public static Status status = Status.PRE_LOBBY;
	//MySQL
	public static MySQL mysql;
	//Plugin
	public static Plugin plugin;
	
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
	
	//---------------------------------------------------------------------
	//Combat Axe
	public List<Player> playercombataxelist = new ArrayList<>();
	
	//Grenade
	public List<Player> playergrenadelist = new ArrayList<>();
	
	//Clay More
	public List<Player> playerclaymorelist = new ArrayList<>();
	
	//Mine
	public List<Player> playerminelist = new ArrayList<>();
	
	//Knife
	public List<Player> playerknifelist = new ArrayList<>();
	
	//Bow
	public List<Player> playerbowlist = new ArrayList<>();
	
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
	public List<Entity> powerupentity = new ArrayList<>();
	public HashMap<Integer, Holograms> poweruphashmap = new HashMap<>();
	public static Integer powerupinteger = 0;
	public List<Player> powerupspeedeffect = new ArrayList<>();
	
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
			lobbytasks.wm.saver.kt.stop();
			lobbytasks.wm.saver.ct.stop();
			lobbytasks.wm.saver.stop();
		}
		
		for(Entity entities : powerupentity) entities.remove();
		for(int i = 0; i < poweruphashmap.size(); i++){
			Holograms holo = poweruphashmap.get(i);
			if(holo != null) {
				for(Player players : Bukkit.getOnlinePlayers()) holo.destroy(players);
			}
		}
		
		if(villager != null) villager.remove();
		
		getServer().getConsoleSender().sendMessage("�f[�4RageMode�f] �cstopped");
	}
	
	//Plugin start
	public void onEnable(){
		//Load Config
		loadConfig();
		
		//Set Config Standart
		configstandart = new ConfigStandart(this);
		configstandart.setStandart();
		
		//Register all Events and Commands
		registerListeners();
		registerCommands();
		
		//All planted Things will be removed.
		PlantedRemover();
		
		//Start Tasks
		lobbytasks = new Lobby(this);
		lobbytasks.lobbywplayers();
		
		getServer().getConsoleSender().sendMessage("�f[�4RageMode�f] �aStarted�8! �fThe most important things started �awell�8!");
		
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
			//Not Working: 
			Ranking ranking = new Ranking(this);
			ranking.set();
		}
		
		if(isShop) {
			if(Bukkit.getOnlinePlayers().size() > 0 && villager == null) villager = VillagerShopSpawner();		
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
		mysql.update("CREATE TABLE IF NOT EXISTS Coins(UUID varchar(64), COINS int, SPEEDUPGRADE int, BOWPOWERUPGRADE int);");
	}
    
	//Register All Events
	private void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		
		//Items
		pm.registerEvents(new AxeEvent(this), this);
		pm.registerEvents(new Knife(this), this);
		pm.registerEvents(new Compass(this), this);
		pm.registerEvents(new Grenade(this), this);
		pm.registerEvents(new Bow(this), this);
		pm.registerEvents(new Mine(this), this);
		pm.registerEvents(new Healer(this), this);
		
		//Events
		pm.registerEvents(new PlayerDeathListener(this), this);
		pm.registerEvents(new PlayerJoinListener(this), this);
		pm.registerEvents(new PlayerQuitListener(this), this);
		pm.registerEvents(new Listeners(this), this);
		pm.registerEvents(new PlayerRespawnListener(this), this);
		pm.registerEvents(new AsyncPlayerChatListener(this), this);
		pm.registerEvents(new FoodWeatherChangeListener(), this);
		pm.registerEvents(new InventoryItemListener(this), this);
		pm.registerEvents(new BlockBedListener(this), this);
		pm.registerEvents(new Shop(this), this);
		pm.registerEvents(new PowerUPItemListener(this), this);
		
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
		this.getCommand("leave").setExecutor(new LobbyCommands(this));
		this.getCommand("lobby").setExecutor(new LobbyCommands(this));
		this.getCommand("l").setExecutor(new LobbyCommands(this));
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
	
	//Power System
	public int getPower(Player p){
		switch(getType(p)){
			case ADMIN:
				return 10;
			case MODERATOR:
				return 5;
			case YOUTUBER:
				return 3;
			case PREMIUM:
				return 1;
			case USER:
				return 0;
			default:
				return 0;
		}
	}
	
	public Type getType(Player p){
		if(p.hasPermission("ragemode.admin")) return Type.ADMIN;
		if(p.hasPermission("ragemode.moderator")) return Type.MODERATOR;
		if(p.hasPermission("ragemode.youtuber")) return Type.YOUTUBER;
		if(p.hasPermission("ragemode.premium")) return Type.PREMIUM;
		return Type.USER;
	}
	
	//User Type
	public enum Type{
		USER, PREMIUM, YOUTUBER, MODERATOR, ADMIN;
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
	
	public void killGroundremover(Player player) {
		if(!playerminelist.contains(player) && !playercombataxelist.contains(player) && !playergrenadelist.contains(player) && !playerclaymorelist.contains(player) && !playerknifelist.contains(player) && !playerbowlist.contains(player)) {
			if(isDebug) System.out.println("[Debug]> Victim: " + player.getName() + " | Killer: Nobody | Before Killground: Nothing");
		}
		
		if(playercombataxelist.contains(player)) {
			if(isDebug) System.out.println("[Debug]> Victim: " + player.getName() + " | Before Killground: Combat Axe");
			playercombataxelist.remove(player);
		}
		
		if(playergrenadelist.contains(player)) {
			if(isDebug) System.out.println("[Debug]> Victim: " + player.getName() + " | Before Killground: Grenade");
			playergrenadelist.remove(player);
		}
		
		if(playerclaymorelist.contains(player)) {
			if(isDebug) System.out.println("[Debug]> Victim: " + player.getName() + " | Before Killground: ClayMore");
			playerclaymorelist.remove(player);
		}
		
		if(playerminelist.contains(player)) {
			if(isDebug) System.out.println("[Debug]> Victim: " + player.getName() + " | Before Killground: Mine");
			playerminelist.remove(player);
		}
		
		if(playerknifelist.contains(player)) {
			if(isDebug) System.out.println("[Debug]> Victim: " + player.getName() + " | Before Killground: Knife");
			playerknifelist.remove(player);
		}
		
		if(playerbowlist.contains(player)) {
			if(isDebug) System.out.println("[Debug]> Victim: " + player.getName() + " | Before Killground: Bow");
			playerbowlist.remove(player);
		}
	}
	
	public Villager VillagerShopSpawner() {
		Location loc = new TeleportAPI(this).getVillagerShopLocation();
		
		if(loc != null) {
			Villager villager = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
			villager.setCustomName("�6Shop");
			villager.setCustomNameVisible(true);
			villager.setAgeLock(true);
			villager.setCanPickupItems(false);
			new VillagerThread(villager, loc).start();
			return villager;
		} else {
			System.out.println("[RageMode] WARNING: You haven't set the villager shop spawn!");
			return null;
		}
	}
}