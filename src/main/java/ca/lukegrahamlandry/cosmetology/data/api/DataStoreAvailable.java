package ca.lukegrahamlandry.cosmetology.data.api;

import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.Set;

public interface DataStoreAvailable {
    Collection<CosmeticInfo> getAll();
    void register(CosmeticInfo cosmetic);
    CosmeticInfo getInfo(ResourceLocation cosmetic);

    Set<ResourceLocation> getSlots();
}
