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
			System.out.println(Strings.error_no_created_maps);
			return;
		} else {
			for (int i = 0; i < 3; i++) {
				Random random = new Random();
				int randomzahl = random.nextInt(mapnamenumber);
				
				if(!mapsinlist.contains(randomzahl)) {
					mapsinlist.add(randomzahl);
					Library.mapstovote.add(plugin.getConfig().getString("ragemode.mapnames.mapnames." + randomzahl));
				} else {
					i--;
				}
			}
		}
	}
	
	public void AllMapsAdd(){
		int mapnamenumber = plugin.getConfig().getInt("ragemode.mapnames.mapnamenumber");
		
		for (int i = 0; i < mapnamenumber; i++) {	
			Library.maps.add(plugin.getConfig().getString("ragemode.mapnames.mapnames." + i));
		}
	}
		
	// Put Votes on null
	public void Mapvotenull() {
		for (String all : Library.mapstovote) {
			Library.votes.put(all, 0);
		}
		
		for(int i = 0; i < Library.voted.size(); i++) {
			Library.voted.remove(0);
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
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
					player.sendMessage(Main.pre + "§eMap§8: §6" + Library.votedmap);
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
						if(!Library.voted.contains(player.getName())){
							//Check of Map is in Maplist
							if(Library.mapstovote.contains(args[0].toLowerCase())) {
							
								//Get all Already votes
								int votes = Library.votes.get(args[0].toLowerCase());
								votes++;
								//Put all votes again on
								Library.votes.put(args[0].toLowerCase(), votes);
							
								player.sendMessage(Strings.votes_you_have_voted + args[0]);
								//Put the Player to already voted
								Library.voted.add(player.getName());
								
							}else player.sendMessage(Strings.error_map_cannot_vote);
							
						} else player.sendMessage(Strings.error_you_already_voted);
						
					} else player.sendMessage(Strings.error_voting_finished);
					
				} else player.sendMessage(Strings.error_map_cannot_vote);
				
			}
			
			return true;
		}
		return true;
	}
	
	public void getListCommand(Player player){
		if(Main.status == Status.LOBBY){
			player.sendMessage(Strings.votes_vote_for_map);
			for(String all : Library.mapstovote){
				player.sendMessage(pre + "§7- §e" + all + "§8: §b" + Library.votes.get(all));
			}
		}else player.sendMessage(Strings.error_voting_finished);	
	}
	
	public void getListBroadcast(){
		Bukkit.broadcastMessage(Strings.votes_vote_for_map);
		for(String all : Library.mapstovote){
			Bukkit.broadcastMessage(pre + "§7- §e" + all + "§8: §b" + Library.votes.get(all));
		}
	}
	
	public void getResult() {
		int max = 0;
		for(int i : Library.votes.values()){
			if(i > max){
				max = i;
			}
		}
		
		for(String all : Library.votes.keySet()){
			if(Library.votes.get(all) == max){
				 mapwinner = all;
				 Library.votedmap = mapwinner;
			}
		}
	}
}
