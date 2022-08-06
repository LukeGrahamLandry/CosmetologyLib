package ca.lukegrahamlandry.cosmetology.data;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface DataStore {
    String getStoreID();

    Collection<CosmeticInfo> getAll();
    void register(CosmeticInfo cosmetic);
    CosmeticInfo getInfo(ResourceLocation cosmetic);

    void set(UUID playerID, ResourceLocation slotKey, ResourceLocation cosmeticKey);
    void clearCosmetic(UUID playerID, ResourceLocation cosmeticKey);
    void clearSlot(UUID playerID, ResourceLocation slotKey);
    List<CosmeticInfo> getActive(UUID playerID);
    CosmeticInfo getInSlot(UUID playerID, ResourceLocation slotID);

    default void set(UUID playerID, ResourceLocation cosmeticKey){
        CosmeticInfo cosmetic = getInfo(cosmeticKey);
        set(playerID, cosmetic.slot, cosmeticKey);
    }

    default void onPlayerLogin(PlayerEntity player){

    };

    void unlock(UUID player, ResourceLocation cosmeticKey);
}
