package fr.d0gma.core.world;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class VoidGenerator extends ChunkGenerator {

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0, 150, 0);
    }
}
