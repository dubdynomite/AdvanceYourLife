package com.maverickmartyn.advanceyourlife;

import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class CommandAwardLife implements CommandExecutor {

    private static final String SHOWLIVES = "showlives";

    Scoreboard board;

    public CommandAwardLife(Scoreboard board) {
        super();
        this.board = board;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            // Here we need to give items to our player
            if (player.hasPermission("advanceyourlife.awardlife")) {
                if (args.length > 0) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (Objects.equals(p.getName(), args[0])) {
                            Objective obj = board.getObjective(SHOWLIVES);
                            Score curScore = obj.getScore(p.getDisplayName());
                            curScore.setScore(curScore.getScore() + 1);
                            Bukkit.broadcastMessage(p.getDisplayName() + " has been awarded a new life!");
                            return true;
                        }
                    }
                    sender.sendMessage("Player not found. Check the name and try again.");
                    return false;                    
                }
                else {
                    Objective obj = board.getObjective(SHOWLIVES);
                    Score curScore = obj.getScore(player.getDisplayName());
                    curScore.setScore(curScore.getScore() + 1);
                    Bukkit.broadcastMessage(player.getDisplayName() + " has been awarded a new life!");
                    return true;
                }
            }
            sender.sendMessage("Access denied. Only OPs can do that!");
            return false;
        }
        else {
            sender.sendMessage("This command can not be run from the console at the moment, only as a player.");
            return false;
        }
    }
    
}
