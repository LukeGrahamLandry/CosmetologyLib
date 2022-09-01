package ca.lukegrahamlandry.cosmetology.data;

import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerCosmeticsCollection {
    // CosmeticSlots -> CosmeticInfo
    // a resource location cannot be the key because then gson can't remake it because it represents rls as {path: "", namespace: ""} and json cannot have objects as keys
    public Map<String, ResourceLocation> equipped = new HashMap<>();
    public List<ResourceLocation> unlocked = new ArrayList<>();

    public void equip(ResourceLocation slot, ResourceLocation cosmetic){
        if (cosmetic == null) equipped.remove(slot.toString());
        else equipped.put(slot.toString(), cosmetic);
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
