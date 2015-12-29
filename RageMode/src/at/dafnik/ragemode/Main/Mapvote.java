package at.dafnik.ragemode.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.Main.Main.Status;

public class Mapvote implements CommandExecutor{
	
	private Main plugin;
	private String pre;
	private String mapwinner;
	private List<Integer> mapsinlist = new ArrayList<Integer>();
	
	public Mapvote(Main main){
		this.plugin = main;
		this.pre = Main.pre;
		
		plugin.getCommand("list").setExecutor(this);
		plugin.getCommand("vote").setExecutor(this);
	}
	
	//Add Map name to the Variable
	public void MapsToVoteAdd(){
		int mapnamenumber = plugin.getConfig().getInt("ragemode.mapnames.mapnamenumber");
		
		if(mapnamenumber <= 0) {
			System.out.println("[RageMode] ERROR: You've got no Maps in the config! You can add Maps and spawns with /rm");
			return;
		} else {
			for (int i = 0; i < 3; i++) {
				Random random = new Random();
				int randomzahl = random.nextInt(mapnamenumber);
				
				if(!mapsinlist.contains(randomzahl)) {
					mapsinlist.add(randomzahl);
					plugin.mapstovote.add(plugin.getConfig().getString("ragemode.mapnames.mapnames." + randomzahl));
				} else {
					i--;
				}
			}
		}
	}
	
	public void AllMapsAdd(){
		int mapnamenumber = plugin.getConfig().getInt("ragemode.mapnames.mapnamenumber");
		
		for (int i = 0; i < mapnamenumber; i++) {	
			plugin.maps.add(plugin.getConfig().getString("ragemode.mapnames.mapnames." + i));
		}
	}
		
	// Put Votes on null
	public void Mapvotenull() {
		for (String all : plugin.mapstovote) {
			plugin.votes.put(all, 0);
		}
		
		for(int i = 0; i < plugin.voted.size(); i++) {
			plugin.voted.remove(0);
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		
		//Return the Map list
		if(cmd.getName().equalsIgnoreCase("list")){
			//Check if Ingame
			if(Main.status == Main.Status.LOBBY) {
				getListCommand(player);
				
			//Ingame
			} else if(Main.status == Status.PRE_LOBBY){
				player.sendMessage(Strings.error_voting_finished);
			} else {
				player.sendMessage(Main.pre + "§eMap§8: §6" + plugin.votedmap);
			}
		}
		
		//Vote System
		if(cmd.getName().equalsIgnoreCase("vote")){
			if(args.length == 0) {
				if(Main.status == Status.LOBBY) getListCommand(player);
				else player.sendMessage(Strings.error_voting_finished);
				
			} else if(args.length > 0 && args.length < 2){
				//If Ingame
				if(Main.status == Status.LOBBY){
					//Check of Player has also voted
					if(!plugin.voted.contains(player.getName())){
						//Check of Map is in Maplist
						if(plugin.mapstovote.contains(args[0].toLowerCase())) {
						
							//Get all Already votes
							int votes = plugin.votes.get(args[0].toLowerCase());
							votes++;
							//Put all votes again on
							plugin.votes.put(args[0].toLowerCase(), votes);
						
							player.sendMessage(Strings.votes_you_have_voted + args[0]);
							//Put the Player to already voted
							plugin.voted.add(player.getName());
						}else{
							player.sendMessage(Strings.error_map_cannot_vote);
						}
					}else{
						player.sendMessage(Strings.error_you_already_voted);
					}
				}else{
					player.sendMessage(Strings.error_voting_finished);
				}
			} else {
				player.sendMessage(Strings.error_map_cannot_vote);
			}
		}
		
		return true;
	}
	
	public void getListCommand(Player player){
		if(Main.status == Status.LOBBY){
			player.sendMessage(Strings.votes_vote_for_map);
			for(String all : plugin.mapstovote){
				player.sendMessage(pre + "§3- §6" + all + "§3: §c" + plugin.votes.get(all));
			}
		}else{
			player.sendMessage(Strings.error_voting_finished);
		}
	}
	
	public void getListBroadcast(){
		Bukkit.broadcastMessage(Strings.votes_vote_for_map);
		for(String all : plugin.mapstovote){
			Bukkit.broadcastMessage(pre + "§3- §6" + all + "§8: §c" + plugin.votes.get(all));
		}
	}
	
	public void getResult() {
		
		int max = 0;
		for(int i : plugin.votes.values()){
			if(i > max){
				max = i;
			}
		}
		
		for(String all : plugin.votes.keySet()){
			if(plugin.votes.get(all) == max){
				 mapwinner = all;
				 plugin.votedmap = mapwinner;
			}
		}
	}
}
