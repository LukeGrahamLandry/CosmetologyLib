package ca.lukegrahamlandry.cosmetology.data.api;

import net.minecraft.util.ResourceLocation;

import java.util.UUID;

/**
 * Stores data about which cosmetics each player has favourited to be prioritised in GUI displays
 */
public interface DataStoreFavourites {
    void favourite(UUID player, ResourceLocation cosmeticKey);
    void unfavourite(UUID player, ResourceLocation cosmeticKey);

    boolean isFavourite(UUID player, ResourceLocation cosmetic);

    void unfavouriteAll(UUID player);
}
