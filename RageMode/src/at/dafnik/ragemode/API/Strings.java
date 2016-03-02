package at.dafnik.ragemode.API;

import at.dafnik.ragemode.Main.Main;

public class Strings {
	
	private static String pre = Main.pre;
	private static String log_pre = "[RageMode] ";
	private static String debug_pre = "[Debug]> ";
	
	public static String error_no_map = log_pre + "ERROR: TeleportAPI: getRandomMapSpawnLocation! Try catch faild!";
	public static String error_explosion_no_killground = log_pre + "ERROR: ExplosionAPI doesn't work well. No Explosionground can found!";
	public static String error_powerup_last = log_pre + "ERROR: PowerUP spawning failed! Caused by: No Map or no PowerUP Spawns";
	public static String error_powerup_spawn = log_pre + "ERROR: PowerUP spawing failed! Caused by: Coordinates Error. Trying Spawn function again.";
	public static String error_randommizer_dont_work = log_pre + "ERROR: PowerUPItemListener - Randomizer doesn't work!";
	public static String error_more_rankingwall_places_then_player_in_mysql = log_pre + "WARNING: More Ranking Places than players registered in your MySQL Database!";
	public static String error_rankingwall_headrotation = log_pre + "ERROR: Please set the rotation of the Ranking Heads! /rm setranking setrotation";
	public static String error_could_not_connect_to_mysql = log_pre + "MySQL Couldn't Connect! Error: ";
	public static String error_could_not_disconnect_to_mysql = log_pre + "MySQL Couldn't Disconnect! Error: ";
	public static String error_no_created_maps = log_pre + "ERROR: You've got no Maps in the config! You can add Maps and spawns with /rm";
	public static String error_not_existing_villagerspawn = log_pre + "ERROR: You haven't set the villager shop spawn!";
	public static String error_unkown_gamestate = log_pre + "ERROR: Unknown Game Status!";
	public static String error_not_existing_lobbyspawn = log_pre + "ERROR: You haven't set the Lobby spawnpoint!";
	public static String error_playerdeath_points = log_pre + "ERROR: PlayerDeathListener - givePlayerPoints - Unknown killground exception! Nothing will happen";
	public static String error_not_existing_map_middle_point = log_pre + "WARNING: You haven't set the Map Middle Point and the approximately mapradius!";
	public static String error_cast_to_int = log_pre + "ERROR: Cast to int error in Compass!";
	public static String error_not_authenticated_player = log_pre +  "ERROR: Not authenticated player is on the server! Manager don't work";
	public static String error_only_player_use = log_pre + "Only player can use these commands";
	public static String error_not_on_a_bungee = pre + "§cYou aren't on a BungeeCord Network";
	public static String error_enter = pre + "§cWrong enter";
	public static String error_enter_unknown_map = pre + "§cYou must enter a Mapname";
	public static String error_enter_unknown_spawnnumber = pre + "§cYou must enter a spawnnumber";
	public static String error_permission = pre + "§cYou aren't allowed to use the command";
	public static String error_unknown_map = pre + "§cUnknown map";
	public static String error_not_mysql_enabled = pre + "§cYou haven't enabled the MySQL Database";
	public static String error_cant_join_at_the_moment = pre + "\n§cYou can't join at the moment";
	public static String error_game_is_full = pre + "\n§4The server is full\n§3You need §6premium §3to join full games";
	public static String error_you_kicked = pre + "\n§cYou haven been kicked by a §6premium\n§3Only other §6premiums §3can't be kicked";
	public static String error_all_left = pre + "§cBecause all player left the game, the game stopped";
	public static String error_you_already_voted = pre + "§cYou have already voted";
	public static String error_map_cannot_vote = pre + "§cYou can not vote for this map";
	public static String error_voting_finished = pre + "§cYou can't vote for a map at this moment";
	public static String error_not_enough_player= pre + "§cWaiting for more players";
	public static String error_needed_player = pre + "§eNeeded players§8: §b";
	public static String error_no_winner = pre + "§3There is no §ewinner §3in §bRageMode";
	public static String error_inventory_false_click = pre + "§cFalse click";
	public static String error_no_uuid = pre + "§cThe player hasn't played before on this server";
	
