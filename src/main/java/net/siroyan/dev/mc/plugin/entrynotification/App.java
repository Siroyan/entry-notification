package net.siroyan.dev.mc.plugin.entrynotification;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {
    @Override
    public void onEnable() {
        FileConfiguration conf = getConfig();
        DiscordGateway dg = new DiscordGateway(conf.getString("DiscordWebhookUrl"));
        getCommand("discord").setExecutor(new DiscordCommand(dg));
    }
}
