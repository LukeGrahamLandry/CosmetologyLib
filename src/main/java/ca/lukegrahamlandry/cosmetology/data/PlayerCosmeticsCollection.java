package ca.lukegrahamlandry.cosmetology.data;

import net.minecraft.util.ResourceLocation;

import java.util.*;
import java.util.stream.Stream;

/**
 * A very simple data structure for storing a single player's cosmetic information.
 * Stores which cosmetics are equipped in each slot, which they have permission to use and which are marked as favourites.
 * A DataStore implementation is responsible for storing these somewhere and mapping them to players.
 */
public class PlayerCosmeticsCollection {
    public Map<ResourceLocation, ResourceLocation> equipped = new HashMap<>();
    public List<ResourceLocation> unlocked = new ArrayList<>();
    public List<ResourceLocation> favourites = new ArrayList<>();

    public void equip(ResourceLocation slot, ResourceLocation cosmetic){
        if (cosmetic == null) equipped.remove(slot);
        else equipped.put(slot, cosmetic);
    }

    public boolean hasUnlocked(ResourceLocation cosmetic){
        return unlocked.contains(cosmetic);
    }

    public void lock(ResourceLocation cosmetic){
        unlocked.remove(cosmetic);
    }

    public void unlock(ResourceLocation cosmetic){
        unlocked.add(cosmetic);
    }

    public boolean isFavourite(ResourceLocation cosmetic){
        return favourites.contains(cosmetic);
    }

    public void unfavourite(ResourceLocation cosmetic){
        favourites.remove(cosmetic);
    }

    public void favourite(ResourceLocation cosmetic){
        favourites.add(cosmetic);
    }

    public ResourceLocation getFromSlot(ResourceLocation slotID) {
        return equipped.get(slotID);
    }

    public void lockAll() {
        unlocked.clear();
    }

    public void unfavouriteAll() {
        favourites.clear();
    }
}
