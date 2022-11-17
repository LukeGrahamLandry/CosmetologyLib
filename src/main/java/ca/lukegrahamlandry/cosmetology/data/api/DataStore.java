package ca.lukegrahamlandry.cosmetology.data.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

import java.util.UUID;

/**
 * A combination of all interfaces required for a cosmetics management implementation.
 */
public interface DataStore extends DataStoreEquipped, DataStoreUnlocked, DataStoreAvailable, DataStoreFavourites {
    String getStoreID();

    default void onPlayerLogin(PlayerEntity player){

    };

    default void set(UUID playerID, ResourceLocation cosmeticKey){
        CosmeticInfo cosmetic = getInfo(cosmeticKey);
        set(playerID, cosmetic.slot, cosmeticKey);
    }
}
