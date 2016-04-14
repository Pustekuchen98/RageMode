package at.dafnik.ragemode.API;

import org.bukkit.Bukkit;

import at.dafnik.ragemode.Main.Main;

public class Strings {
	
	private static String pre = Main.pre;
	public static String log_pre = "[RageMode] ";
	private static String debug_pre = "[Debug]> ";
	
	public static String error_wrong_inv_string = log_pre + "ERROR: No update for this item!";
	public static String error_no_map = log_pre + "ERROR: TeleportAPI: getRandomMapSpawnLocation!";
	public static String error_explosion_no_killground = log_pre + "ERROR: ExplosionAPI crashed. No cause of explosion found!";
	public static String error_powerup_last = log_pre + "ERROR: No PowerUP has been created! Caused by: No Map or no PowerUP Spawns";
	public static String error_randommizer_dont_work = log_pre + "ERROR: PowerUPItemListener - Randomizer crashed!";
	public static String error_more_rankingwall_places_then_player_in_mysql = log_pre + "WARNING: More Ranking places than players registered in your MySQL Database!";
	public static String error_rankingwall_headrotation = log_pre + "ERROR: Please set the rotation of the Ranking Heads! /rm setranking setrotation";
	public static String error_could_not_connect_to_mysql = log_pre + "MySQL Couldn't Connect!";
	public static String error_could_not_disconnect_to_mysql = log_pre + "MySQL Couldn't Disconnect! Error: ";
	public static String error_no_created_maps = log_pre + "ERROR: You have not got any Maps in the config! You can add Maps and spawns with /rm";
	public static String error_not_existing_villagerspawn = log_pre + "ERROR: You have not set the villager shop spawn!";
	public static String error_unkown_gamestate = log_pre + "ERROR: Unknown Game Status!";
	public static String error_not_existing_lobbyspawn = log_pre + "ERROR: You have not set the Lobby spawnpoint!";
	public static String error_playerdeath_points = log_pre + "ERROR: PlayerDeathListener - givePlayerPoints - Unknown kill cause of death! Nothing will happen!";
	public static String error_not_existing_map_middle_point = log_pre + "WARNING: No Map-Middle-Point was findable!";
	public static String error_cast_to_int = log_pre + "ERROR: CompassThread Cast Exception!";
	public static String error_not_authenticated_player = log_pre +  "ERROR: Player authentication failed! Manager did not work!";
	public static String error_only_player_use = log_pre + "This command is only for players available!";
	public static String error_not_on_a_bungee = pre + "§cThis server does not support BungeeCord§8!";
	public static String error_enter = pre + "§cWrong enter§8!";
	public static String error_enter_unknown_map = pre + "§cYou have to enter a map§8!";
	public static String error_enter_unknown_spawnnumber = pre + "§cYou have to enter a spawnnumber§8!";
	public static String error_permission = pre + "§cYou are not allowed to use this command§8!";
	public static String error_unknown_map = pre + "§cUnknown map§8!";
	public static String error_not_mysql_enabled = pre + "§cYou have not enabled the MySQL Database§8!";
	public static String error_cant_join_at_the_moment = pre + "\n§7You can not join at the moment";
	public static String error_game_is_full = pre + "\n§4The server is full\n§7You had to buy §6premium §7for joining full games";
	public static String error_you_kicked = pre + "\n§cYou have been kicked by a §6premium\n§3Only other §6premiums §7can not be kicked";
	public static String error_all_left = pre + "§cAll players left the game§8! §cThe game will stop§8!";
	public static String error_you_already_voted = pre + "§cYou have already voted§8!";
	public static String error_map_cannot_vote = pre + "§cYou can not vote for this map§8!";
	public static String error_voting_finished = pre + "§cYou can not vote for a map at this moment§8!";
	public static String error_not_enough_player= pre + "§cWaiting for more players";
	public static String error_needed_player = pre + "§eNeeded players§8: §b";
	public static String error_no_winner = pre + "§7There is no §ewinner §7in §cRageMode";
	public static String error_inventory_false_click = pre + "§cWrong click";
	public static String error_no_uuid = pre + "§cThe player has not played before on this server§8!";
	public static String error_thread_exception = "thread crashed! The game goes on!";
	public static String error_cast = pre + "§cInvalid number§8! §cYou can only use full numbers§8!";
	
	public static String map_create = pre + "§7The map has been created §asuccessful§8: §b";
	public static String map_set_villagershop = pre + "§7The §bvillagershop §7has been created §asuccessful§8!";
	public static String map_edit_allowed = pre + "§7Now you are §aallowed §7to build§8!";
	public static String map_edit_disallowed = pre + "§7Now you are §cdisallowed §7to build§8!";
	public static String map_set_author = pre + "§7The map author has been set §asuccessful§8: §b";
	public static String map_set_coordinates = pre + "§7Added this map spawn §asuccessful§8: §b";
	public static String map_set_powerup_coordinates = pre + "§7Added this powerup spawn §asuccessful§8: §b";
	public static String map_set_middlepoint = pre + "§7Added the map-middle-point §asuccessful§8!";
	public static String map_worldborder = pre + "§cYou are not allowed to pass§8!\n" + pre + "§cThe map ends here§8!";
	public static String map_edit_not_yet = pre + "§cYou are only allowed to build during §aLobby §cand §aPre Lobby §ctime§8!";
	public static String map_spawnpowerup = pre + "§7The powerup has been §asuccessful §7spawned§8!";
	
