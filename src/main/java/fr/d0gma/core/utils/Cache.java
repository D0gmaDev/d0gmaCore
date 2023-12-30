package fr.d0gma.core.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class Cache<T, U> {

    private final Function<T, UUID> uuidProvider;

    private final Map<UUID, Entry<U>> cacheMap = new HashMap<>();

    private final Duration cacheDuration;

    public Cache(Function<T, UUID> uuidProvider, Duration cacheDuration) {
        this.uuidProvider = uuidProvider;
        this.cacheDuration = cacheDuration;
    }

    public U put(T key, U value) {
        Instant expirationTime = Instant.now().plus(cacheDuration);
        Entry<U> entry = new Entry<>(value, expirationTime);
        cacheMap.put(this.uuidProvider.apply(key), entry);
        return value;
    }

    public Optional<U> get(T key) {
        UUID uuid = this.uuidProvider.apply(key);
        Entry<U> entry = this.cacheMap.get(uuid);

        if (entry != null && !entry.isExpired()) {
            return Optional.of(entry.value());
        } else {
            cacheMap.remove(uuid);
            return Optional.empty();
        }
    }

    public void remove(T key) {
        this.cacheMap.remove(this.uuidProvider.apply(key));
    }

    public void clear() {
        this.cacheMap.clear();
    }

    public void removeExpiredEntries() {
        this.cacheMap.values().removeIf(Entry::isExpired);
    }

    record Entry<V>(V value, Instant expirationTime) {
        public boolean isExpired() {
            return Instant.now().isAfter(expirationTime);
        }
    }
}
