package com.maverickmartyn.advanceyourlife;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class MyListener implements Listener {
    /**
     * The objective name.
     */
    private static final String SHOWLIVES = "showlives";

    Scoreboard board;

    public MyListener(Scoreboard board) {
        super();
        this.board = board;
    }
     
    @EventHandler
    public void onPlayerDies(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Objective obj = board.getObjective(SHOWLIVES);
        Score lives = obj.getScore(player.getDisplayName());

        lives.setScore(lives.getScore() - 1);

        if (lives.getScore() < 0) {
            //spectator mode
            event.setDeathMessage(event.getDeathMessage() + "\n" + event.getEntity().getPlayer() + " ran out of lives.");
            player.setGameMode(GameMode.SPECTATOR);
            lives.setScore(0);
        }
        else {
            event.setDeathMessage(event.getDeathMessage() + " and lost a life.");
            // event.getDrops().clear();
            // event.setKeepInventory(true);
            //allow respawn.
        }
    }
     
    @EventHandler
    public void onPlayerAdvances(PlayerAdvancementDoneEvent event) {
        String keyStr = event.getAdvancement().getKey().toString();
        if (!keyStr.startsWith("minecraft:recipes")) {
            Player player = event.getPlayer();
            if (keyStr.startsWith("minecraft:") && keyStr.contains("/root")) {
                player.sendMessage("More Advancements have been revealed!");
            }
            Objective obj = board.getObjective(SHOWLIVES);
            Score lives = obj.getScore(player.getDisplayName());
            lives.setScore(lives.getScore() + 1);
            player.sendMessage("Congratulations " + player.getName() + "! You have earned another life.");
        }
    }
     
    @EventHandler
    public void onPlayerJoins(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Objective obj = board.getObjective(SHOWLIVES);
        Score lives = obj.getScore(player.getDisplayName());
        if (!lives.isScoreSet()) {
            lives.setScore(3);
            player.sendMessage("Welcome " + player.getName() + "! You have been given 3 lives to start with. Be careful!");
        }
        else if (lives.getScore() <= 0) {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage("Welcome " + player.getName() + "! You have no lives remaining. You must petition for resurrection!");
        }
        else if (lives.getScore() > 0) {
            player.setGameMode(GameMode.SURVIVAL);
        }
        else {
            player.sendMessage("You have " + lives.getScore() + " lives.");
        }
        player.setScoreboard(board);
    }
}
