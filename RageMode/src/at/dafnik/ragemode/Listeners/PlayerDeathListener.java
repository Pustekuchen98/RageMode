package at.dafnik.ragemode.Listeners;

import java.util.HashMap;

import at.dafnik.ragemode.API.Holograms;
import at.dafnik.ragemode.API.Strings;
import at.dafnik.ragemode.API.Title;
import at.dafnik.ragemode.Main.Library;
import at.dafnik.ragemode.Main.Main;
import at.dafnik.ragemode.MySQL.SQLCoins;
import at.dafnik.ragemode.MySQL.SQLStats;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private int bowkill;
    private int knifekill;
    private int knifedeath;
    private int suicide;
    private int combataxekill;
    private int grenadekill;
    private int claymorekill;
    private int minekill;
    private int c4kill;
    private int killstreakpoints;

    private HashMap<Player, Integer> killstreak = new HashMap<>();

    public PlayerDeathListener() {
        this.bowkill = Main.getInstance().getConfig().getInt("ragemode.points.bowkill");
        this.knifekill = Main.getInstance().getConfig().getInt("ragemode.points.knifekill");
        this.knifedeath = Main.getInstance().getConfig().getInt("ragemode.points.knifedeath");
        this.suicide = Main.getInstance().getConfig().getInt("ragemode.points.suicide");
        this.combataxekill = Main.getInstance().getConfig().getInt("ragemode.points.combat_axekill");
        this.grenadekill = Main.getInstance().getConfig().getInt("ragemode.points.grenadekill");
        this.claymorekill = Main.getInstance().getConfig().getInt("ragemode.points.clay_morekill");
        this.minekill = Main.getInstance().getConfig().getInt("ragemode.points.minekill");
        this.c4kill = Main.getInstance().getConfig().getInt("ragemode.points.c4");
        this.killstreakpoints = Main.getInstance().getConfig().getInt("ragemode.points.killstreakpoints");
    }

    @EventHandler
    public void PlayDeathEvent(PlayerDeathEvent event) {
        event.setDroppedExp(0);
        event.getDrops().clear();

        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        //[Check] if MySQL add Death to Victim everytime he dies
        if (Main.isMySQL) SQLStats.addDeaths(victim.getUniqueId().toString(), 1);

        //[Check] if Killer is a Player
        if (killer instanceof Player) {

            if (killer == victim) {
                event.setDeathMessage(Main.pre + victim.getDisplayName() + Strings.kill_suicide);
                victim.sendMessage("§c§l" + this.suicide);

                addPoints(victim, this.suicide);
                if (Main.isMySQL) SQLStats.addSuicides(victim.getUniqueId().toString(), 1);

                createHologram(victim.getEyeLocation(), this.suicide);
                Library.bar.setTitle(killer.getDisplayName() + Strings.kill_killed + victim.getDisplayName() + Strings.kill_with + Strings.kill_suicide);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> Library.bar.setTitle(Strings.bossbar), 6 * 20);

            } else {

                if (victim.getMetadata("killedWith") != null && !victim.getMetadata("killedWith").isEmpty()) {
                    switch (victim.getMetadata("killedWith").get(0).asString()) {
                        case "bow":
                            make(event, killer, victim, Strings.kill_with_bow, this.bowkill);

                            break;

                        case "combataxe":
                            make(event, killer, victim, Strings.kill_with_combat_axe, this.combataxekill);
                            break;

                        case "grenade":
                            make(event, killer, victim, Strings.kill_with_grenade, this.grenadekill);

                            break;

                        case "mine":
                            make(event, killer, victim, Strings.kill_with_mine, this.minekill);

                            break;

                        case "claymore":
                            make(event, killer, victim, Strings.kill_with_claymore, this.claymorekill);

                            break;

                        case "c4":
                            make(event, killer, victim, Strings.kill_with_c4, this.c4kill);

                            break;

                        case "knife":
                            make(event, killer, victim, Strings.kill_with_knife, this.knifekill);

                            victim.sendMessage(Strings.kill_points_negative + this.knifedeath);
                            addPoints(victim, this.knifedeath);

                            break;

                        default:
                            event.setDeathMessage(Main.pre + victim.getDisplayName() + Strings.kill_unknown_killer);

                            break;
                    }
                } else {
                    event.setDeathMessage(Main.pre + victim.getDisplayName() + Strings.kill_unknown_killer);
                }

                victim.removeMetadata("killedWith", Main.getInstance());

            }

            if (killstreak.get(killer) == null) killstreak.put(killer, 1);
            else killstreak.replace(killer, killstreak.get(killer), killstreak.get(killer) + 1);

            if (killstreak.get(killer) == 3 || killstreak.get(killer) == 5 || killstreak.get(killer) == 7) {
                addPoints(killer, this.killstreakpoints);

                if (Main.isMySQL) SQLCoins.addCoins(killer.getUniqueId().toString(), 10);
                Bukkit.broadcastMessage(Main.pre + killer.getDisplayName() + Strings.killstreak);
            }

            killer.sendMessage(Main.pre + Strings.kill_your_points + String.valueOf(Library.playerpoints.get(killer)));
            killer.setPlayerListName(killer.getDisplayName() + " §8- [§6" + Library.playerpoints.get(killer) + "§8]");


        } else event.setDeathMessage(Main.pre + victim.getDisplayName() + Strings.kill_unknown_killer);

        //Auto Respawn
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> victim.spigot().respawn(), 30);

        //Killstreak
        if (killstreak.get(victim) == null) killstreak.put(victim, 0);
        else killstreak.replace(victim, killstreak.get(victim), 0);

        if (Library.playerpoints.get(victim) == null) victim.sendMessage(Main.pre + Strings.kill_your_points + "0");
        else
            victim.sendMessage(Main.pre + Strings.kill_your_points + String.valueOf(Library.playerpoints.get(victim)));

        victim.setPlayerListName(victim.getDisplayName() + " §8- [§6" + Library.playerpoints.get(victim) + "§8]");

    }

    private void make(PlayerDeathEvent event, Player killer, Player victim, String reason, int points) {
        event.setDeathMessage(Main.pre + killer.getDisplayName() + Strings.kill_killed + victim.getDisplayName() + Strings.kill_with + reason);

        killer.sendMessage(Strings.kill_points_plus + points);

        Library.bar.setTitle(killer.getDisplayName() + Strings.kill_killed + victim.getDisplayName() + Strings.kill_with + reason);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> Library.bar.setTitle(Strings.bossbar), 6 * 20);

        createHologram(victim.getEyeLocation(), points);

        if (Main.isMySQL) {
            String killerUUID = killer.getUniqueId().toString();

            switch (reason) {
                case "bow":
                    SQLStats.addBowKills(killerUUID, 1);
                    break;

                case "knife":
                    SQLStats.addKnifeKills(killerUUID, 1);
                    break;
                case "combataxe":
                    SQLStats.addAxtKills(killerUUID, 1);
                    break;

                default:
                    break;
            }

            SQLStats.addPoints(killerUUID, points);
            SQLStats.addKills(killerUUID, 1);
            SQLCoins.addCoins(killerUUID, 20);

            killer.sendMessage(Strings.commands_coins_added_0 + 20 + Strings.commands_coins_added_1);
        }

        addPoints(killer, points);

        if (!killer.isDead()) {
            killer.playSound(killer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1000.0F, 1.0F);
            Title.sendActionBar(killer, Strings.kill_your_points + String.valueOf(Library.playerpoints.get(killer)));
        }

        killer.sendMessage(Main.pre + Strings.kill_your_points + String.valueOf(Library.playerpoints.get(killer)));
        killer.setPlayerListName(killer.getDisplayName() + " §8- [§6" + Library.playerpoints.get(killer) + "§8]");
    }

    private void createHologram(Location location, int points) {
        Holograms holo = new Holograms(location, "§c+§6" + points + Strings.kill_holo_points);

        for (Player players : Bukkit.getOnlinePlayers()) {
            holo.display(players);
        }

        int hologramLifetime = 2 * 20;
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            for (Player players : Bukkit.getOnlinePlayers())
                holo.destroy(players);
        }, hologramLifetime);
    }

    private void addPoints(Player player, int newpoints) {
        if (Library.playerpoints.get(player) == null) {
            Library.playerpoints.put(player, 0);
        } else {
            int points = Library.playerpoints.get(player);
            if (points + newpoints < 0) points = 0;
            else {
                if (points + newpoints >= 0) {
                    points += newpoints;
                } else System.out.println(Strings.error_playerdeath_points);
            }

            Library.playerpoints.put(player, points);
        }
    }

}