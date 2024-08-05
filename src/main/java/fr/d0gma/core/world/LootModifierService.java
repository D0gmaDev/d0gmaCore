package fr.d0gma.core.world;

import com.google.common.base.Preconditions;
import fr.d0gma.core.timer.RunnableHelper;
import fr.d0gma.core.utils.GameUtils;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class LootModifierService {

    private static final Queue<PriorityObject<LootModifier>> lootModifierList = new PriorityQueue<>(Comparator.comparingInt(PriorityObject::priority));

    public static void register(ModifierPriority modifierPriority, Material blockType, LootModifier lootModifier) {
        register(modifierPriority, blockType, lootModifier, 0, false);
    }

    public static void register(ModifierPriority modifierPriority, Material blockType, LootModifier lootModifier, int expToDrop) {
        register(modifierPriority, blockType, lootModifier, expToDrop, false);
    }

    public static void register(ModifierPriority modifierPriority, Material blockType, LootModifier lootModifier, int expToDrop, boolean allowBlockPlaced) {
        Preconditions.checkNotNull(lootModifier);

        lootModifierList.add(new PriorityObject<>(blockType, lootModifier, expToDrop, modifierPriority.ordinal(), allowBlockPlaced));
    }

    public static void applyLootModifiers(BlockBreakEvent event) {
        boolean modified = false;

        List<ItemStack> drops = event.getBlock().getDrops(event.getPlayer().getActiveItem()).stream().toList();

        for (PriorityObject<LootModifier> priorityObject : lootModifierList) {
            if (!priorityObject.allowBlockPlaced() && !event.getBlock().getMetadata("dropped").isEmpty()) {
                continue;
            }

            if (priorityObject.blockType() == null || priorityObject.blockType() == event.getBlock().getType()) {
                modified = true;

                if (priorityObject.expToDrop() > 0) {
                    GameUtils.dropExperience(event.getBlock().getLocation().add(0.5, 0.5, 0.5), priorityObject.expToDrop());
                }

                LootModifier lootModifier = priorityObject.object();

                for (ItemStack itemStack : drops) {
                    lootModifier.accept(itemStack);
                }
            }
        }

        if (modified) {
            event.setDropItems(false);

            RunnableHelper.runLaterSynchronously(() -> {
                for (ItemStack itemStack : drops) {
                    if (itemStack != null && itemStack.getType() != Material.AIR) {
                        event.getBlock().getWorld().dropItem(event.getBlock().getLocation().clone().add(0.5, 0.3, 0.5), itemStack);
                    }
                }
            }, 3);
        }
    }

    private record PriorityObject<U>(
            Material blockType,
            U object,
            int expToDrop,
            int priority,
            boolean allowBlockPlaced
    ) {
    }
}
