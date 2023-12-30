package fr.d0gma.core;

import fr.d0gma.core.proxy.ProxyMessagingService;
import fr.d0gma.core.team.ScoreboardTeamService;
import fr.d0gma.core.timer.RunnableHelper;
import fr.d0gma.core.world.LootModifierListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Core {

    public static void initialize(JavaPlugin plugin) {
        RunnableHelper.setPlugin(plugin);
        ProxyMessagingService.initialize(plugin);
        ScoreboardTeamService.initialize(plugin);
        plugin.getServer().getPluginManager().registerEvents(new LootModifierListener(plugin), plugin);
    }

}
