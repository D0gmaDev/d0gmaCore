package fr.d0gma.core.utils;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.DyeColor;

import java.util.Map;

import static java.util.Map.entry;

public class ComponentUtils {

    private static final Map<NamedTextColor, DyeColor> NAMED_TEXT_COLOR_DYE_COLOR_MAP = Map.ofEntries(
            entry(NamedTextColor.RED, DyeColor.RED),
            entry(NamedTextColor.YELLOW, DyeColor.YELLOW),
            entry(NamedTextColor.GOLD, DyeColor.ORANGE),
            entry(NamedTextColor.GREEN, DyeColor.LIME),
            entry(NamedTextColor.DARK_GREEN, DyeColor.GREEN),
            entry(NamedTextColor.DARK_AQUA, DyeColor.CYAN),
            entry(NamedTextColor.DARK_BLUE, DyeColor.BLUE),
            entry(NamedTextColor.BLUE, DyeColor.LIGHT_BLUE),
            entry(NamedTextColor.AQUA, DyeColor.LIGHT_BLUE),
            entry(NamedTextColor.LIGHT_PURPLE, DyeColor.PINK),
            entry(NamedTextColor.DARK_PURPLE, DyeColor.PURPLE),
            entry(NamedTextColor.DARK_RED, DyeColor.RED),
            entry(NamedTextColor.DARK_GRAY, DyeColor.GRAY),
            entry(NamedTextColor.GRAY, DyeColor.LIGHT_GRAY),
            entry(NamedTextColor.WHITE, DyeColor.WHITE),
            entry(NamedTextColor.BLACK, DyeColor.BLACK)
    );

    public static DyeColor getDyeColor(NamedTextColor namedTextColor) {
        return NAMED_TEXT_COLOR_DYE_COLOR_MAP.get(namedTextColor);
    }

}
