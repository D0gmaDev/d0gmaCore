package fr.d0gma.core.world.schematic;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

public sealed interface PasteArgument {

    enum Flag implements PasteArgument {
        IGNORE_AIR,
        IGNORE_ENTITIES
    }

    record OnBlockPlace(Consumer<Block> onPlace) implements PasteArgument {
    }

    record OnEntitySpawn(Consumer<Entity> onSpawn) implements PasteArgument {
    }

    static Optional<Consumer<Block>> placeConsumer(Set<PasteArgument> pasteArguments) {
        var consumers = pasteArguments.stream().flatMap(pasteArgument -> pasteArgument instanceof OnBlockPlace(
                var onPlace
        ) ? Stream.of(onPlace) : Stream.empty());
        return consumers.reduce(Consumer::andThen);
    }

    static Optional<Consumer<Entity>> spawnConsumer(Set<PasteArgument> pasteArguments) {
        var consumers = pasteArguments.stream().flatMap(pasteArgument -> pasteArgument instanceof OnEntitySpawn(
                var onSpawn
        ) ? Stream.of(onSpawn) : Stream.empty());
        return consumers.reduce(Consumer::andThen);
    }
}
