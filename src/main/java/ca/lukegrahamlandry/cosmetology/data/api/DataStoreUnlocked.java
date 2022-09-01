package ca.lukegrahamlandry.cosmetology.data.api;

import net.minecraft.util.ResourceLocation;

import java.util.UUID;

public interface DataStoreUnlocked {
    void unlock(UUID player, ResourceLocation cosmeticKey);
}
