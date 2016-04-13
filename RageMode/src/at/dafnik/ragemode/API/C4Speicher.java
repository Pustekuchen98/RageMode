package at.dafnik.ragemode.API;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class C4Speicher {

	private List<Block> blocks = new ArrayList<>();
	private Player player = null;
	
	public void addC4(Block block) {
		if(block.getType() == Material.STONE_BUTTON) blocks.add(block);
		else System.out.println("C4Speicher! Not saveable");
	}
	
	/* Not used
	public void removeC4(Block block) {
		blocks.remove(block);
	}*/
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void detonateAll() {
		for(Block blocks : blocks) {
			new Explosion("c4", blocks.getLocation(), player);
			blocks.setType(Material.AIR);
		}
		
		blocks = null;
		blocks = new ArrayList<>();
	}
	/* Not used
	public void detonate(Block block) {
		if(blocks.contains(block)) {
			new Explosion("c4", block.getLocation(), player);
			block.setType(Material.AIR);
			
		} else return;
		
		blocks.remove(block);
	}*/
}
