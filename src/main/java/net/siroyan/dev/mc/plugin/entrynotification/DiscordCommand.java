package net.siroyan.dev.mc.plugin.entrynotification;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DiscordCommand implements CommandExecutor {
    
    private DiscordGateway dg;

    public DiscordCommand(DiscordGateway dg) {
        this.dg = dg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("discord")) {
            if (args.length == 1) {
                Player player = Bukkit.getPlayer(sender.getName());
                try {
                    dg.postCommandMessage(player, args[0]);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
