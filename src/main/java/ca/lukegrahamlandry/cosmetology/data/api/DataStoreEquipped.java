package ca.lukegrahamlandry.cosmetology.data.api;

import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.UUID;

public interface DataStoreEquipped {
    void set(UUID playerID, ResourceLocation slotKey, ResourceLocation cosmeticKey);
    void clearCosmetic(UUID playerID, ResourceLocation cosmeticKey);
    void clearSlot(UUID playerID, ResourceLocation slotKey);
    List<CosmeticInfo> getActive(UUID playerID);
    CosmeticInfo getInSlot(UUID playerID, ResourceLocation slotID);
}
