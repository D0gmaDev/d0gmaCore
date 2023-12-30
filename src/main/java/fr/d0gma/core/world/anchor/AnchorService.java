package fr.d0gma.core.world.anchor;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class AnchorService {

    private static final Logger logger = Bukkit.getLogger();

    private static final List<AnchorSource> sources = new ArrayList<>();

    public static void initializeWorld(World world) {
        logger.info("[Anchor] Initializing world " + world.getName());
        addSource(new AnchorWorldSource(world));
        logger.info("[Anchor] Initialized world " + world.getName());
    }

    public static void unregisterWorld(World world) {
        logger.info("[Anchor] Unregistering world " + world.getName());
        new ArrayList<>(sources).stream()
                .filter(source -> source instanceof AnchorWorldSource anchorWorldSource && world.equals(anchorWorldSource.getWorld()))
                .forEach(AnchorService::removeSource);
        logger.info("[Anchor] Unregistered world " + world.getName());
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
