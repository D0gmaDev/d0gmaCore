package fr.d0gma.core.proxy;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class ProxyMessagingService {

    private static JavaPlugin plugin;

    public static void initialize(JavaPlugin plugin) {
        ProxyMessagingService.plugin = plugin;
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
    }

    public static void connect(Player player, String server) {
        sendData(player, "Connect", server);
    }

    public static void connectOther(Player sender, String targetName, String server) {
        sendData(sender, "ConnectOther", targetName, server);
    }

    public static void kickFromProxy(Player sender, String targetName, Component reason) {
        sendData(sender, "KickPlayer", targetName, LegacyComponentSerializer.legacySection().serialize(reason));
    }

    public static void sendMessage(Player sender, String targetName, Component message) {
        sendData(sender, "MessageRaw", targetName, GsonComponentSerializer.gson().serialize(message));
    }

    public static void sendMessage(Player sender, String targetName, String message) {
        sendData(sender, "Message", targetName, message);
    }

    private static void sendData(Player player, String... utfData) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        Arrays.stream(utfData).forEach(out::writeUTF);

        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }
}
