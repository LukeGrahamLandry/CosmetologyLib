package ca.lukegrahamlandry.cosmetology.data.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

import java.util.UUID;


// Available - what cosmetics exist
// Unlocked - what cosmetics am i allowed to use
// Equipped - what cosmetics am i wearing

public interface DataStore extends DataStoreEquipped, DataStoreUnlocked, DataStoreAvailable {
    String getStoreID();

    default void onPlayerLogin(PlayerEntity player){

    };

    default void set(UUID playerID, ResourceLocation cosmeticKey){
        CosmeticInfo cosmetic = getInfo(cosmeticKey);
        set(playerID, cosmetic.slot, cosmeticKey);
    }
}
