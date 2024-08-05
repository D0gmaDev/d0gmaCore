package fr.d0gma.core.translation;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TranslationService {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final NavigableMap<String, String> TRANSLATIONS = new TreeMap<>();

    public static Component translate(String key) {
        String input = TRANSLATIONS.get(key);
        return input != null ? MINI_MESSAGE.deserialize(input) : notFound(key);
    }

    public static Component translate(String key, TagResolver resolver) {
        String input = TRANSLATIONS.get(key);
        return input != null ? MINI_MESSAGE.deserialize(input, resolver) : notFound(key);
    }

    public static Component translate(String key, Iterable<? extends TagResolver> resolvers) {
        String input = TRANSLATIONS.get(key);
        return input != null ? MINI_MESSAGE.deserialize(input, TagResolver.builder().resolvers(resolvers).build()) : notFound(key);
    }

    public static List<String> getAllKeysStartingWith(String prefix) {
        return TRANSLATIONS.subMap(prefix, prefix + Character.MAX_VALUE).keySet().stream().toList();
    }

    public static void loadTranslations(InputStream inputStream) {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream));
        var values = yaml.getValues(true).entrySet().stream()
                .filter(entry -> entry.getValue() instanceof String)
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString()));
        TRANSLATIONS.putAll(values);
        Bukkit.getLogger().info("Loaded " + values.size() + " translations.");
    }

    private static Component notFound(String key) {
        return Component.text("[Missing translation (").append(Component.text(key)).append(Component.text(")]")).decorate(TextDecoration.ITALIC);
    }

}
