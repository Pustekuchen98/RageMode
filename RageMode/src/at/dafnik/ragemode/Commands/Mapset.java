package at.dafnik.ragemode.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.Main.Main.Status;

public class Mapset implements CommandExecutor{
	
	private Main plugin;
	private String pre;
	int zahl = 0;
	
	public Mapset(Main main){
		this.plugin = main;
		this.pre = Main.pre;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		if(sender instanceof Player){
			Player player = (Player)sender;
			
			if (cmd.getName().equalsIgnoreCase("rm")){
				if(!player.hasPermission("ragemode.admin")){
					if(player.hasPermission("ragemode.youtuber")) {
						player.sendMessage(pre + "§bRageMode §cCommands §3you can use§8:");
						player.sendMessage(pre + "/forcestart §8<- §aset the countdown to ten");
						player.sendMessage(pre + "/latestart §8<- §aset the countdown to 50");
						player.sendMessage(pre + "/list §8<- §aShows the voting");
						player.sendMessage(pre + "/stats §8<- §aShows you your stats");
						player.sendMessage(pre + "/statsreset §8<- §aResets your stats");
						
					} else if(player.hasPermission("ragemode.moderator")) {
						player.sendMessage(pre + "§bRageMode §cCommands §3you can use§8:");
						player.sendMessage(pre + "/forcestart §8<- §aset the countdown to ten");
						player.sendMessage(pre + "/latestart §8<- §aset the countdown to 50");
						player.sendMessage(pre + "/tpmap §8<§aArena Name> §8<§aNumber from Spawn's§8> <- §aTeleports you to the arena");
						player.sendMessage(pre + "/tplobby §8<- §aTeleports you to the Lobby");
						player.sendMessage(pre + "/list §8<- §aShows the voting");
						player.sendMessage(pre + "/stats §8<- §aShows you your stats");
						player.sendMessage(pre + "/statsreset §8<- §aResets your stats");
					} else {
						player.sendMessage(pre + "§bRageMode §cCommands §3you can use§8:");
						player.sendMessage(pre + "/list §8<- §aShows the voting");
						player.sendMessage(pre + "/stats §8<- §aShows you your stats");
						player.sendMessage(pre + "/statsreset §8<- §aResets your stats");
						
					}
				}else{
					if(args.length == 0){
						player.sendMessage(pre + "§bRageMode §cCommands§8:");
						player.sendMessage(pre + "/rm setlobby");
						player.sendMessage(pre + "/rm setmapname §8<§aMapname§8>");
						player.sendMessage(pre + "/rm setmapauthor §8<§aMapname§8> §8<§aMap Author §8/ §aMap Builders§8>");
						player.sendMessage(pre + "/rm setmapmiddle §8<§aMapname§8> §8<§aApproximately Blockradius of the map§8>");
						player.sendMessage(pre + "/rm setmapspawn §8<§aMapname§8>");
						player.sendMessage(pre + "/rm setpowerupspawn §8<§aMapname§8>");
						player.sendMessage(pre + "/rm setneededplayers §8<§aNeeded Players§8>");
						player.sendMessage(pre + "/rm setgametime §8<§aMinutes§8>");
						player.sendMessage(pre + "/rm sethologram");
						player.sendMessage(pre + "/rm setvillagershop");
						player.sendMessage(pre + "/rm builder §8<- §aAllows to build in the world during Lobby and PRE Lobby Time");
						player.sendMessage(pre + "/rm setranking §8<- §aSet postion of ranking places");
						player.sendMessage(pre + "/rm setranking setrotation §8<§aNORTH §8| §aEAST §8| §aSOUTH §8| §aWEST§8> <- §aChange the head rotation");
						player.sendMessage(pre + "§a-------------------------------------");
						player.sendMessage(pre + "/rm mysql switch §8<§aon §8| §aoff§8>");
						player.sendMessage(pre + "/rm mysql sethost §8<§aHost§8>");
						player.sendMessage(pre + "/rm mysql setdatabase §8<§aDatabase§8>");
						player.sendMessage(pre + "/rm mysql setusername §8<§aUsername§8>");
						player.sendMessage(pre + "/rm mysql setpassword §8<§aPassword§8>");
						player.sendMessage(pre + "§a-------------------------------------");
						player.sendMessage(pre + "/rm bungee switch §8<§aon §8| §aoff§8>");
						player.sendMessage(pre + "/rm bungee setlobbyserver §8<§aLobby Server§8>");
						player.sendMessage(pre + "§a-------------------------------------");
						player.sendMessage(pre + "/tpmap §8<§aArena Name> §8<§aNumber from Spawn's§8> <- §cModerators §acan use this command too");
						player.sendMessage(pre + "/tplobby §8<- §cModerators §acan also use command too");
						player.sendMessage(pre + "§a-------------------------------------");
						player.sendMessage(pre + "/stats");
						player.sendMessage(pre + "/statsreset");
						player.sendMessage(pre + "/statsadmin §8[§aAdmin Access§8]");
						player.sendMessage(pre + "/coins");
						player.sendMessage(pre + "/coinsadmin §8[§aAdmin Access§8]");
						player.sendMessage(pre + "§a-------------------------------------");
						player.sendMessage(pre + "/forcestart §8<- §cModerators §aand §5YouTubers §acan use this command too");
						player.sendMessage(pre + "/latestart §8<- §cModerators §aand §5YouTubers §acan use this command too");
					}
				 
					//Set lobby spawn
					if (args.length > 0) {
						if (args[0].equalsIgnoreCase("setlobby")) {
							//Permissions access
							if (!player.hasPermission("ragemode.admin")) {
								player.sendMessage(Strings.error_permission);
							} else {
								//Set all Coordinates
								String w = player.getWorld().getName();
								double x = player.getLocation().getX();
								double y = player.getLocation().getY();
								double z = player.getLocation().getZ();
								double yaw = player.getLocation().getYaw();
								double pitch = player.getLocation().getPitch();
			    	        
								plugin.getConfig().set("ragemode.lobbyspawn.world", w);
								plugin.getConfig().set("ragemode.lobbyspawn.x", Double.valueOf(x));
								plugin.getConfig().set("ragemode.lobbyspawn.y", Double.valueOf(y));
								plugin.getConfig().set("ragemode.lobbyspawn.z", Double.valueOf(z));
								plugin.getConfig().set("ragemode.lobbyspawn.yaw", Double.valueOf(yaw));
								plugin.getConfig().set("ragemode.lobbyspawn.pitch", Double.valueOf(pitch));
								plugin.saveConfig();
			    	        
								player.sendMessage(Strings.lobby_set_spawn);
							}
						} else if (args[0].equalsIgnoreCase("setvillagershop")) {
							// Permissions access
							if (!player.hasPermission("ragemode.admin")) {
								player.sendMessage(Strings.error_permission);
							} else {
								// Set all Coordinates
								String w = player.getWorld().getName();
								double x = player.getLocation().getX();
								double y = player.getLocation().getY();
								double z = player.getLocation().getZ();
								double yaw = player.getLocation().getYaw();
								double pitch = player.getLocation().getPitch();

								plugin.getConfig().set("ragemode.villagershopspawn.world", w);
								plugin.getConfig().set("ragemode.villagershopspawn.x", Double.valueOf(x));
								plugin.getConfig().set("ragemode.villagershopspawn.y", Double.valueOf(y));
								plugin.getConfig().set("ragemode.villagershopspawn.z", Double.valueOf(z));
								plugin.getConfig().set("ragemode.villagershopspawn.yaw", Double.valueOf(yaw));
								plugin.getConfig().set("ragemode.villagershopspawn.pitch", Double.valueOf(pitch));
								plugin.saveConfig();

								player.sendMessage(Strings.map_set_villagershop);
							}
						} else if (args[0].equalsIgnoreCase("builder")) {
							//Permissions access
							if (!player.hasPermission("ragemode.admin")) {
								player.sendMessage(Strings.error_permission);
							}else{
								if(Main.status == Status.PRE_LOBBY || Main.status == Status.LOBBY) {
									if(plugin.builder.get(player) == null) {
										plugin.builder.put(player, true);
										player.sendMessage(Strings.map_edit_allowed);
									} else {
										if(plugin.builder.get(player)) {
											plugin.builder.replace(player, false);
											player.sendMessage(Strings.map_edit_disallowed);
										} else {
											plugin.builder.replace(player, true);
											player.sendMessage(Strings.map_edit_allowed);
										}
									}
								} else player.sendMessage(Strings.map_edit_not_yet);
							}
						} else if(args[0].equalsIgnoreCase("setmapname")){
							//Permissions Access
							if(!player.hasPermission("ragemode.admin")){
								player.sendMessage(Strings.error_permission);
							}else{
								if(args.length == 2) {
									String mapname = args[1].toLowerCase();
									int settetnumber;
									
									int mapnamenumber = plugin.getConfig().getInt("ragemode.mapnames.mapnamenumber");
									settetnumber = mapnamenumber;
									mapnamenumber++;
									plugin.getConfig().set("ragemode.mapnames.mapnamenumber", Integer.valueOf(mapnamenumber));
									plugin.getConfig().set("ragemode.mapnames.mapnames." + settetnumber, mapname);
									plugin.getConfig().set("ragemode.mapspawn." + mapname + ".mapauthor", "None");
									plugin.saveConfig();
									
									plugin.maps.add(mapname);	
									
									player.sendMessage(Strings.map_create + args[1]);								
								} else {
									player.sendMessage(Strings.error_enter);
								}
							}
						} else if(args[0].equalsIgnoreCase("setranking")){
							//Permissions Access
							if(!player.hasPermission("ragemode.admin")){
								player.sendMessage(Strings.error_permission);
							}else{		
								if(args.length == 1) {
									int settetnumber;
									int rankingnumber = plugin.getConfig().getInt("ragemode.ranking.rankingnumber");
									settetnumber = rankingnumber;
									rankingnumber++;
									plugin.getConfig().set("ragemode.ranking.rankingnumber", rankingnumber);
									
									String w = player.getWorld().getName();
									int x = player.getLocation().getBlockX();
									int y = player.getLocation().getBlockY();
									int z = player.getLocation().getBlockZ();
									
									plugin.getConfig().set("ragemode.ranking.positions." + settetnumber + ".world", w);
									plugin.getConfig().set("ragemode.ranking.positions." + settetnumber + ".x", Integer.valueOf(x));
									plugin.getConfig().set("ragemode.ranking.positions." + settetnumber + ".y", Integer.valueOf(y));
									plugin.getConfig().set("ragemode.ranking.positions." + settetnumber + ".z", Integer.valueOf(z));
									
									plugin.saveConfig();
									
									player.sendMessage(Strings.statsadmin_succesfull);
									
								} else if(args[1].equalsIgnoreCase("setrotation")) {
									if(args.length == 2) {
										player.sendMessage(Strings.error_enter);
									} else if(args.length == 3){
										String eingabe = args[2].toUpperCase();
										if(eingabe.equals("NORTH")) {
											plugin.getConfig().set("ragemode.ranking.rotation", Integer.valueOf(0));
											player.sendMessage(Strings.lobby_set_rotation + eingabe);
										} else if(eingabe.equals("EAST")) {
											plugin.getConfig().set("ragemode.ranking.rotation", Integer.valueOf(1));
											player.sendMessage(Strings.lobby_set_rotation + eingabe);
										} else if(eingabe.equals("SOUTH")) {
											plugin.getConfig().set("ragemode.ranking.rotation", Integer.valueOf(2));
											player.sendMessage(Strings.lobby_set_rotation + eingabe);
										} else if(eingabe.equals("WEST")) {
											plugin.getConfig().set("ragemode.ranking.rotation", Integer.valueOf(3));
											player.sendMessage(Strings.lobby_set_rotation + eingabe);
										} else {
											player.sendMessage(Strings.error_enter);
										}
										
										plugin.saveConfig();
									}
								} else {
									player.sendMessage(Strings.error_enter);
								}
							}
						} else if(args[0].equalsIgnoreCase("setmapauthor")){
							//Permissions Access
							if(!player.hasPermission("ragemode.admin")){
								player.sendMessage(Strings.error_permission);
							}else{
								if(args.length == 3) {
									if(plugin.maps.contains(args[1].toLowerCase())) {
										String mapname = args[1].toLowerCase();
										String mapauthor = args[2];
										
										plugin.getConfig().set("ragemode.mapspawn." + mapname + ".mapauthor", mapauthor);
										plugin.saveConfig();
										
										player.sendMessage(Strings.map_set_author + mapauthor);
									} else {
										player.sendMessage(Strings.error_unknown_map);
									}
								} else {
									player.sendMessage(Strings.error_enter);
								}
							}
						} else if (args[0].equalsIgnoreCase("setmapspawn")) {
							//Permissions access
							if (!player.hasPermission("ragemode.admin")) {
								player.sendMessage(Strings.error_permission);
							}else{
								if(args.length == 2) {
									if(plugin.maps.contains(args[1].toLowerCase())) {
										//Set Arena name
										String mapname = args[1].toLowerCase();
										String w = player.getWorld().getName();
										double x = player.getLocation().getX();
										double y = player.getLocation().getY();
										double z = player.getLocation().getZ();
										double yaw = player.getLocation().getYaw();
										double pitch = player.getLocation().getPitch();
										
										int spawnnumbertoshow;
										int spawnnumber = plugin.getConfig().getInt("ragemode.mapspawn." + mapname + ".spawnnumber");
										spawnnumbertoshow = spawnnumber;
										if(spawnnumber == 0) {
											
										}
						            
										plugin.getConfig().set("ragemode.mapspawn." + mapname + "." + spawnnumber + ".world", w);
										plugin.getConfig().set("ragemode.mapspawn." + mapname + "." + spawnnumber + ".x", Double.valueOf(x));
										plugin.getConfig().set("ragemode.mapspawn." + mapname + "." + spawnnumber + ".y", Double.valueOf(y));
										plugin.getConfig().set("ragemode.mapspawn." + mapname + "." + spawnnumber + ".z", Double.valueOf(z));
										plugin.getConfig().set("ragemode.mapspawn." + mapname + "." + spawnnumber + ".yaw", Double.valueOf(yaw));
										plugin.getConfig().set("ragemode.mapspawn." + mapname + "." + spawnnumber + ".pitch", Double.valueOf(pitch));
										
										spawnnumber++;
										plugin.getConfig().set("ragemode.mapspawn." + mapname + ".spawnnumber", Integer.valueOf(spawnnumber));
										 
										plugin.saveConfig();
				
										player.sendMessage(Strings.map_set_coordinates + spawnnumbertoshow + " §3| §3Mapname§8: §6" + mapname);
									} else {
										player.sendMessage(Strings.error_unknown_map);
									}
								} else {
									player.sendMessage(Strings.error_enter);
								}
							 }
							
						} else if (args[0].equalsIgnoreCase("setpowerupspawn")) {
							//Permissions access
							if (!player.hasPermission("ragemode.admin")) {
								player.sendMessage(Strings.error_permission);
							}else{
								if(args.length == 2) {
									if(plugin.maps.contains(args[1].toLowerCase())) {
										//Set Arena name
										String mapname = args[1].toLowerCase();
										String w = player.getWorld().getName();
										double x = player.getLocation().getX();
										double y = player.getLocation().getY();
										double z = player.getLocation().getZ();
										
										int settetspawnnumber;
										int spawnnumber = plugin.getConfig().getInt("ragemode.powerupspawn." + mapname + ".spawnnumber");
										settetspawnnumber = spawnnumber;
										
										plugin.getConfig().set("ragemode.powerupspawn." + mapname + "." + spawnnumber + ".world", w);
										plugin.getConfig().set("ragemode.powerupspawn." + mapname + "." + spawnnumber + ".x", Double.valueOf(x));
										plugin.getConfig().set("ragemode.powerupspawn." + mapname + "." + spawnnumber + ".y", Double.valueOf(y));
										plugin.getConfig().set("ragemode.powerupspawn." + mapname + "." + spawnnumber + ".z", Double.valueOf(z));
										
										spawnnumber++;
										plugin.getConfig().set("ragemode.powerupspawn." + mapname + ".spawnnumber", Integer.valueOf(spawnnumber));
		 
										plugin.saveConfig();
				
										player.sendMessage(Strings.map_set_powerup_coordinates + settetspawnnumber + " §3| §3Mapname§8: §6" + mapname);
				
										zahl++;
									} else {
										player.sendMessage(Strings.error_unknown_map);
									}
								} else {
									player.sendMessage(Strings.error_enter);
								}
							 }
						} else if (args[0].equalsIgnoreCase("setmapmiddle")) {
							//Permissions access
							if (!player.hasPermission("ragemode.admin")) {
								player.sendMessage(Strings.error_permission);
							}else{
								if(args.length == 3) {
									if(plugin.maps.contains(args[1].toLowerCase())) {
										//Set Arena name
										String mapname = args[1].toLowerCase();
										String radius = args[2];
										String w = player.getWorld().getName();
										double x = player.getLocation().getX();
										double y = player.getLocation().getY();
										double z = player.getLocation().getZ();
						            
										plugin.getConfig().set("ragemode.mapspawn." + mapname + ".middlepoint.world", w);
										plugin.getConfig().set("ragemode.mapspawn." + mapname + ".middlepoint.x", Double.valueOf(x));
										plugin.getConfig().set("ragemode.mapspawn." + mapname + ".middlepoint.y", Double.valueOf(y));
										plugin.getConfig().set("ragemode.mapspawn." + mapname + ".middlepoint.z", Double.valueOf(z));
										
										plugin.getConfig().set("ragemode.mapspawn." + mapname + ".mapradius", Integer.valueOf(radius));
										
										plugin.saveConfig();
				
										player.sendMessage(Strings.map_set_middlepoint + " §8| §3Mapname§8: §6" + mapname + " §8| §3Mapradius§8: §6" + radius);
									} else {
										player.sendMessage(Strings.error_unknown_map);
									}
								} else {
									player.sendMessage(Strings.error_enter);
								}
							 }
						 }else if(args[0].equalsIgnoreCase("setneededplayers")){
							 //Permissions Access
							 if(!player.hasPermission("ragemode.admin")){
								 player.sendMessage(Strings.error_permission);
							 }else{
								 if(args.length == 2) {
									 plugin.getConfig().set("ragemode.settings.neededplayers", Integer.valueOf(args[1]));
									 plugin.saveConfig();
				 			
									 player.sendMessage(Strings.game_set_needed_players + args[1]);
								 } else {
									 player.sendMessage(Strings.error_enter);
								 }
							 }
						 } else if(args[0].equalsIgnoreCase("setgametime")){
							 if(!player.hasPermission("ragemode.admin")){
								 player.sendMessage(Strings.error_permission);
							 }else{
								 if(args.length == 2) {
									 plugin.getConfig().set("ragemode.settings.gametime", Integer.valueOf(args[1]));
									 int gametime = plugin.getConfig().getInt("ragemode.settings.gametime");
									 gametime = gametime * 60;
									 plugin.getConfig().set("ragemode.settings.gametime", Integer.valueOf(gametime));
									 plugin.saveConfig();
									 
									 player.sendMessage(Strings.game_set_time + gametime/60);
								 } else {
									 player.sendMessage(Strings.error_enter);
								 }
							 }
						 } else if(args[0].equalsIgnoreCase("sethologram")){
							 if(!player.hasPermission("ragemode.admin")){
								 player.sendMessage(Strings.error_permission);
							 }else{
								 
								 String w = player.getWorld().getName();
								 double x = player.getLocation().getX();
								 double y = player.getLocation().getY();
								 double z = player.getLocation().getZ();
								 
								 plugin.getConfig().set("ragemode.hologram.world", w);
								 plugin.getConfig().set("ragemode.hologram.x", Double.valueOf(x));
								 plugin.getConfig().set("ragemode.hologram.y", Double.valueOf(y));
								 plugin.getConfig().set("ragemode.hologram.z", Double.valueOf(z));
								 plugin.saveConfig();
								 
								 player.sendMessage(Strings.lobby_set_hologram);
							 }
						 } else if (args[0].equalsIgnoreCase("bungee")) {
							if (!player.hasPermission("ragemode.admin")) {
								player.sendMessage(Strings.error_permission);
							} else {
								if(args.length == 3) {
									if(args[1].equals("switch")) {	
										if(args[2].equals("on")) {		
											plugin.getConfig().set("ragemode.settings.bungee.switch", true);
											plugin.saveConfig();
											 
											player.sendMessage(Strings.bungee_switch_on);
											
										} else if(args[2].equals("off")) {	
											plugin.getConfig().set("ragemode.settings.bungee.switch", false);
											plugin.saveConfig();
											
											player.sendMessage(Strings.bungee_switch_off);
											
										} else {
											player.sendMessage(Strings.error_enter);
										}
									} else if(args[1].equals("setlobbyserver")) {	
										plugin.getConfig().set("ragemode.settings.bungee.lobbyserver", args[2]);
										plugin.saveConfig();
										
										player.sendMessage(Strings.bungee_switch_off + args[2]);
										
									} else {
										player.sendMessage(Strings.error_enter);
									}
								} else {
									player.sendMessage(Strings.error_enter);
								}
							 }
						 } else if(args[0].equalsIgnoreCase("mysql")){
							 if(!player.hasPermission("ragemode.admin")){
								 player.sendMessage(Strings.error_permission);
							 }else{
								if (args.length == 3) {
									String eingabe = args[1];
									
									if (args[1].equals("sethost")) {
										plugin.getConfig().set("ragemode.settings.mysql.host",eingabe);
										plugin.saveConfig();

										player.sendMessage(Strings.mysql_set_host+ eingabe);

									} else if (args[1].equals("setdatabase")) {
										plugin.getConfig().set("ragemode.settings.mysql.database",eingabe);
										plugin.saveConfig();

										player.sendMessage(Strings.mysql_set_database+ eingabe);

									} else if (args[1].equals("setusername")) {
										plugin.getConfig().set("ragemode.settings.mysql.username",eingabe);
										plugin.saveConfig();

										player.sendMessage(Strings.mysql_set_username + eingabe);

									} else if (args[1].equals("setpassword")) {
										plugin.getConfig().set("ragemode.settings.mysql.password", eingabe);
										plugin.saveConfig();

										player.sendMessage(Strings.mysql_set_password + eingabe);

									} else if (args[1].equals("switch")) {
										if (args[2].equals("on")) {

											plugin.getConfig().set("ragemode.settings.mysql.switch", true);
											plugin.saveConfig();

											player.sendMessage(Strings.mysql_switch_on);

										} else if (args[2].equals("off")) {
											plugin.getConfig().set("ragemode.settings.mysql.switch", false);
											plugin.saveConfig();

											player.sendMessage(Strings.mysql_switch_off);

										} else {
											player.sendMessage(Strings.error_enter);
										}
									} else {
										player.sendMessage(Strings.error_enter);
									}
								} else {
									player.sendMessage(Strings.error_enter);
								}
							 }
						 } else {
							 player.sendMessage(Strings.error_enter);
						 }
					 }
				 }
			}
		}
		return true;
	}
}