	public static String map_create = pre + "§3You create the Map §asuccesfull§8: §6";
	public static String map_set_villagershop = pre + "§3You set the villagershop spawn §asuccesfull";
	public static String map_edit_allowed = pre + "§3You are §aallowed §3to build in your world";
	public static String map_edit_disallowed = pre + "§3You are §cdisallowed §3to build in your world";
	public static String map_set_author = pre + "§3You have set the author §asuccesfull§8: §6";
	public static String map_set_coordinates = pre + "§3Set succesfull Spawn§8: §a";
	public static String map_set_powerup_coordinates = pre + "§3Set succesfull PowerUp Spawns§8: §a";
	public static String map_set_middlepoint = pre + "§3Set §asuccesfull §3middlepoint";
	public static String map_worldborder = pre + "§cThis is the worldborder";
	public static String map_edit_not_yet = pre + "§cYou can only build on the map during §aLobby §cand §aPre Lobby";
	
	public static String game_set_needed_players = pre + "§3You have set the needed player number §asuccesful§8: §6";
	public static String game_set_time = pre + "§3You have set the gametime succesfull§8: §6";
	
	public static String lobby_set_hologram = pre + "§3You have set the hologram §asuccesful";
	public static String lobby_set_spawn = pre + "§3You have set the Lobby Spawn §asuccesful";
	public static String lobby_set_rotation = pre + "§3You have set the Rotation to the Ranking §asuccesful§a: §6";
	
	public static String bungee_switch_on = pre + "§3You have switch the Bungee module §aon";
	public static String bungee_switch_off = pre + "§3You have switch the Bungee module §coff";
	public static String bungee_lobby_server = pre + "§3You have set the lobbyserver §asuccesfull§8: §6";
	
	public static String mysql_set_host = pre + "§3You have set the host§8: §6";
	public static String mysql_set_database = pre + "§3You have set the database§8: §6";
	public static String mysql_set_username = pre + "§3You have set the username§8: §6";
	public static String mysql_set_password = pre + "§3You have set the password§8: §6";
	public static String mysql_switch_on = pre + "§3You have switch the MySQL Module §aon";
	public static String mysql_switch_off = pre + "§3You have switch the MySQL Module §coff";
	
	public static String stats_reset = pre + "§3Your stats has been §aresettet";
	public static String statsadmin_succesfull = pre + "§3The command was §asuccesful";
	public static String stats_admin = pre + "/statsadmin §8<§aadd §8| §aremove§8> §8<§akills §8| §adeaths §8| §awongames §8| §aplayedgames §8| §apoints §8| §aresets §8| §abowkills §8| §aknifekills §8| §aaxtkills§8 §8| §asuicides§8> §8<§aplayername§8> <§anumber§8>";
	
	public static String lobby_rotate_your_mouse = pre + "§3Rotate your scroll wheel to fly";
	
	public static String votes_you_have_voted = pre + "§3You have voted for§8: §6";
	public static String votes_vote_for_map = pre + "§3Vote for a map§8:";
	
	public static String tasks_ingame_countdown_1 = pre + "§3The round ends in §e";
	public static String tasks_ingame_countdown_2 = " §3seconds";
	public static String tasks_ingame_countdown_21= " §3second";
	public static String tasks_ingame_peacetime_ends = pre + "§eThe peace time ends now";
	
	public static String tasks_win_is_the_winner = "§3is the winner";
	public static String tasks_restart_countdown_0 = pre + "§cThe server restarts in the next§e ";
	public static String tasks_restart_countdown_01 = " §cseconds§8\n" + Main.pre + "§cYou will be kicked after §e10 §cseconds";
	public static String tasks_restart_countdown_1 = pre + "§cThe server restarts in §e";
	public static String tasks_restart_countdown_2 = " §cseconds";
	public static String tasks_restart_countdown_21 = " §csecond";
	public static String tasks_restart_now = pre + "§4§lRESTART";
	
	public static String ragemode_winner = pre + "§3The winner is§8: §r";
	public static String ragemode_server_is_back = "§3The server is back soon";
	public static String ragemode_mysql_connected = log_pre + "MySQL Connected";
	public static String ragemode_mysql_disconnected = log_pre + "MySQL Disconnected";
	
	public static String kill_suicide = " §3suicided";
	public static String kill_killed = " §3killed §r";
	public static String kill_with = " §3with ";
	public static String kill_with_knife = "§6Knife";
	public static String kill_with_combat_axe = "§aCombat Axt";
	public static String kill_with_grenade = "§6Cluster Grenade";
	public static String kill_with_claymore = "§6Claymore";
	public static String kill_with_mine = "§6Mine";
	public static String kill_with_bow = "§6Bow";
	public static String kill_backstab_knife = pre + "§4§l+ §c3 §4Hearts";
	public static String kill_your_points = "§3Your points§8: §e";
	public static String kill_unknown_killer = " §3died";
	public static String kill_holo_points = " §3Points";
	public static String kill_points_plus = pre + "§a§l+";
	public static String kill_points_negative = pre + "§c§l";
	
