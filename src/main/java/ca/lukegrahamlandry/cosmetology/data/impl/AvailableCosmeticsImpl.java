package ca.lukegrahamlandry.cosmetology.data.impl;

import ca.lukegrahamlandry.cosmetology.data.api.CosmeticInfo;
import ca.lukegrahamlandry.cosmetology.data.api.CosmeticSlots;
import ca.lukegrahamlandry.cosmetology.data.api.DataStoreAvailable;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AvailableCosmeticsImpl implements DataStoreAvailable {
    protected Map<ResourceLocation, CosmeticInfo> cosmetics = new HashMap<>();

    @Override
    public Collection<CosmeticInfo> getAll() {
        return cosmetics.values();
    }

    @Override
    public void register(CosmeticInfo cosmetic) {
        cosmetics.put(cosmetic.id, cosmetic);
    }

    @Override
    public CosmeticInfo getInfo(ResourceLocation cosmetic) {
        return cosmetics.get(cosmetic);
    }

    @Override
    public Set<ResourceLocation> getSlots() {
        return CosmeticSlots.getAll();
    }
}
