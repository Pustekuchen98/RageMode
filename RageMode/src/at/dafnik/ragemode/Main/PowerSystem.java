package at.dafnik.ragemode.Main;

import org.bukkit.entity.Player;

public class PowerSystem {
	
	public static int getPower(Player player){
		switch(getType(player)){
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
	
	private static Type getType(Player player){
		if(player.hasPermission("ragemode.admin")) return Type.ADMIN;
		if(player.hasPermission("ragemode.moderator")) return Type.MODERATOR;
		if(player.hasPermission("ragemode.youtuber")) return Type.YOUTUBER;
		if(player.hasPermission("ragemode.premium")) return Type.PREMIUM;
		return Type.USER;
	}
	
	//User Type
	private static enum Type{
		USER, PREMIUM, YOUTUBER, MODERATOR, ADMIN;
	}
}