	public static String game_set_needed_players = pre + "§7You changed the needed players to start §asuccesful§8: §b";
	public static String game_set_time = pre + "§7You changed the needed players §asuccessful§8: §b";
	
	public static String lobby_set_hologram = pre + "§7The hologram spawnpoint has been §asuccessful §7placed§8!";
	public static String lobby_set_spawn = pre + "§7The lobby spawnpoint has been §asuccessful §7placed§8!";
	public static String lobby_set_rotation = pre + "§7The rotation of the ranking wall heads has been §asuccesful §7changed§8: §b";
	
	public static String bungee_switch_on = pre + "§7BungeeCord support turned§8: §aON";
	public static String bungee_switch_off = pre + "§7BungeeCord support turned§8: §cOFF";
	public static String bungee_lobby_server = pre + "§7Changed the BungeeCord fallback server §asuccessful§8: §b";
	
	public static String mysql_set_host = pre + "§7Changed the MySQL host §asuccessful§8: §b";
	public static String mysql_set_database = pre + "§7MySQL database changed §asuccessful§8: §b";
	public static String mysql_set_username = pre + "§7MySQL username changed §asuccessful§8: §b";
	public static String mysql_set_password = pre + "§7MySQL password changed §asuccessful§8: §b";
	public static String mysql_switch_on = pre + "§7MySQL support turned§8: §aON";
	public static String mysql_switch_off = pre + "§7MySQL support turned§8: §cOFF";
	
	public static String stats_reset = pre + "§7Your stats has been resettet §asuccessful§8!";
	public static String statsadmin_successful = pre + "§7The execution of this command was §asuccesful§8!";
	public static String stats_admin = pre + "/statsadmin §8<§aadd §8| §aremove§8> §8<§akills §8| §adeaths §8| §awongames §8| §aplayedgames §8| §apoints §8| §aresets §8| §abowkills §8| §aknifekills §8| §aaxtkills§8 §8| §asuicides§8> §8<§aplayername§8> <§anumber§8>";
	public static String stats_are_loading = "§7Please wait a moment, your stats are being loaded...";
	public static String stats_your_name_first = "§7-= §eStats from ";
	public static String stats_your_name_two = " §7=-";
	public static String stats_points = " §7Points§8: §e";
	public static String stats_allkills = " §7All Kills§8: §e";
	public static String stats_explosivekills = " §7Explosion Kills§8: §e";
	public static String stats_knifekills = " §7Knife Kills§8: §e";
	public static String stats_axtkills = " §7Axt Kills§8: §e";
	public static String stats_deaths = " §7Deaths§8: §e";
	public static String stats_kd = " §7K/D§8: §e";
	public static String stats_playedgames = " §7Played Games§8: §e";
	public static String stats_wongames = " §7Won Games§8: §e";
	public static String stats_winningchances = " §7Won Games in Percent§8: §e";
	public static String stats_suicides = " §7Suicides§8: §e";
	public static String stats_statsreset = " §7Stats resets§8: §e";
	public static String stats_coins = " §7Coins§8: §e";
	
	public static String lobby_rotate_your_mouse = pre + "§7Rotate your scroll wheel to fly";
	
	public static String votes_you_have_voted = pre + "§7You have voted for§8: §b";
	public static String votes_vote_for_map = pre + "§7Vote for a map§8:";
	
	public static String tasks_lobby_player_online = pre + "§ePlayers online§8: §b" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers();
	public static String tasks_lobby_map_voted = pre + "§eMap§8: §b";
	public static String tasks_lobby_author = pre + "§eAuthor§8: §b";
	public static String tasks_lobby_no_author = "No Author";
	public static String tasks_lobby_countdown_first = pre + "§7Round starts in §e";
	public static String tasks_lobby_countdown_two = " §7seconds";
	public static String tasks_lobby_countdown_two_0 = " §7second";
	
	public static String tasks_warmup_voted_map = "§7Voted Map§8: §e";
	public static String tasks_warmup_voted_map_1 = " §8|| §7Author§8: §e";
	public static String tasks_warmup_teleport_to_map = pre + "§eAll players will be teleported to the map";
	public static String tasks_warmup_peacetime_ends_in = pre + "§7Peace time ends in §e";
	public static String tasks_warmup_peacetime_ends_in_0 =  " §7seconds";
	public static String tasks_warmup_peactime_ends_now = "§ePeace time ends";
	public static String tasks_warmup_peactime_ends_now_0 = "§cnow";
	public static String tasks_warmup_peacetime_ends = pre + "§ePeace time ends now";
	
