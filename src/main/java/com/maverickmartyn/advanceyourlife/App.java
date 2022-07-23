package com.maverickmartyn.advanceyourlife;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

/**
 * Hello world!
 *
 */
public class App extends JavaPlugin {
    
    @Override
    public void onEnable() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        
        Objective objective = board.getObjective("showlives");
        if (objective == null) {
            board.registerNewObjective("showlives", "lives", "Lives", RenderType.INTEGER);
        }
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        
        for(Player online : Bukkit.getOnlinePlayers()){
            online.setScoreboard(board);
            Score lives = objective.getScore(online.getDisplayName());
            if (!lives.isScoreSet()) {
                lives.setScore(1);
            }
        }

        getServer().getPluginManager().registerEvents(new MyListener(board), this);
        this.getCommand("ressurrect").setExecutor(new CommandRessurrect(board));
        this.getCommand("getlives").setExecutor(new CommandGetLives(board));
        this.getCommand("givelife").setExecutor(new CommandGiveLife(board));
        this.getCommand("awardlife").setExecutor(new CommandAwardLife(board));
        getLogger().info("AdvanceYourLife loaded. Hello!");
    }

    @Override
    public void onDisable() {
        getLogger().info("AdvanceYourLife unloaded.");
    }
}