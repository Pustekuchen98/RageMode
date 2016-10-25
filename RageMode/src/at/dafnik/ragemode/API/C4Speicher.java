package at.dafnik.ragemode.API;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class C4Speicher {

	private List<Block> c4blocks = new ArrayList<>();
	private Player player = null;
	
	public void addC4(Block block) {
		if(block.getType() == Material.STONE_BUTTON) c4blocks.add(block);
		else System.out.println("C4Speicher! Not saveable block!");
	}
	
	/* Not used
	public void removeC4(Block block) {
		c4blocks.remove(block);
	}*/
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void detonateAll() {
		for(Block blocks : c4blocks) {
			new Explosion("c4", blocks.getLocation(), player);
			blocks.setType(Material.AIR);
		}

		c4blocks = null;
		c4blocks = new ArrayList<>();
	}
	
	/* Not used
	public void detonate(Block block) {
		if(blocks.contains(block)) {
			new Explosion("c4", block.getLocation(), player);
			c4blocks.setType(Material.AIR);
			
		} else return;
		
		c4blocks.remove(block);
	}*/
}
