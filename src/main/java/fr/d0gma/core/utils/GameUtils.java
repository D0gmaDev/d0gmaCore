package fr.d0gma.core.utils;

import fr.d0gma.core.world.VoidGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.HumanEntity;

import java.util.List;
import java.util.Objects;

public class GameUtils {

    public static void dropExperience(Location location, int experience) {
        if (experience <= 0) {
            return;
        }

        ExperienceOrb experienceOrb = Objects.requireNonNull(location.getWorld()).spawn(location, ExperienceOrb.class);
        experienceOrb.setExperience(experience);
    }

    public static void broadcastSound(Sound sound, float volume, float pitch) {
        Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), sound, volume, pitch));
    }

    public static void broadcastSound(String sound, float volume, float pitch) {
        Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), sound, volume, pitch));
    }

    public static World createVoidWorld(String name) {
        return new WorldCreator(name).generator(new VoidGenerator()).createWorld();
    }

    public static List<String> getOnlinePlayersPrefixed(String prefix) {
        String lowerPrefix = prefix.toLowerCase();
        return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).filter(s -> s.toLowerCase().startsWith(lowerPrefix)).toList();
    }
}
