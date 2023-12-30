package fr.d0gma.core.world.schematic;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.Optional;
import java.util.function.Consumer;

public final class SchematicPasteSpec {

    private boolean spawnEntities = true;
    private boolean pasteAir = false;

    private Consumer<Block> onBlockPlace;
    private Consumer<Entity> onEntitySpawn;


    private SchematicPasteSpec() {
    }

    public static SchematicPasteSpec create() {
        return new SchematicPasteSpec();
    }

    public SchematicPasteSpec withEntities(boolean spawnEntities) {
        this.spawnEntities = spawnEntities;
        return this;
    }

    public SchematicPasteSpec pasteAir(boolean pasteAir) {
        this.pasteAir = pasteAir;
        return this;
    }

    public SchematicPasteSpec onBlockPlace(Consumer<Block> onBlockPlace) {
        this.onBlockPlace = onBlockPlace;
        return this;
    }

    public SchematicPasteSpec onEntitySpawn(Consumer<Entity> onEntitySpawn) {
        this.onEntitySpawn = onEntitySpawn;
        return this;
    }

    public boolean doesSpawnEntities() {
        return spawnEntities;
    }

    public boolean doesPasteAir() {
        return pasteAir;
    }

    public Optional<Consumer<Block>> getBlockPlaceConsumer() {
        return Optional.ofNullable(onBlockPlace);
    }

    public Optional<Consumer<Entity>> getEntitySpawnConsumer() {
        return Optional.ofNullable(onEntitySpawn);
    }
}
