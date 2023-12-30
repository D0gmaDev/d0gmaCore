package fr.d0gma.core.world.schematic;

import fr.d0gma.core.world.Axis;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.entity.Entity;

import java.util.function.Consumer;

/**
 * Represents a Minecraft schematic, which is a 3D structure that can be loaded, manipulated, and then pasted into a world.
 * The schematic object should be considered as immutable.
 *
 * @see SchematicService the service to load schematics
 */
public interface Schematic {

    /**
     * Gets the schematic version.
     *
     * @return the version
     */
    int version();

    /**
     * Pastes the schematic at the specified location, without a block or entity consumer.
     *
     * @param location the location to paste the schematic at
     */
    void paste(Location location);

    /**
     * Pastes the schematic at the specified location, with a block consumer to customize block placement.
     *
     * @param location      the location to paste the schematic at
     * @param blockConsumer a consumer to customize the placement of each block in the schematic
     */
    void paste(Location location, Consumer<Block> blockConsumer);

    /**
     * Pastes the schematic at the specified location, with a block consumer and an option to include entities.
     *
     * @param location      the location to paste the schematic at
     * @param blockConsumer a consumer to customize the placement of each block in the schematic
     * @param withEntities  whether to include entities when pasting the schematic
     */
    void paste(Location location, Consumer<Block> blockConsumer, boolean withEntities);

    /**
     * Pastes the schematic at the specified location, with block and entity consumers to customize placement and spawning.
     *
     * @param location       the location to paste the schematic at
     * @param blockConsumer  a consumer to customize the placement of each block in the schematic
     * @param withEntities   whether to include entities when pasting the schematic
     * @param entityConsumer a consumer to customize the spawning of each entity in the schematic
     */
    void paste(Location location, Consumer<Block> blockConsumer, boolean withEntities, Consumer<Entity> entityConsumer);

    /**
     * Pastes the schematic at the specified location, with a custom spec.
     *
     * @param location           the location to paste the schematic at
     * @param schematicPasteSpec the custom spec
     * @see SchematicPasteSpec
     */
    void paste(Location location, SchematicPasteSpec schematicPasteSpec);

    /**
     * Returns the rotated schematic by the specified rotation.
     *
     * @param rotation the rotation to rotate the schematic by
     * @return a new schematic instance with the operation performed
     */
    Schematic rotate(StructureRotation rotation);

    /**
     * Returns the schematic, flipped along the specified axis.
     *
     * @param axis the axis to flip the schematic along
     * @return a new schematic instance with the operation performed
     */
    Schematic flip(Axis axis);

    /**
     * Returns the schematic, flipped along the X axis.
     *
     * @return a new schematic instance with the operation performed
     */
    Schematic flipX();

    /**
     * Returns the schematic, flipped along the Y axis.
     *
     * @return a new schematic instance with the operation performed
     */
    Schematic flipY();

    /**
     * Returns the schematic, flipped along the Z axis.
     *
     * @return a new schematic instance with the operation performed
     */
    Schematic flipZ();

    /**
     * Returns the width of the schematic.
     *
     * @return the width of the schematic in blocks
     */
    short width();

    /**
     * Returns the length of the schematic.
     *
     * @return the length of the schematic in blocks
     */
    short length();

    /**
     * Returns the height of the schematic.
     *
     * @return the height of the schematic in blocks
     */
    short height();
}
