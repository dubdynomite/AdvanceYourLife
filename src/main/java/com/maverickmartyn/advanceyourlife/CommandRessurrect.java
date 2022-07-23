package com.maverickmartyn.advanceyourlife;

import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class CommandRessurrect implements CommandExecutor {

    Scoreboard board;

    public CommandRessurrect(Scoreboard board) {
        super();
        this.board = board;
    }

    private static final String SHOWLIVES = "showlives";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            // Here we need to give items to our player
            if (player.hasPermission("advanceyourlife.ressurrect")) {
                if (args.length > 0) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (Objects.equals(p.getName(), args[0])) {
                            Objective obj = board.getObjective(SHOWLIVES);
                            Score lives = obj.getScore(p.getDisplayName());
                            if (lives.getScore() <= 0) {
                                p.setGameMode(GameMode.SURVIVAL);
                                lives.setScore(1);
                                Bukkit.broadcastMessage(p.getDisplayName() + " has been ressurrected by an unknown force!");
                                return true;
                            }
                            else {
                                 sender.sendMessage("Player cannot be resurrected - They are not dead!");
                                 return false;
                            }
                        }
                    }
                    sender.sendMessage("Player not found. Check the name and try again.");
                    return false;
                }
                else {
                    Objective obj = board.getObjective(SHOWLIVES);
                    Score lives = obj.getScore(player.getDisplayName());
                    if (lives.getScore() <= 0) {
                        player.setGameMode(GameMode.SURVIVAL);
                        lives.setScore(1);
                        Bukkit.broadcastMessage(player.getDisplayName() + " has been ressurrected by an unknown force!");
                        return true;
                    }
                    else {
                        Bukkit.broadcastMessage(player.getDisplayName() + " You are not dead. You cannot be resurrected!");
                        return false;
                    }    
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
