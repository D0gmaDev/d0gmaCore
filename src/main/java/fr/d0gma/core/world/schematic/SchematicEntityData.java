package fr.d0gma.core.world.schematic;

import fr.d0gma.core.world.Axis;
import org.bukkit.Location;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.entity.Entity;

public interface SchematicEntityData {

    Entity summon(Location location);

    SchematicEntityData flip(Axis axis);

    SchematicEntityData rotate(StructureRotation rotation);

}
