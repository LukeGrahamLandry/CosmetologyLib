package ca.lukegrahamlandry.cosmetology.data;

import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerCosmeticsCollection {
    // CosmeticSlots -> CosmeticInfo
    public Map<ResourceLocation, ResourceLocation> equipped = new HashMap<>();
    public List<ResourceLocation> unlocked = new ArrayList<>();

    public void equip(ResourceLocation slot, ResourceLocation cosmetic){
        if (cosmetic == null) equipped.remove(slot);
        else equipped.put(slot, cosmetic);
    }

    public void lock(ResourceLocation cosmetic){
        unlocked.add(cosmetic);
    }

    public void unlock(ResourceLocation cosmetic){
        unlocked.remove(cosmetic);
    }

    public ResourceLocation getFromSlot(ResourceLocation slotID) {
        return equipped.get(slotID);
    }
}
