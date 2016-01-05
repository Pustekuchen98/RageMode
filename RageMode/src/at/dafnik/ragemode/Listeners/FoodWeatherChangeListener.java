package at.dafnik.ragemode.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class FoodWeatherChangeListener implements Listener{
	
	//Foodlevel cannot change
	@EventHandler
	public void FoodChange(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}
		
	//Weather cannot Change
	@EventHandler
	public void WeahterChange(WeatherChangeEvent event){
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onAchivement(PlayerAchievementAwardedEvent event){
		event.setCancelled(true);
	}
}
