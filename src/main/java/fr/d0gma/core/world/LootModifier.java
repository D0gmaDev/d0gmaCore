package fr.d0gma.core.world;

import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

@FunctionalInterface
public interface LootModifier extends Consumer<ItemStack> {


}
