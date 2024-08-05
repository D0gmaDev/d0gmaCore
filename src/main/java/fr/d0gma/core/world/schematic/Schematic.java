package fr.d0gma.core.world.schematic;

import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.entity.Entity;

import java.util.Set;
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

    void paste(Location location);

    void paste(Location location, Consumer<Block> blockConsumer);

    void pasteWithoutEntities(Location location, Consumer<Block> blockConsumer);

    void paste(Location location, Consumer<Block> blockConsumer, Consumer<Entity> entityConsumer);

    void paste(Location location, Set<PasteArgument> pasteArguments);

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
