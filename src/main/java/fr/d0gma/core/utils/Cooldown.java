package fr.d0gma.core.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public class Cooldown<T> {

    private final Function<T, UUID> uuidProvider;

    private final Map<UUID, Instant> cooldownMap = new HashMap<>();

    public Cooldown(Function<T, UUID> uuidProvider) {
        this.uuidProvider = uuidProvider;
    }

    public boolean isInCooldown(T key) {
        UUID uuid = this.uuidProvider.apply(key);
        Instant instant = this.cooldownMap.get(uuid);

        if (instant == null) {
            return false;
        }

        if (instant.isBefore(Instant.now())) {
            this.cooldownMap.remove(uuid);
            return false;
        }

        return true;
    }

    public void setCooldown(T key, Duration duration) {
        this.cooldownMap.put(this.uuidProvider.apply(key), Instant.now().plus(duration));
    }

    public Duration getCooldownLeft(T key) {
        Instant now = Instant.now();

        if (!isInCooldown(key)) {
            return Duration.ZERO;
        }

        return Duration.between(now, this.cooldownMap.get(this.uuidProvider.apply(key)));
    }

    public void removeCooldown(T key) {
        this.cooldownMap.remove(this.uuidProvider.apply(key));
    }

    public void clear() {
        this.cooldownMap.clear();
    }

    public void clearExpiredCooldowns() {
        Instant now = Instant.now();
        this.cooldownMap.values().removeIf(now::isAfter);
    }

    public void ifInCooldownOrElse(T key, Consumer<Duration> inCooldown, Runnable notInCooldown) {
        Duration cooldownLeft = getCooldownLeft(key);

        if (cooldownLeft.isZero()) {
            notInCooldown.run();
        } else {
            inCooldown.accept(cooldownLeft);
        }
    }

}