	public static String tasks_ingame_countdown_1 = pre + "§7Round ends in §e";
	public static String tasks_ingame_countdown_2 = " §7seconds";
	public static String tasks_ingame_countdown_21 = " §7second";
	public static String tasks_ingame_kick_first_playing = pre + "\n§7Thanks for playing §cRageMode§8!";
	public static String tasks_ingame_kick_first_spectator = pre + "\n§7Thanks for watching §cRageMode§8!";
	public static String tasks_ingame_kick_back = "\n§eServer is back in few seconds";
	public static String tasks_ingame_kick_you_winner = "\n§6You are the winner§8!";
	public static String tasks_ingame_kick_the_winner = "\n§7Winner of this round is§8: §r";
	public static String tasks_ingame_kick_winner_nobody = "§cNobody";
	public static String tasks_ingame_kick_with = " §7with§8: §e";
	public static String tasks_ingame_kick_points = " §7points";
	
	public static String tasks_win_is_the_winner = "§7is the winner";
	public static String tasks_restart_countdown_0 = pre + "§cServer restarts in the next§e ";
	public static String tasks_restart_countdown_01 = " §cseconds§8\n" + Main.pre + "§cYou will be kicked after §e10 §cseconds";
	public static String tasks_restart_countdown_1 = pre + "§cServer restarts in §e";
	public static String tasks_restart_countdown_2 = " §cseconds";
	public static String tasks_restart_countdown_21 = " §csecond";
	public static String tasks_restart_now = pre + "§4§lRESTART";
	
	public static String ragemode_winner = pre + "§7Winner is§8: §r";
	public static String ragemode_server_is_back = "§7Server is back soon";
	public static String ragemode_mysql_connected = log_pre + "MySQL Connected";
	public static String ragemode_mysql_disconnected = log_pre + "MySQL Disconnected";
	public static String ragemode_updated_succesful = log_pre + "Configuration and your MySQL Database have been successful updated!";
	
	public static String kill_suicide = " §7suicided";
	public static String kill_killed = " §7killed §r";
	public static String kill_with = " §7with ";
	public static String kill_with_knife = "§6Knife";
	public static String kill_with_combat_axe = "§aCombat Axt";
	public static String kill_with_grenade = "§6Cluster Grenade";
	public static String kill_with_claymore = "§6Claymore";
	public static String kill_with_mine = "§6Mine";
	public static String kill_with_c4 = "§4C4";
	public static String kill_with_bow = "§6Bow";
	public static String kill_backstab_knife = pre + "§4§l+ §c3 §6Hearts";
	public static String kill_your_points = "§7Your points§8: §e";
	public static String kill_unknown_killer = " §7died";
	public static String kill_holo_points = " §7Points";
	public static String kill_points_plus = pre + "§a§l+";
	public static String kill_points_negative = pre + "§c§l";
	
	public static String killstreak = " §7has a §ckillstreak ";
	
	public static String item_compass_error = pre + "§cThere is no other player§8!";
	public static String item_compass_1 = pre + "§7Next player§8: ";
	public static String item_compass_2 = " §8| §7Blocks away§8: §6";
	
	public static String inventory_inv_speedupgrader = "§fSpeed Upgrade";
	public static String inventory_inv_speedupgrader_description = "§7Upgrade your speed, when you hold the knife in your hand";
	public static String inventory_inv_bowpowerupgrader = "§fBow Power Upgrade";
	public static String inventory_inv_bowpowerupgrader_description = "§7Upgrade your Bow power";
	public static String inventory_inv_knockbackupgrade = "§fKnockback ability Upgrade";
	public static String inventory_inv_knockbackupgrade_description = "§7Upgrade your Knife right click ability";
	public static String inventory_inv_spectralarrowupgrade = "§fSpectral Arrow Upgrade";
	public static String inventory_inv_spectralarrowupgrade_description = "§7Shoot spectral Arrows with your bow";
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
	public static String inventory_invmore_description_spectralarrow= "§fWhen you normally shoot with your bow. You will shoot normal arrows.";
	public static String inventory_invmore_description_spectralarrow_2 = "§fSo, when you buy this upgrade you will shoot spectral arrows.";
	public static String inventory_buy_successful = pre + "§aYou bought it successful";
	public static String inventory_buy_new_coins = pre + "§cCoins at the moment§8: §6";
	public static String inventory_buy_not_enough = pre + "§cYou haven't enough coins§8. §cMissing coins§8: §6";
	public static String inventory_buy_already_buy = pre + "§cYou have already bought it";
	
	public static String coins_your = pre + "§cYou have §6";
	public static String coins_your_2 = " §cCoins";
	public static String coins_admin = pre + "/coinsadmin §8<§aadd §8| §aremove§8> <§aplayername§8> <§anumber§8>";
	public static String coins_added_0 = pre + "§a+ §f";
	public static String coins_added_1 = " §7Coins";
	
	public static String powerup_get_0 = pre + "§e§k!dkafaaf21adöfaö223ö1jökldaöl\n" + Main.pre + "§7Your §aPowerUP§8: ";
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
	public static String items_c4 = "§4C4";
	public static String items_c4_detonator = "§4C4 §7Detonator";
	
	public static String bossbar = "§6You §bare playing §cRageMode";
}
