package fr.d0gma.core.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.UUID;
import java.util.function.Function;

public class MaterialCooldown<T> extends Cooldown<T> {

    private final Function<T, Player> playerProvider;
    private final Material cooldownMaterial;

    public MaterialCooldown(Function<T, UUID> uuidProvider, Function<T, Player> playerProvider, Material cooldownMaterial) {
        super(uuidProvider);
        this.playerProvider = playerProvider;
        this.cooldownMaterial = cooldownMaterial;
    }

    @Override
    public void setCooldown(T data, Duration duration) {
        super.setCooldown(data, duration);
        this.playerProvider.apply(data).setCooldown(this.cooldownMaterial, (int) (duration.getSeconds() * 20));
    }
}
