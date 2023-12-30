package fr.d0gma.core.utils;

import java.time.Duration;
import java.util.UUID;
import java.util.function.Function;

public class AutoCache<T, U> extends Cache<T, U> {

    private final Function<T, U> dataFetcher;

    public AutoCache(Function<T, UUID> uuidProvider, Function<T, U> dataFetcher, Duration cacheDuration) {
        super(uuidProvider, cacheDuration);
        this.dataFetcher = dataFetcher;
    }

    public U getOrFetch(T key) {
        return get(key).orElseGet(() -> put(key));
    }

    public U put(T key) {
        return put(key, this.dataFetcher.apply(key));
    }
}
