package at.dafnik.ragemode.Main;

import org.bukkit.entity.Player;

public class PowerSystem {
	
	public static int getPower(Player p){
		switch(getType(p)){
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
	
	private static Type getType(Player p){
		if(p.hasPermission("ragemode.admin")) return Type.ADMIN;
		if(p.hasPermission("ragemode.moderator")) return Type.MODERATOR;
		if(p.hasPermission("ragemode.youtuber")) return Type.YOUTUBER;
		if(p.hasPermission("ragemode.premium")) return Type.PREMIUM;
		return Type.USER;
	}
	
	//User Type
	private static enum Type{
		USER, PREMIUM, YOUTUBER, MODERATOR, ADMIN;
	}
}
