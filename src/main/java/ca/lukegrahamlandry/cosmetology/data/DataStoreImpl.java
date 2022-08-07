package ca.lukegrahamlandry.cosmetology.data;

import net.minecraft.util.ResourceLocation;

import java.util.*;
import java.util.stream.Collectors;

public class DataStoreImpl implements DataStore {
    private final String id;
    protected Map<ResourceLocation, CosmeticInfo> cosmetics = new HashMap<>();
    protected Map<UUID, PlayerCosmeticsCollection> playerCosmetics = new HashMap<>();

    public DataStoreImpl(String id){
        this.id = id;
    }

    @Override
    public String getStoreID() {
        return this.id;
    }

    @Override
    public Collection<CosmeticInfo> getAll() {
        return this.cosmetics.values();
    }

    @Override
    public void register(CosmeticInfo cosmetic) {
        this.cosmetics.put(cosmetic.id, cosmetic);
    }

    @Override
    public CosmeticInfo getInfo(ResourceLocation cosmetic) {
        return this.cosmetics.get(cosmetic);
    }

    // TODO: impl must check that cosmetic is unlocked
    @Override
    public void set(UUID playerID, ResourceLocation slotKey, ResourceLocation cosmeticKey) {
        getOrCreateData(playerID).equip(slotKey, cosmeticKey);
    }

    @Override
    public void clearCosmetic(UUID playerID, ResourceLocation cosmeticKey) {
        // TODO
    }

    @Override
    public void clearSlot(UUID playerID, ResourceLocation slotKey) {
        getOrCreateData(playerID).equip(slotKey, null);
    }

    @Override
    public List<CosmeticInfo> getActive(UUID playerID) {
        return getOrCreateData(playerID).equipped.values().stream().map(this::getInfo).collect(Collectors.toList());
    }

    @Override
    public CosmeticInfo getInSlot(UUID playerID, ResourceLocation slotID) {
        return getInfo(getOrCreateData(playerID).getFromSlot(slotID));
    }

    @Override
    public void unlock(UUID player, ResourceLocation cosmeticKey) {
        getOrCreateData(player).unlock(cosmeticKey);
    }

    public PlayerCosmeticsCollection getOrCreateData(UUID playerID){
        if (!this.playerCosmetics.containsKey(playerID)){
            this.playerCosmetics.put(playerID, new PlayerCosmeticsCollection());
        }
        return this.playerCosmetics.get(playerID);
    }
}
