package fr.d0gma.core.world.schematic;

import org.bukkit.plugin.Plugin;

import java.io.InputStream;
import java.util.Optional;

public class SchematicService {

    /**
     * Loads a schematic from the given plugin's resources folder.
     *
     * @param name   the name of the schematic file
     * @param plugin the plugin to load the schematic from
     * @return the loaded schematic, if it successfully loaded
     */
    public static Optional<Schematic> loadSchematic(String name, Plugin plugin) {
        return loadSchematic(getSchematicStreamFromResources(name, plugin));
    }

    /**
     * Loads a schematic from an input stream.
     *
     * @param inputStream the input stream to load the schematic from
     * @return the loaded schematic, if it successfully loaded
     */
    public static Optional<Schematic> loadSchematic(InputStream inputStream) {
        try {
            SchematicLoader schematicLoader = new SchematicLoader();
            return Optional.of(schematicLoader.load(inputStream));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Gets an input stream of a schematic file from the given plugin's resources folder.
     *
     * @param name   the name of the schematic file
     * @param plugin the plugin to get the schematic file from
     * @return the input stream of the schematic file
     */
    public static InputStream getSchematicStreamFromResources(String name, Plugin plugin) {
        return plugin.getClass().getClassLoader().getResourceAsStream(name);
    }
}
