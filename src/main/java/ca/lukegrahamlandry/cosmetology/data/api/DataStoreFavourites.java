package ca.lukegrahamlandry.cosmetology.data.api;

import net.minecraft.util.ResourceLocation;

import java.util.UUID;

public interface DataStoreFavourites {
    void favourite(UUID player, ResourceLocation cosmeticKey);
    void unfavourite(UUID player, ResourceLocation cosmeticKey);

    boolean isFavourite(UUID player, ResourceLocation cosmetic);

    void unfavouriteAll(UUID player);
}
