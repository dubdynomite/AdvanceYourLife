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

public class CommandGiveLife implements CommandExecutor {

    private static final String SHOWLIVES = "showlives";
    
    Scoreboard board;

    public CommandGiveLife(Scoreboard board) {
        super();
        this.board = board;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("advanceyourlife.givelife")) {
                Objective obj = board.getObjective(SHOWLIVES);
                Score gvrLives = obj.getScore(player.getDisplayName());
                if (gvrLives.getScore() > 1) {
                // Here we need to give items to our player
                    if (args.length > 0) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (Objects.equals(p.getName(), args[0])) {
                                Score lives = obj.getScore(p.getDisplayName());
                                lives.setScore(lives.getScore() + 1);
                                p.sendMessage("Congratulations " + p.getName() + "! You have been given another life.");
                                p.setGameMode(GameMode.SURVIVAL);
                                gvrLives.setScore(gvrLives.getScore() - 1);
                                player.sendMessage("Congratulations " + player.getName() + "! You have given another life to " + p.getName() + ".");
                                return true;
                             }

                    }
                    sender.sendMessage("Player not found. Check the name and try again.");
                    return false;
                    }
                    else {
                        sender.sendMessage("Who are you trying to give a life to? You can't give lives to everyone!");
                        return false;
                    }
                }
                else {
                    sender.sendMessage("Not enough available lives to give.");
                    return false;
                }

            }
            else {
                sender.sendMessage("This command can not be completed.");
                return false;
            }

        }
        sender.sendMessage("This command can not be run from the console at the moment, only as a player.");
        return false;      
    }   
}