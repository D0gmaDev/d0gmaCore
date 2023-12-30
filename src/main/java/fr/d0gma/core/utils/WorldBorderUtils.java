package fr.d0gma.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Objects;

public class WorldBorderUtils {

    public static void setBorderCenterForAllWorlds(double x, double z) {
        Bukkit.getWorlds().forEach(world -> world.getWorldBorder().setCenter(x, z));
    }

    public static void setBorderCenter(World world, double x, double z) {
        world.getWorldBorder().setCenter(x, z);
    }

    public static void setBorderSizeForAllWorlds(double diameter) {
        Bukkit.getWorlds().forEach(world -> world.getWorldBorder().setSize(diameter));
    }

    public static void setBorderSizeWithDelayForAllWorlds(double diameter, long delay) {
        Bukkit.getWorlds().forEach(world -> world.getWorldBorder().setSize(diameter, delay));
    }

    public static void setBorderSizeWithSpeedForAllWorlds(double diameter, double speed) {
        Bukkit.getWorlds().forEach(world -> setBorderSizeWithSpeed(world, diameter, speed));
    }

    public static void setBorderDamagesAmountForAllWorlds(double damages) {
        Bukkit.getWorlds().forEach(world -> world.getWorldBorder().setDamageAmount(damages));
    }

    public static void setBorderDamagesDistanceForAllWorlds(double distance) {
        Bukkit.getWorlds().forEach(world -> world.getWorldBorder().setDamageBuffer(distance));
    }

    public static void setBorderSize(World world, double diameter) {
        world.getWorldBorder().setSize(diameter);
    }

    public static void setBorderSizeWithDelay(World world, double diameter, long delay) {
        world.getWorldBorder().setSize(diameter, delay);
    }

    public static void setBorderSizeWithSpeed(World world, double diameter, double speed) {
        double sizeToReduce = world.getWorldBorder().getSize() - diameter;
        long delay = Double.valueOf(sizeToReduce / speed).longValue();

        world.getWorldBorder().setSize(diameter, delay);
    }

    public static void setBorderDamagesAmount(World world, double damages) {
        world.getWorldBorder().setDamageAmount(damages);
    }

    public static void setBorderDamagesDistance(World world, double distance) {
        world.getWorldBorder().setDamageBuffer(distance);
    }

    public static boolean isOutsideOfBorder(Entity entity) {
        return getOutsideFacing(entity) != Facing.UNKNOWN;
    }

    public static void teleportOutOfBorderEntity(Entity entity) {

        if (!isOutsideOfBorder(entity)) {
            return;
        }

        Location location = entity.getLocation().clone();
        Location center = entity.getWorld().getWorldBorder().getCenter();

        Vector vector = location.clone().subtract(center.clone()).toVector();
        double max = Math.max(Math.abs(vector.getX()), Math.abs(vector.getZ()));
        double multRatio = Math.sqrt(Math.pow(vector.getX() / max, 2) + Math.pow(vector.getZ() / max, 2));
        Vector finalVector = vector.normalize().multiply(Math.max(0, entity.getWorld().getWorldBorder().getSize() / 2 - 10)).multiply(multRatio);
        Location upLoc = center.clone().add(finalVector);

        upLoc.setY(Objects.requireNonNull(upLoc.getWorld()).getHighestBlockYAt(upLoc) + 1.2);
        entity.teleport(new Location(location.getWorld(), upLoc.getX() + 0.5, upLoc.getY(), upLoc.getZ() + 0.5, entity.getLocation().getYaw(), entity.getLocation().getPitch()));

        if (entity instanceof Player player) {
            player.playSound(entity.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1F, 50F);
        }
    }

    private static Facing getOutsideFacing(Entity entity) {
        double x = entity.getLocation().getX();
        double z = entity.getLocation().getZ();

        double size = entity.getWorld().getWorldBorder().getSize() / 2;

        if (x > 0 && x > size) {
            return Facing.EAST; //1
        }
        if (x < 0 && x < -size) {
            return Facing.WEST; //2
        }

        if (z > 0 && z > size) {
            return Facing.SOUTH; //3
        }
        if (z < 0 && z < -size) {
            return Facing.NORTH; //4
        }

        return Facing.UNKNOWN;
    }

    private enum Facing {

        NORTH,
        EAST,
        WEST,
        SOUTH,

        UNKNOWN

    }
}
