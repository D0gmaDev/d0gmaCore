package fr.d0gma.core.world.anchor;

import fr.d0gma.core.Core;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnchorService {

    private static final List<AnchorSource> sources = new ArrayList<>();

    public static void initializeWorld(World world) {
        Core.getPlugin().getLogger().info("[Anchor] Initializing world " + world.getName());
        addSource(new AnchorWorldSource(world));
        Core.getPlugin().getLogger().info("[Anchor] Initialized world " + world.getName());
    }

    public static void unregisterWorld(World world) {
        Core.getPlugin().getLogger().info("[Anchor] Unregistering world " + world.getName());
        new ArrayList<>(sources).stream()
                .filter(source -> source instanceof AnchorWorldSource anchorWorldSource && world.equals(anchorWorldSource.getWorld()))
                .forEach(AnchorService::removeSource);
        Core.getPlugin().getLogger().info("[Anchor] Unregistered world " + world.getName());
    }


    public static Optional<Anchor> findOne(String key) {
        return sources.stream().map(anchorSource -> anchorSource.findOne(key)).findAny().orElse(Optional.empty());
    }

    public static List<Anchor> findMany(String key) {
        return sources.stream().map(anchorSource -> anchorSource.findMany(key)).flatMap(List::stream).toList();
    }

    public static List<Anchor> findAll() {
        return sources.stream().map(AnchorSource::findAll).flatMap(List::stream).toList();
    }

    private static void addSource(AnchorSource source) {
        sources.add(source);
    }

    private static void removeSource(AnchorSource source) {
        sources.remove(source);
    }
}