	public static String killstreak = " §3has a §ckillstreak ";
	
	public static String item_compass_error = pre + "§cThere isn't a player in this game";
	public static String item_compass_1 = pre + "§3Next Player§8: ";
	public static String item_compass_2 = " §8| §3Blocks away§8: §6";
	
	public static String inventory_inv_speedupgrader = "§fSpeed Upgrade";
	public static String inventory_inv_speedupgrader_description = "§7Upgrade your speed, when you hold the knife in your hand";
	public static String inventory_inv_bowpowerupgrader = "§fBow Power Upgrade";
	public static String inventory_inv_bowpowerupgrader_description = "§7Upgrade your Bow power, when you shoot with the Bow";
	public static String inventory_inv_knockbackupgrade = "§fKnockback ability Upgrade";
	public static String inventory_inv_knockbackupgrade_description = "§7Upgrade your Knife ability, when you right click your knife";
	public static String inventory_invmore_back_to_inv = "§cBack to the shop";
	public static String inventory_bought = " §a(bought)";
	public static String inventory_not_bought = " §c(not bought)";
	public static String inventory_flint_bought = "§aBought";
	public static String inventory_flint_not_enough_coins = "§cNot enough coins";
	public static String inventory_flint_buyable = "§aBuy";
	public static String inventory_invmore_description = "§6Description";
	public static String inventory_invmore_description_description = "§f§nDescription:§r ";
	public static String inventory_invmore_description_feather = "§fWhen you normally hold the knife you get speed tier 1.";
	public static String inventory_invmore_description_feather_2 = "§fSo, when you buy this upgrade you will get speed tier 2.";
	public static String inventory_invmore_description_powder = "§fWhen you normally shoot with the bow you get explosion radius and damage tier 1.";
	public static String inventory_invmore_description_powder_2 = "§fSo, when you buy this upgrade you will get explosion radius and damage tier 2.";
	public static String inventory_invmore_description_blazepowder = "§fWhen you normally right click your knife. You will have knockback ability tier 1.";
	public static String inventory_invmore_description_blazepowder_2 = "§fSo, when you buy this upgrade you will get knockback ability tier 2.";
	public static String inventory_buy_succesfull = pre + "§aYour bought it successful";
	public static String inventory_buy_new_coins = pre + "§cCoins at the moment§8: §6";
	public static String inventory_buy_not_enough = pre + "§cYou haven't enough money§8. §cMissing coins§8: §6";
	public static String inventory_buy_already_buy = pre + "§cYou have already bought it";
	
	public static String coins_your = pre + "§cYou have §6";
	public static String coins_your_2 = " §cCoins";
	public static String coins_admin = pre + "/coinsadmin §8<§aadd §8| §aremove§8> <§aplayername§8> <§anumber§8>";
	
	public static String powerup_get_0 = pre + "§e§k!dkafaaf21adöfaö223ö1jökldaöl\n" + Main.pre + "§3Your §aPowerUP§8: ";
	public static String powerup_get_1 = "§8!\n" + Main.pre + "§e§k!dkafaaf21adöfaö223ö1jökldaöl";
	
	public static String debug_saver_started = debug_pre + "Started Compass and Knife Thread";
	public static String debug_saver_stopped = debug_pre + "Stopped Compass and Knife Thread";
	public static String debug_powerup_get_1 = debug_pre + "PowerUP: ";
	public static String debug_powerup_get_2 = " | Player:";
	public static String debug_powerup_spawn_1 = debug_pre + "PowerUP spawnt on: ";
	public static String debug_powerup_spawn_2 = " | Coordinates: ";
	
	public static String items_doubleheart = "§4Double Heart";
	public static String items_mine = "§5Mine";
	public static String items_claymore = "§dClaymore";
	public static String items_jump = "§aJump boost";
	public static String items_slowness = "§1Slowness";
	public static String items_blindness = "§1Blindness";
	public static String items_speed = "§aSpeed";
	public static String items_invisibility = "§fInvisibility";
	public static String items_doublejump = "§5Double jump";
	public static String items_flash = "§fFlash";
	public static String items_fly = "§5Fly";
}
