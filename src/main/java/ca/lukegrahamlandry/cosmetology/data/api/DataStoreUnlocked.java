package ca.lukegrahamlandry.cosmetology.data.api;

import net.minecraft.util.ResourceLocation;

import java.util.UUID;

/**
 * Stores data about which cosmetics each player is allowed to equip.
 */
public interface DataStoreUnlocked {
    void unlock(UUID player, ResourceLocation cosmeticKey);
    void lock(UUID player, ResourceLocation cosmeticKey);

    boolean hasUnlocked(UUID player, ResourceLocation cosmetic);

    void lockAll(UUID player);
}
