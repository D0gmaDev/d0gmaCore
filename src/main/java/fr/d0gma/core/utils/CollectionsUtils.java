package fr.d0gma.core.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class CollectionsUtils {

    private static final Random RANDOM = new Random();

    public static <T> Optional<T> getRandomElement(List<T> list) {
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(RANDOM.nextInt(list.size())));
    }

    public static <T> Optional<T> getRandomElement(T[] array) {
        return array.length == 0 ? Optional.empty() : Optional.of(array[RANDOM.nextInt(array.length)]);
    }

    public static <T> Optional<T> getRandomElementExcept(List<T> list, T except) {
        return getRandomElementExcept(list, List.of(except));
    }

    public static <T> Optional<T> getRandomElementExcept(T[] array, T except) {
        return getRandomElementExcept(array, List.of(except));
    }

    public static <T> Optional<T> getRandomElementExcept(List<T> list, Collection<T> except) {
        return getRandomElement(list.stream().filter(t -> !except.contains(t)).toList());
    }

    public static <T> Optional<T> getRandomElementExcept(T[] array, Collection<T> except) {
        return getRandomElement(Arrays.stream(array).filter(t -> !except.contains(t)).toList());
    }
}
