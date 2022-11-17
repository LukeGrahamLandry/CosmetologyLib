package ca.lukegrahamlandry.cosmetology.data.impl;

import ca.lukegrahamlandry.cosmetology.data.DataStoreImpl;
import ca.lukegrahamlandry.cosmetology.data.api.CosmeticInfo;
import ca.lukegrahamlandry.cosmetology.data.api.DataStoreAvailable;
import ca.lukegrahamlandry.cosmetology.data.api.DataStoreUnlocked;
import net.minecraft.util.ResourceLocation;

import java.util.*;

/**
 * A DataStore that allows mods to provide cosmetics to their supporters with a unified interface.
 * Sources for available and unlocked cosmetics are split so multiple mods can provide their own group.
 * TODO: some way of isolating so people can only effect their own modid. for now just trust that people will be friendly.
 * Data about equipped and favourite cosmetics will be stored with world data.
 * This class will only be on the server and will sync to the client as a normal packet data store.
 */
public class ModSupportCosmetics extends DataStoreImpl {
    public static ModSupportCosmetics INSTANCE = new ModSupportCosmetics("mod_supporters");
    private Map<String, DataStoreAvailable> availableCosmetics = new HashMap<>();
    private Map<String, DataStoreUnlocked> unlockedCosmetics = new HashMap<>();

    public ModSupportCosmetics(String id) {
        super(id);
    }

    public static void registerAvailable(String modid, DataStoreAvailable available){
        INSTANCE.availableCosmetics.put(modid, available);
    }

    public static void registerUnlocked(String modid, DataStoreUnlocked unlocked){
        INSTANCE.unlockedCosmetics.put(modid, unlocked);
    }

    @Override
    public Collection<CosmeticInfo> getAll() {
        Collection<CosmeticInfo> all = new ArrayList<>();
        this.availableCosmetics.forEach((modid, available) -> all.addAll(available.getAll()));
        return all;
    }

    @Override
    public CosmeticInfo getInfo(ResourceLocation cosmetic) {
        for (Map.Entry<String, DataStoreAvailable> entry : this.availableCosmetics.entrySet()){
            CosmeticInfo info = entry.getValue().getInfo(cosmetic);
            if (info != null) return info;
        }
        return null;
    }

    @Override
    public Set<ResourceLocation> getSlots() {
        Set<ResourceLocation> all = new HashSet<>();
        this.availableCosmetics.forEach((modid, available) -> all.addAll(available.getSlots()));
        return all;
    }

    @Override
    public boolean hasUnlocked(UUID player, ResourceLocation cosmetic) {
        for (Map.Entry<String, DataStoreUnlocked> entry : this.unlockedCosmetics.entrySet()){
            if (entry.getValue().hasUnlocked(player, cosmetic)) return true;
        }
        return false;
    }
}
