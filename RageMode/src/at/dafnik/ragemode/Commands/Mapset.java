package at.dafnik.ragemode.Commands;

import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;
import at.dafnik.ragemode.Threads.PowerUpThread;

public class Mapset implements CommandExecutor {

	private String pre = Main.pre;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (cmd.getName().equalsIgnoreCase("rm")) {
				if (args.length == 0) {
					if (player.hasPermission(Strings.permissions_admin)) {
						writeAdminCommands(player);

					} else if (player.hasPermission("ragemode.youtuber")) {
						player.sendMessage(pre + "§bRageMode §cCommands §3you can use§8:");
						player.sendMessage(pre + "/forcestart §8<- §aset the countdown to ten");
						player.sendMessage(pre + "/latestart §8<- §aset the countdown to 50");
						player.sendMessage(pre + "/vote §8<§6mapname§8>");
						player.sendMessage(pre + "/list §8<- §aShows the voting");
						player.sendMessage(pre + "/stats §8<- §aShows you your stats");
						player.sendMessage(pre + "/statsreset §8<- §aResets your stats");

					} else if (player.hasPermission(Strings.permissions_moderator)) {
						player.sendMessage(pre + "§bRageMode §cCommands §3you can use§8:");
						player.sendMessage(pre + "/forcestart §8<- §aset the countdown to ten");
						player.sendMessage(pre + "/latestart §8<- §aset the countdown to 50");
						player.sendMessage(pre
								+ "/tpmap §8<§aArena Name> §8<§aNumber from Spawn's§8> <- §aTeleports you to the arena");
						player.sendMessage(pre + "/tplobby §8<- §aTeleports you to the Lobby");
						player.sendMessage(pre + "/vote §8<§6mapname§8>");
						player.sendMessage(pre + "/list §8<- §aShows the voting");
						player.sendMessage(pre + "/stats §8<- §aShows you your stats");
						player.sendMessage(pre + "/statsreset §8<- §aResets your stats");

					} else {
						player.sendMessage(pre + "§bRageMode §cCommands §3you can use§8:");
						player.sendMessage(pre + "/vote §8<§6mapname§8>");
						player.sendMessage(pre + "/list §8<- §aShows the voting");
						player.sendMessage(pre + "/stats §8<- §aShows you your stats");
						player.sendMessage(pre + "/statsreset §8<- §aResets your stats");

					}
					// Not args.length == 0
				} else {
					if (player.hasPermission(Strings.permissions_admin)) {
						switch (args[0].toUpperCase()) {
						case "SETLOBBY":
							if (args.length == 1) {
								String w = player.getWorld().getName();
								double x = player.getLocation().getX();
								double y = player.getLocation().getY();
								double z = player.getLocation().getZ();
								double yaw = player.getLocation().getYaw();
								double pitch = player.getLocation().getPitch();

								Main.getInstance().getConfig().set("ragemode.lobbyspawn.world", w);
								Main.getInstance().getConfig().set("ragemode.lobbyspawn.x", Double.valueOf(x));
								Main.getInstance().getConfig().set("ragemode.lobbyspawn.y", Double.valueOf(y));
								Main.getInstance().getConfig().set("ragemode.lobbyspawn.z", Double.valueOf(z));
								Main.getInstance().getConfig().set("ragemode.lobbyspawn.yaw", Double.valueOf(yaw));
								Main.getInstance().getConfig().set("ragemode.lobbyspawn.pitch", Double.valueOf(pitch));
								Main.getInstance().saveConfig();

								player.sendMessage(Strings.commands_mapset_setlobby_successful);

							} else
								player.sendMessage(Strings.commands_mapset_setlobby_usage);

							break;

						case "SETVILLAGERSHOP":
							if (args.length == 1) {
								String w = player.getWorld().getName();
								double x = player.getLocation().getX();
								double y = player.getLocation().getY();
								double z = player.getLocation().getZ();

								Main.getInstance().getConfig().set("ragemode.villagershopspawn.world", w);
								Main.getInstance().getConfig().set("ragemode.villagershopspawn.x", Double.valueOf(x));
								Main.getInstance().getConfig().set("ragemode.villagershopspawn.y", Double.valueOf(y));
								Main.getInstance().getConfig().set("ragemode.villagershopspawn.z", Double.valueOf(z));
								Main.getInstance().saveConfig();

								player.sendMessage(Strings.commands_mapset_setvillagershop_successful);

							} else
								player.sendMessage(Strings.commands_mapset_setvillagershop_usage);

							break;

						case "CREATEMAP":
							if (args.length == 2) {
								String mapname = args[1].toLowerCase();
								int settetnumber;

								int mapnamenumber = Main.getInstance().getConfig()
										.getInt("ragemode.mapnames.mapnamenumber");
								settetnumber = mapnamenumber;
								mapnamenumber++;
								Main.getInstance().getConfig().set("ragemode.mapnames.mapnamenumber",
										Integer.valueOf(mapnamenumber));
								Main.getInstance().getConfig().set("ragemode.mapnames.mapnames." + settetnumber,
										mapname);
								Main.getInstance().getConfig().set("ragemode.mapspawn." + mapname + ".mapauthor",
										"None");
								Main.getInstance().saveConfig();

								Library.maps.add(mapname);

								player.sendMessage(pre + "§e" + args[1] + Strings.commands_mapset_createmap_successful);

							} else
								player.sendMessage(Strings.commands_mapset_createmap_usage);

							break;

						case "SETRANKING":
							if (args.length == 1) {
								int settetnumber;
								int rankingnumber = Main.getInstance().getConfig()
										.getInt("ragemode.ranking.rankingnumber");
								settetnumber = rankingnumber;
								rankingnumber++;
								Main.getInstance().getConfig().set("ragemode.ranking.rankingnumber", rankingnumber);

								String w = player.getWorld().getName();
								int x = player.getLocation().getBlockX();
								int y = player.getLocation().getBlockY();
								int z = player.getLocation().getBlockZ();

								Main.getInstance().getConfig()
										.set("ragemode.ranking.positions." + settetnumber + ".world", w);
								Main.getInstance().getConfig().set("ragemode.ranking.positions." + settetnumber + ".x",
										Integer.valueOf(x));
								Main.getInstance().getConfig().set("ragemode.ranking.positions." + settetnumber + ".y",
										Integer.valueOf(y));
								Main.getInstance().getConfig().set("ragemode.ranking.positions." + settetnumber + ".z",
										Integer.valueOf(z));

								Main.getInstance().saveConfig();

								player.sendMessage(Strings.commands_mapset_setranking_place_successful_0 + rankingnumber
										+ Strings.commands_mapset_setranking_place_successful_1);

							} else if (args[1].equalsIgnoreCase("SETROTATION")) {
								if (args.length == 3) {

									String eingabe = args[2].toUpperCase();

									switch (eingabe.toUpperCase()) {
									case "NORTH":
										Main.getInstance().getConfig().set("ragemode.ranking.rotation",
												Integer.valueOf(0));
										player.sendMessage(Strings.commands_mapset_setranking_rotation_successful_0
												+ eingabe + Strings.commands_mapset_setranking_rotation_successful_1);
										break;

									case "EAST":
										Main.getInstance().getConfig().set("ragemode.ranking.rotation",
												Integer.valueOf(1));
										player.sendMessage(Strings.commands_mapset_setranking_rotation_successful_0
												+ eingabe + Strings.commands_mapset_setranking_rotation_successful_1);
										break;

									case "SOUTH":
										Main.getInstance().getConfig().set("ragemode.ranking.rotation",
												Integer.valueOf(2));
										player.sendMessage(Strings.commands_mapset_setranking_rotation_successful_0
												+ eingabe + Strings.commands_mapset_setranking_rotation_successful_1);
										break;

									case "WEST":
										Main.getInstance().getConfig().set("ragemode.ranking.rotation",
												Integer.valueOf(3));
										player.sendMessage(Strings.commands_mapset_setranking_rotation_successful_0
												+ eingabe + Strings.commands_mapset_setranking_rotation_successful_1);
										break;

									default:
										player.sendMessage(Strings.commands_mapset_setranking_usage_1);
										break;
									}

									Main.getInstance().saveConfig();

								} else
									player.sendMessage(Strings.commands_mapset_setranking_usage_1);

							} else {
								player.sendMessage(Strings.commands_mapset_setranking_usage_0);
								player.sendMessage(Strings.commands_mapset_setranking_usage_1);
							}

							break;

						case "SETMAPAUTHOR":
							if (args.length == 3) {
								if (Library.maps.contains(args[1].toLowerCase())) {
									String mapname = args[1].toLowerCase();
									String mapauthor = args[2];

									Main.getInstance().getConfig().set("ragemode.mapspawn." + mapname + ".mapauthor",
											mapauthor);
									Main.getInstance().saveConfig();

									player.sendMessage(Strings.commands_mapset_setmapauthor_successful_0 + mapname
											+ Strings.commands_mapset_setmapauthor_successful_1 + mapauthor
											+ Strings.commands_mapset_setmapauthor_successful_2);

								} else
									player.sendMessage(Strings.commands_mapset_error_map_not_exist);

							} else
								player.sendMessage(Strings.commands_mapset_setmapauthor_usage);

							break;

						case "SETMAPSPAWN":
							if (args.length == 2) {
								if (Library.maps.contains(args[1].toLowerCase())) {
									// Set Arena name
									String mapname = args[1].toLowerCase();
									String w = player.getWorld().getName();
									double x = player.getLocation().getX();
									double y = player.getLocation().getY();
									double z = player.getLocation().getZ();
									double yaw = player.getLocation().getYaw();
									double pitch = player.getLocation().getPitch();

									int spawnnumber = Main.getInstance().getConfig()
											.getInt("ragemode.mapspawn." + mapname + ".spawnnumber");
									int numb = spawnnumber;
									Main.getInstance().getConfig()
											.set("ragemode.mapspawn." + mapname + "." + spawnnumber + ".world", w);
									Main.getInstance().getConfig().set(
											"ragemode.mapspawn." + mapname + "." + spawnnumber + ".x",
											Double.valueOf(x));
									Main.getInstance().getConfig().set(
											"ragemode.mapspawn." + mapname + "." + spawnnumber + ".y",
											Double.valueOf(y));
									Main.getInstance().getConfig().set(
											"ragemode.mapspawn." + mapname + "." + spawnnumber + ".z",
											Double.valueOf(z));
									Main.getInstance().getConfig().set(
											"ragemode.mapspawn." + mapname + "." + spawnnumber + ".yaw",
											Double.valueOf(yaw));
									Main.getInstance().getConfig().set(
											"ragemode.mapspawn." + mapname + "." + spawnnumber + ".pitch",
											Double.valueOf(pitch));
									spawnnumber++;

									Main.getInstance().getConfig().set("ragemode.mapspawn." + mapname + ".spawnnumber",
											Integer.valueOf(spawnnumber));

									Main.getInstance().saveConfig();

									player.sendMessage(Strings.commands_mapset_setmapspawn_successful_0 + numb
											+ Strings.commands_mapset_setmapspawn_successful_1 + mapname
											+ Strings.commands_mapset_setmapspawn_successful_2);

								} else
									player.sendMessage(Strings.commands_mapset_error_map_not_exist);

							} else
								player.sendMessage(Strings.commands_mapset_setmapspawn_usage);

							break;

						case "SETPOWERUPSPAWN":
							if (args.length == 2) {
								if (Library.maps.contains(args[1].toLowerCase())) {
									// Set Arena name
									String mapname = args[1].toLowerCase();
									String w = player.getWorld().getName();
									double x = player.getLocation().getX();
									double y = player.getLocation().getY();
									double z = player.getLocation().getZ();

									int spawnnumber = Main.getInstance().getConfig()
											.getInt("ragemode.powerupspawn." + mapname + ".spawnnumber");
									int numb = spawnnumber;

									Main.getInstance().getConfig()
											.set("ragemode.powerupspawn." + mapname + "." + spawnnumber + ".world", w);
									Main.getInstance().getConfig().set(
											"ragemode.powerupspawn." + mapname + "." + spawnnumber + ".x",
											Double.valueOf(x));
									Main.getInstance().getConfig().set(
											"ragemode.powerupspawn." + mapname + "." + spawnnumber + ".y",
											Double.valueOf(y));
									Main.getInstance().getConfig().set(
											"ragemode.powerupspawn." + mapname + "." + spawnnumber + ".z",
											Double.valueOf(z));

									spawnnumber++;
									Main.getInstance().getConfig().set(
											"ragemode.powerupspawn." + mapname + ".spawnnumber",
											Integer.valueOf(spawnnumber));

									Main.getInstance().saveConfig();

									player.sendMessage(Strings.commands_mapset_setpowerupspawn_successful_0 + numb
											+ Strings.commands_mapset_setpowerupspawn_successful_1 + mapname
											+ Strings.commands_mapset_setpowerupspawn_successful_2);

								} else
									player.sendMessage(Strings.commands_mapset_error_map_not_exist);

							} else
								player.sendMessage(Strings.commands_mapset_setpowerupspawn_usage);

							break;

						case "SETMAPMIDDLE":
							if (args.length == 3) {
								if (Library.maps.contains(args[1].toLowerCase())) {
									// Set Arena name
									String mapname = args[1].toLowerCase();
									String radius = args[2];
									String w = player.getWorld().getName();
									double x = player.getLocation().getX();
									double y = player.getLocation().getY();
									double z = player.getLocation().getZ();

									Main.getInstance().getConfig()
											.set("ragemode.mapspawn." + mapname + ".middlepoint.world", w);
									Main.getInstance().getConfig()
											.set("ragemode.mapspawn." + mapname + ".middlepoint.x", Double.valueOf(x));
									Main.getInstance().getConfig()
											.set("ragemode.mapspawn." + mapname + ".middlepoint.y", Double.valueOf(y));
									Main.getInstance().getConfig()
											.set("ragemode.mapspawn." + mapname + ".middlepoint.z", Double.valueOf(z));
									Main.getInstance().getConfig().set("ragemode.mapspawn." + mapname + ".mapradius",
											Integer.valueOf(radius));

									Main.getInstance().saveConfig();

									player.sendMessage(Strings.commands_mapset_setmapmiddle_successful_0 + radius
											+ Strings.commands_mapset_setmapmiddle_successful_1 + mapname
											+ Strings.commands_mapset_setmapmiddle_successful_2);

								} else
									player.sendMessage(Strings.commands_mapset_error_map_not_exist);

							} else
								player.sendMessage(Strings.commands_mapset_setmapmiddle_usage);

							break;

						case "SETHOLOGRAM":
							if (args.length == 1) {
								String w = player.getWorld().getName();
								double x = player.getLocation().getX();
								double y = player.getLocation().getY();
								double z = player.getLocation().getZ();

								Main.getInstance().getConfig().set("ragemode.hologram.world", w);
								Main.getInstance().getConfig().set("ragemode.hologram.x", Double.valueOf(x));
								Main.getInstance().getConfig().set("ragemode.hologram.y", Double.valueOf(y));
								Main.getInstance().getConfig().set("ragemode.hologram.z", Double.valueOf(z));
								Main.getInstance().saveConfig();

								player.sendMessage(Strings.commands_mapset_sethologram_successful);

							} else
								player.sendMessage(Strings.commands_mapset_sethologram_usage);

							break;

						case "BUNGEE":
							if (args.length == 1) {
								if (!Main.isBungee) {
									Main.getInstance().getConfig().set("ragemode.settings.bungee.switch", true);
									Main.getInstance().saveConfig();

									player.sendMessage(Strings.commands_mapset_bungee_changed_on);

								} else {
									Main.getInstance().getConfig().set("ragemode.settings.bungee.switch", false);
									Main.getInstance().saveConfig();

									player.sendMessage(Strings.commands_mapset_bungee_changed_off);
								}
							} else if (args.length == 3) {
								if (args[1].equalsIgnoreCase("setlobbyserver")) {
									Main.getInstance().getConfig().set("ragemode.settings.bungee.lobbyserver", args[2]);
									Main.getInstance().saveConfig();

									player.sendMessage(Strings.commands_mapset_bungee_setserver_successful + args[2]);

								} else
									player.sendMessage(Strings.commands_mapset_bungee_usage_1);

							} else {
								player.sendMessage(Strings.commands_mapset_bungee_usage_0);
								player.sendMessage(Strings.commands_mapset_bungee_usage_1);
							}

							break;

						case "MYSQL":
							if (args.length == 1) {
								if (!Main.isMySQL) {
									Main.getInstance().getConfig().set("ragemode.settings.mysql.switch", true);
									Main.getInstance().saveConfig();

									player.sendMessage(Strings.commands_mapset_mysql_changed_on);

								} else {
									Main.getInstance().getConfig().set("ragemode.settings.mysql.switch", false);
									Main.getInstance().saveConfig();

									player.sendMessage(Strings.commands_mapset_mysql_changed_off);
								}

							} else if (args.length == 3) {
								switch (args[1].toUpperCase()) {
								case "SETHOST":
									Main.getInstance().getConfig().set("ragemode.settings.mysql.host", args[1]);
									Main.getInstance().saveConfig();

									player.sendMessage(Strings.commands_mapset_mysql_sethost + args[1]);
									break;

								case "SETDATABASE":
									Main.getInstance().getConfig().set("ragemode.settings.mysql.database", args[1]);
									Main.getInstance().saveConfig();

									player.sendMessage(Strings.commands_mapset_mysql_setdatabase + args[1]);
									break;

								case "SETUSERNAME":
									Main.getInstance().getConfig().set("ragemode.settings.mysql.username", args[1]);
									Main.getInstance().saveConfig();

									player.sendMessage(Strings.commands_mapset_mysql_setusername + args[1]);
									break;

								case "SETPASSWORD":
									Main.getInstance().getConfig().set("ragemode.settings.mysql.password", args[1]);
									Main.getInstance().saveConfig();

									player.sendMessage(Strings.commands_mapset_mysql_setpassword + args[1]);
									break;

								default:
									player.sendMessage(Strings.commands_mapset_mysql_usage_1);
									break;
								}

							} else {
								player.sendMessage(Strings.commands_mapset_mysql_usage_0);
								player.sendMessage(Strings.commands_mapset_mysql_usage_1);
							}

							break;
						}
					}

					if (player.hasPermission(Strings.permissions_admin)
							|| player.hasPermission(Strings.permissions_moderator)) {
						switch (args[0].toUpperCase()) {
						case "BUILDER":
							if (args.length == 1) {
								if (Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) {
									if (Library.builder.contains(player)) {
										Library.builder.remove(player);
										player.setGameMode(GameMode.ADVENTURE);
										player.sendMessage(Strings.commands_mapset_builder_disallowed);

									} else {
										Library.builder.add(player);
										player.setGameMode(GameMode.CREATIVE);
										player.sendMessage(Strings.commands_mapset_builder_allowed);
									}

								} else
									player.sendMessage(Strings.commands_mapset_builder_not_yet);

							} else
								player.sendMessage(Strings.commands_mapset_builder_usage);

							break;

						case "SETPLAYERS":
							if (args.length == 2) {
								try {
									setPlayers(args);

									player.sendMessage(Strings.commands_mapset_setplayers_successful + args[1]);

								} catch (Exception ex) {
									player.sendMessage(Strings.commands_mapset_setplayers_usage);
								}

							} else
								player.sendMessage(Strings.commands_mapset_setplayers_usage);

							break;

						case "SPAWNPOWERUP":
							if (args.length == 1) {
								Location loc = player.getTargetBlock((Set<Material>) null, 5).getLocation();
								loc.setY(loc.getY() + 1);
								PowerUpThread.spawnPowerUP(loc);

								player.sendMessage(Strings.commands_mapset_spawnpowerup_successful);

							} else if (args.length == 2) {
								try {
									int numb = Integer.valueOf(args[1]);

									for (int i = 0; i < numb; i++) {
										Location loc = player.getTargetBlock((Set<Material>) null, 5).getLocation();
										loc.setY(loc.getY() + 1);
										PowerUpThread.spawnPowerUP(loc);
									}

									player.sendMessage(Strings.commands_mapset_spawnpowerup_successful);

								} catch (Exception ex) {
									player.sendMessage(Strings.commands_mapset_spawnpowerup_usage);
								}

							} else
								player.sendMessage(Strings.commands_mapset_spawnpowerup_usage);

							break;

						case "SETGAMETIME":
							if (args.length == 2) {
								try {
									setGameTime(args);

									player.sendMessage(Strings.commands_mapset_setgametime_successful + Main.getInstance().getConfig().getInt("ragemode.settings.gametime") / 60);

								} catch (Exception ex) {
									player.sendMessage(Strings.commands_mapset_setgametime_usage);
								}

							} else
								player.sendMessage(Strings.commands_mapset_setgametime_usage);

							break;

						default:
							break;

						}

					} else
						player.sendMessage(Strings.commands_mapset_error_permissions_denied);
				}
			}

		} else if (cmd.getName().equalsIgnoreCase("rm")) {
			if (args.length == 0) {
				System.out.println(Strings.log_pre + "RageMode Commands:");
				System.out.println(Strings.log_pre + "/rm setgametime <Minutes>");
				System.out.println(Strings.log_pre + "/rm setplayers <Needed Player>");
			} else {
				switch (args[0].toUpperCase()) {
				case "SETGAMETIME":
					if (args.length == 2) {
						try {
							setGameTime(args);

							System.out.println(Strings.commands_mapset_setgametime_successful_console + Main.getInstance().getConfig().getInt("ragemode.settings.gametime") / 60);

						} catch (Exception ex) {
							System.out.println(Strings.commands_mapset_setgametime_usage_console);
						}

					} else
						System.out.println(Strings.commands_mapset_setgametime_usage_console);

					break;

				case "SETPLAYERS":
					if (args.length == 2) {
						try {
							setPlayers(args);

							System.out.println(Strings.commands_mapset_setplayers_successful_console + args[1]);

						} catch (Exception ex) {
							System.out.println(Strings.commands_mapset_setplayers_usage_console);
						}

					} else
						System.out.println(Strings.commands_mapset_setplayers_usage_console);

					break;

				default:
					break;
				}
			}

		} else
			System.out.println(Strings.error_only_player_use);

		return true;
	}

	private void setGameTime(String[] args) {
		int gameminutes = Integer.valueOf(args[1]);
		int gametime = gameminutes * 60;
		Main.getInstance().getConfig().set("ragemode.settings.gametime", Integer.valueOf(gametime));
		Main.getInstance().saveConfig();
	}

	private void setPlayers(String[] args) {
		Main.getInstance().getConfig().set("ragemode.settings.neededplayers", Integer.valueOf(args[1]));
		Main.getInstance().saveConfig();
	}

	private void writeAdminCommands(Player player) {
		player.sendMessage(pre + "§bRageMode §cCommands§8:");
		player.sendMessage(pre + "§aMap §6Commands§8: - [§4Admin§8]");
		player.sendMessage(pre + "/rm setlobby");
		player.sendMessage(pre + "/rm createmap §8<§aMapname§8>");
		player.sendMessage(pre + "/rm setmapauthor §8<§aMapname§8> §8<§aMap Author §8/ §aMap Builders§8>");
		player.sendMessage(pre + "/rm setmapmiddle §8<§aMapname§8> §8<§aApproximately Blockradius of the map§8>");
		player.sendMessage(pre + "/rm setmapspawn §8<§aMapname§8>");
		player.sendMessage(pre + "/rm setpowerupspawn §8<§aMapname§8>");
		player.sendMessage(pre + "/rm sethologram");
		player.sendMessage(pre + "/rm setvillagershop");
		player.sendMessage(pre + "/rm setranking §8<- §aSet postions of ranking places");
		player.sendMessage(pre
				+ "/rm setranking setrotation §8<§aNORTH §8| §aEAST §8| §aSOUTH §8| §aWEST§8> <- §aChange the head rotation");

		player.sendMessage(pre + "\n");
		player.sendMessage(pre + "§aGame §6Commands§8: - [§4Admin§8] - [§cModerator§8]");
		player.sendMessage(pre + "/rm setgametime §8<§aMinutes§8>");
		player.sendMessage(pre + "/rm builder §8<- §aAllows to build in the world during Lobby and PRE Lobby Time");
		player.sendMessage(pre + "/rm spawnpowerup §8<§anumber§8>");
		player.sendMessage(pre + "/rm setplayers §8<§aNeeded Players§8>");

		player.sendMessage(pre + "\n");
		player.sendMessage(pre + "§aMySQL §6Commands§8:  - [§4Admin§8]");
		player.sendMessage(pre + "/rm mysql §8| §fenable / disable MySQL Support");
		player.sendMessage(pre + "/rm mysql sethost §8<§aHost§8>");
		player.sendMessage(pre + "/rm mysql setdatabase §8<§aDatabase§8>");
		player.sendMessage(pre + "/rm mysql setusername §8<§aUsername§8>");
		player.sendMessage(pre + "/rm mysql setpassword §8<§aPassword§8>");

		player.sendMessage(pre + "\n");
		player.sendMessage(pre + "§aBungeeCord §6Commands§8:  - [§4Admin§8]");
		player.sendMessage(pre + "/rm bungee §8| §fenable / disable BungeeCord Support");
		player.sendMessage(pre + "/rm bungee setlobbyserver §8<§aLobby Server§8>");

		player.sendMessage(pre + "\n");
		player.sendMessage(pre + "§aStats §8& §6Coins §6Commands§8:  - [§4Admin§8]");
		player.sendMessage(pre + "/statsadmin §8[§aAdmin Access§8]");
		player.sendMessage(pre + "/coinsadmin §8[§aAdmin Access§8]");

		player.sendMessage(pre + "\n");
		player.sendMessage(pre + "§aTeleport §6Commands§8:  - [§4Admin§8] - [§cModerators]");
		player.sendMessage(pre + "/tpmap §8<§aArena Name> §8<§aNumber from Spawn's§8>");
		player.sendMessage(pre + "/tplobby");

		player.sendMessage(pre + "\n");
		player.sendMessage(pre + "§aRoundstart §6Commands§8: - [§4Admins§8] - [§cModerators§8] - [§5YouTubers§8]");
		player.sendMessage(pre + "/forcestart §8<- §aset the countdown to ten");
		player.sendMessage(pre + "/latestart §8<- §aset the countdown to 50");

		player.sendMessage(pre + "\n");
		player.sendMessage(pre + "§aStats §8& §6Coins §6Commands§8:  - [§aEveryone§8]");
		player.sendMessage(pre + "/stats");
		player.sendMessage(pre + "/statsreset");
		player.sendMessage(pre + "/coins");

		player.sendMessage(pre + "\n");
		player.sendMessage(pre + "§aMap vote §6Commands§8:  - [§aEveryone§8]");
		player.sendMessage(pre + "/list §8<- §aShows the voting");
		player.sendMessage(pre + "/vote §8<§6mapname§8>");
	}
}
