package fr.d0gma.core.world.anchor;

import org.bukkit.Location;

import java.util.List;
import java.util.Objects;

public record Anchor(String key, List<String> args, Location location) {

    public Anchor {
        Objects.requireNonNull(key);
        Objects.requireNonNull(args);
        Objects.requireNonNull(location);
    }

    public Location blockLocation() {
        return location.getBlock().getLocation();
    }

    @Override
    public String toString() {
        return "Anchor (" + key + ") [" + String.join(",", args) + "]: " + location;
    }
}
