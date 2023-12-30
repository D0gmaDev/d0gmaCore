package fr.d0gma.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class GameUtils {

    private static final double PI = 3.14;

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

    public static String getDistanceBetweenLocations(Location firstLocation, Location secondLocation) {
        if (firstLocation.getWorld() != secondLocation.getWorld()) {
            return "?";
        }

        double xDistance = Math.abs(secondLocation.getX() - firstLocation.getX());
        double zDistance = Math.abs(secondLocation.getZ() - firstLocation.getZ());

        double distance = Math.sqrt((xDistance * xDistance) + (zDistance * zDistance));
        return String.valueOf((int) distance);
    }

    public static String getDistanceBetweenPlayerAndLocation(Player player, Location location) {
        return getDistanceBetweenLocations(player.getLocation(), location);
    }

    public static char getArrowCharByAngle(double angle) {
        if (angle == -2.0) {
            return ' ';
        } else if (angle == -1.0) {
            return '✖';
        } else if (angle < 22.5 && angle >= 0.0 || angle > 337.5) {
            return '⬆';
        } else if (angle < 67.5 && angle > 22.5) {
            return '⬈';
        } else if (angle < 112.5 && angle > 67.5) {
            return '➡';
        } else if (angle < 157.5 && angle > 112.5) {
            return '⬊';
        } else if (angle < 202.5 && angle > 157.5) {
            return '⬇';
        } else if (angle < 247.5 && angle > 202.5) {
            return '⬋';
        } else if (angle < 292.5 && angle > 247.5) {
            return '⬅';
        } else if (angle < 337.5 && angle > 292.5) {
            return '⬉';
        } else {
            return ' ';
        }
    }

    /**
     * Special cases:
     * <br> -2. if the player is not in location's world
     * <br> -1. if the player is at location's X and Z coordinates.
     *
     * @return the computed angle in degrees.
     */
    public static double getAngleBetweenPlayerAndLocation(Player player, Location location) {
        Location playerLocation = player.getLocation();

        if (playerLocation.getWorld() != location.getWorld()) {
            return -2.;
        }

        if (playerLocation.getBlockX() == location.getBlockX() && playerLocation.getBlockZ() == location.getBlockZ()) {
            return -1.;
        }

        double xDistance = playerLocation.getBlockX() - location.getBlockX();
        double zDistance = playerLocation.getBlockZ() - location.getBlockZ();

        double angle = 180. * Math.atan(Math.abs(zDistance) / Math.abs(xDistance)) / PI;

        if (xDistance < 0. || zDistance < 0.) {
            if (xDistance < 0. && zDistance >= 0.) {
                angle = 180. - angle;
            } else if (xDistance <= 0.) {
                angle += 180.;
            } else {
                angle = 360. - angle;
            }
        }
        if ((angle += 270.) >= 360.) {
            angle -= 360.;
        }
        if ((angle -= player.getEyeLocation().getYaw() + 180.f) <= 0.) {
            angle += 360.;
        }
        if (angle <= 0.) {
            angle += 360.;
        }
        return angle;
    }

    public static char getArrowCharByAngleBetweenPlayerAndLocation(Player player, Location location) {
        return getArrowCharByAngle(getAngleBetweenPlayerAndLocation(player, location));
    }

    public static String getArrowCharAndDistanceBetweenPlayerAndLocation(Player player, Location location) {
        char arrowChar = getArrowCharByAngleBetweenPlayerAndLocation(player, location);
        return (arrowChar == ' ' ? "" : arrowChar + " ") + getDistanceBetweenPlayerAndLocation(player, location);
    }

    public static World createVoidWorld(String name) {
        return new WorldCreator(name).generator("Game").createWorld();
    }

    public static List<String> getOnlinePlayersPrefixed(String prefix) {
        String lowerPrefix = prefix.toLowerCase();
        return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).filter(s -> s.toLowerCase().startsWith(lowerPrefix)).toList();
    }
}
