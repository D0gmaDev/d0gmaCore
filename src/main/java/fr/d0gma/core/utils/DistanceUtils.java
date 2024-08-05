package fr.d0gma.core.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class DistanceUtils {

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

    public static char getArrowCharByAngle(Angle angle) {
        return switch (angle) {
            case Angle.PositiveDegrees(double degrees) -> convertDegreeAngleToArrow(degrees);
            case Angle.Unknown.DIFFERENT_WORLD -> ' ';
            case Angle.Unknown.TOO_CLOSE -> '✖';
        };
    }

    private static char convertDegreeAngleToArrow(double angle) {
        if (angle < 22.5 && angle >= 0.0 || angle > 337.5) {
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
        } else /*if (angle < 337.5 && angle > 292.5)*/ { // useless check, always true if the angle is in range
            return '⬉';
        }
    }

    public static Angle getAngleBetweenPlayerAndLocation(Player player, Location location) {
        Location playerLocation = player.getLocation();

        if (playerLocation.getWorld() != location.getWorld()) {
            return Angle.Unknown.DIFFERENT_WORLD;
        }

        if (playerLocation.getBlockX() == location.getBlockX() && playerLocation.getBlockZ() == location.getBlockZ()) {
            return Angle.Unknown.TOO_CLOSE;
        }

        double xDistance = playerLocation.getBlockX() - location.getBlockX();
        double zDistance = playerLocation.getBlockZ() - location.getBlockZ();

        return new Angle.PositiveDegrees(getAngle(player, zDistance, xDistance));
    }

    private static double getAngle(Player player, double zDistance, double xDistance) {
        double angle = 180. * Math.atan(Math.abs(zDistance) / Math.abs(xDistance)) / Math.PI;

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

    public sealed interface Angle {

        enum Unknown implements Angle {
            DIFFERENT_WORLD,
            TOO_CLOSE
        }

        record PositiveDegrees(double degrees) implements Angle {

            public PositiveDegrees {
                if (degrees < 0 || degrees >= 360.) {
                    throw new IllegalArgumentException("Degrees must be between 0 included and 360 excluded.");
                }
            }
        }
    }
}
