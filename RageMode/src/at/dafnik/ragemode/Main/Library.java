package at.dafnik.ragemode.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import at.dafnik.ragemode.API.C4Speicher;
import at.dafnik.ragemode.API.Holograms;
import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Shop.VillagerShop;

public class Library {

	// VillagerShop
	public static VillagerShop villager;
	// VillagerShop Holo
	public static Holograms villagerholo;

	// BossBar
	public static BossBar bar = Bukkit.getServer().createBossBar(Strings.bossbar, BarColor.BLUE, BarStyle.SOLID,
			BarFlag.PLAY_BOSS_MUSIC);

	// Teams
	public static List<Team> teams = new ArrayList<>();
	public static Team ingame;
	public static Scoreboard scoreboard;

	// --------------------------------------------------------------------
	// Player List which voted
	public static List<String> voted = new ArrayList<>();
	// Maps with votes
	public static HashMap<String, Integer> votes = new HashMap<>();
	// Map list
	public static List<String> maps = new ArrayList<>();
	// Map to vote
	public static List<String> mapstovote = new ArrayList<>();
	// Voted Map
	public static String votedmap;

	// ----------------------------------------------------------------------
	// Player Ingamelist
	public static List<Player> ingameplayer = new ArrayList<Player>();

	// Player Spectator
	public static List<Player> spectatorlist = new ArrayList<Player>();

	// Respawnschutz
	public static List<Player> respawnsafe = new ArrayList<>();

	// Player Win System
	public static HashMap<Player, Integer> playerpoints = new HashMap<>();

	// Allowed to build
	public static List<Player> builder = new ArrayList<>();

	// Planted Things
	public static List<Location> planted = new ArrayList<>();

	// Planted C4
	public static List<C4Speicher> plantedc4 = new ArrayList<>();

	// Sparcles
	public static List<Player> sparcleswitch = new ArrayList<>();

	// Hook
	public static List<Player> allowedhook = new ArrayList<>();

	// PowerUP
	public static List<Entity> powerup_entity = new ArrayList<>();
	public static HashMap<Integer, Holograms> powerup_hashmap = new HashMap<>();
	public static List<Holograms> powerup_list = new ArrayList<>();
	public static Integer powerup_integer = 0;
	public static List<Player> powerup_speedeffect = new ArrayList<>();
	public static List<Player> powerup_doublejump = new ArrayList<>();
	public static List<Player> powerup_flyparticle = new ArrayList<Player>();
	public static List<Player> powerup_shield = new ArrayList<>();

	// Title
	public static int fadein = 5;
	public static int fadeout = 5;
	public static int stay = 20;
}
