package fr.d0gma.core.world.schematic;

import fr.d0gma.core.world.schematic.PasteArgument.OnBlockPlace;
import fr.d0gma.core.world.schematic.PasteArgument.OnEntitySpawn;
import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.entity.Entity;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

record SchematicImpl(int version, short height, short width, short length, SchematicBlockData[] blocksData,
                     SchematicEntityData[] entitiesData, short[] blocks, int airData) implements Schematic {

    public SchematicImpl {
        if (height <= 0 || width <= 0 || length <= 0) {
            throw new IllegalArgumentException("non-positive schematic dimension");
        }

        Objects.requireNonNull(blocksData);
        Objects.requireNonNull(entitiesData);
        Objects.requireNonNull(blocks);
    }

    @Override
    public void paste(Location location) {
        paste(location, Set.of());
    }

    @Override
    public void paste(Location location, Consumer<Block> blockConsumer) {
        paste(location, Set.of(new OnBlockPlace(blockConsumer)));
    }

    @Override
    public void pasteWithoutEntities(Location location, Consumer<Block> blockConsumer) {
        paste(location, Set.of(new OnBlockPlace(blockConsumer), PasteArgument.Flag.IGNORE_ENTITIES));
    }

    @Override
    public void paste(Location location, Consumer<Block> blockConsumer, Consumer<Entity> entityConsumer) {
        paste(location, Set.of(new OnBlockPlace(blockConsumer), new OnEntitySpawn(entityConsumer)));
    }

    @Override
    public void paste(Location location, Set<PasteArgument> pasteArguments) {
        World world = location.getWorld();

        boolean ignoreAir = pasteArguments.contains(PasteArgument.Flag.IGNORE_AIR);
        boolean ignoreEntities = pasteArguments.contains(PasteArgument.Flag.IGNORE_ENTITIES);

        Optional<Consumer<Block>> placeConsumer = PasteArgument.placeConsumer(pasteArguments);
        Optional<Consumer<Entity>> spawnConsumer = PasteArgument.spawnConsumer(pasteArguments);

        int widthTimesLength = width * length;

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                int yStride = y * widthTimesLength;
                for (int z = 0; z < length; ++z) {
                    int zStride = z * width;
                    int index = yStride + zStride + x;
                    short blockId = blocks[index];

                    if (!ignoreAir || blockId != airData) {
                        Location blockLocation = new Location(world, (double) x + location.getX(), (double) y + location.getY(), (double) z + location.getZ());
                        blocksData[blockId].paste(blockLocation);
                        placeConsumer.ifPresent(blockConsumer -> blockConsumer.accept(blockLocation.getBlock()));
                    }
                }
            }
        }

        if (!ignoreEntities) {
            spawnConsumer.ifPresentOrElse(
                    entityConsumer -> Arrays.stream(entitiesData).map(entityData -> entityData.summon(location)).forEach(entityConsumer),
                    () -> Arrays.stream(entitiesData).forEach(entityData -> entityData.summon(location))
            );
        }
    }

    @Override
    public Schematic rotate(StructureRotation rotation) {

        if (rotation == StructureRotation.NONE) {
            return this;
        }

        int quart = switch (rotation) {
            case CLOCKWISE_90 -> 3;
            case CLOCKWISE_180 -> 2;
            case COUNTERCLOCKWISE_90 -> 1;
            default -> throw new IllegalStateException("Unexpected value: " + rotation);
        };

        int widthTimesLength = width * length;
        short[] newBlocks = new short[blocks.length];

        for (int i = 0; i < blocks.length; i++) {
            int y = i / widthTimesLength;
            int z = (i - y * widthTimesLength) / width;
            int x = i - y * widthTimesLength - z * width;
            var newCoords = getRotationNewCoords(width, length, x, z, quart);
            int newId = y * width * length + newCoords.y() * (quart % 2 == 0 ? width : length) + newCoords.x();
            newBlocks[newId] = blocks[i];
        }

        short newWidth = quart % 2 == 1 ? length : width;
        short newLength = quart % 2 == 1 ? width : length;

        SchematicBlockData[] newBlocksData = Arrays.stream(blocksData).map(blockData -> blockData.rotate(rotation)).toArray(SchematicBlockData[]::new);
        SchematicEntityData[] newEntitiesData = Arrays.stream(entitiesData).map(entityData -> entityData.rotate(rotation)).toArray(SchematicEntityData[]::new);

        return new SchematicImpl(version, height, newWidth, newLength, newBlocksData, newEntitiesData, newBlocks, airData);
    }

    @Override
    public Schematic flip(Axis axis) {
        return switch (axis) {
            case X -> flipX();
            case Y -> flipY();
            case Z -> flipZ();
        };
    }

    @Override
    public Schematic flipX() {
        short[] newBlocks = new short[blocks.length];

        for (int i = 0; i < blocks.length; i++) {
            int y = i / (width * length);
            int z = (i - y * width * length) / width;
            int x = i - y * width * length - z * width;
            int newId = y * width * length + (length - 1 - z) * width + x;
            newBlocks[newId] = blocks[i];
        }

        SchematicBlockData[] newBlocksData = Arrays.stream(blocksData).map(blockData -> blockData.flip(Axis.X)).toArray(SchematicBlockData[]::new);
        SchematicEntityData[] newEntitiesData = Arrays.stream(entitiesData).map(entityData -> entityData.flip(Axis.X)).toArray(SchematicEntityData[]::new);

        return new SchematicImpl(version, height, width, length, newBlocksData, newEntitiesData, newBlocks, airData);
    }

    @Override
    public Schematic flipY() {
        short[] newBlocks = new short[blocks.length];

        for (int i = 0; i < blocks.length; i++) {
            int y = i / (width * length);
            int z = (i - y * width * length) / width;
            int x = i - y * width * length - z * width;
            int height = blocks.length / (width * length);
            int newId = (height - 1 - y) * width * length + z * width + x;
            newBlocks[newId] = blocks[i];
        }

        SchematicBlockData[] newBlocksData = Arrays.stream(blocksData).map(blockData -> blockData.flip(Axis.Y)).toArray(SchematicBlockData[]::new);
        SchematicEntityData[] newEntitiesData = Arrays.stream(entitiesData).map(entityData -> entityData.flip(Axis.Y)).toArray(SchematicEntityData[]::new);

        return new SchematicImpl(version, height, width, length, newBlocksData, newEntitiesData, newBlocks, airData);
    }

    @Override
    public Schematic flipZ() {
        short[] newBlocks = new short[blocks.length];

        for (int i = 0; i < blocks.length; i++) {
            int y = i / (width * length);
            int z = (i - y * width * length) / width;
            int x = i - y * width * length - z * width;
            int newId = y * width * length + z * width + (width - 1 - x);
            newBlocks[newId] = blocks[i];
        }

        SchematicBlockData[] newBlocksData = Arrays.stream(blocksData).map(blockData -> blockData.flip(Axis.Z)).toArray(SchematicBlockData[]::new);
        SchematicEntityData[] newEntitiesData = Arrays.stream(entitiesData).map(entityData -> entityData.flip(Axis.Z)).toArray(SchematicEntityData[]::new);

        return new SchematicImpl(version, height, width, length, newBlocksData, newEntitiesData, newBlocks, airData);
    }

    private record Coords2D(int x, int y) {
    }

    private Coords2D getRotationNewCoords(int width, int length, int x, int z, int quart) {
        int[] coords = new int[]{x, z, width - 1 - x, length - 1 - z};
        return new Coords2D(coords[quart % 4], coords[(quart + 1) % 4]);
    }
}
