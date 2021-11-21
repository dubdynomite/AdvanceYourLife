package com.maverickmartyn.advanceyourlife;

import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class CommandRessurrect implements CommandExecutor {

    Scoreboard board;

    public CommandRessurrect(Scoreboard board) {
        super();
        this.board = board;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            // Here we need to give items to our player
            if (player.hasPermission("advanceyourlife.ressurrect")) {
                if (args.length > 0) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (Objects.equals(p.getName(), args[0])) {
                            p.setGameMode(GameMode.SURVIVAL);
                            board.getObjective("showlives").getScore(p.getDisplayName()).setScore(1);
                            Bukkit.broadcastMessage(p.getDisplayName() + " has been ressurrected by an unknown force!");
                            return true;
                        }
                    }
                    sender.sendMessage("Player not found. Check the name and try again.");
                    return false;
                }
                else {
                    player.setGameMode(GameMode.SURVIVAL);
                    board.getObjective("showlives").getScore(player.getDisplayName()).setScore(1);
                    Bukkit.broadcastMessage(player.getDisplayName() + " has been ressurrected by an unknown force!");
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
