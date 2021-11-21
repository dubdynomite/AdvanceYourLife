package com.maverickmartyn.advanceyourlife;

import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class CommandGetLives implements CommandExecutor {

    Scoreboard board;

    public CommandGetLives(Scoreboard board) {
        super();
        this.board = board;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            // Here we need to give items to our player
            if (player.hasPermission("advanceyourlife.getlives")) {
                if (args.length > 0) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (Objects.equals(p.getName(), args[0])) {
                            sender.sendMessage(p.getDisplayName() + " has " + board.getObjective("showlives").getScore(p.getDisplayName()).getScore() + " lives.");
                            return true;
                        }
                    }
                }
                else {
                    sender.sendMessage(player.getDisplayName() + " has " + board.getObjective("showlives").getScore(player.getDisplayName()).getScore() + " lives.");
                    return true;
                }
            }
        }
        return false;
    }
    
}
