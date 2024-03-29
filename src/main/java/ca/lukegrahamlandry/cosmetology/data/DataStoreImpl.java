package ca.lukegrahamlandry.cosmetology.data;

import ca.lukegrahamlandry.cosmetology.data.api.CosmeticInfo;
import ca.lukegrahamlandry.cosmetology.data.api.CosmeticSlots;
import ca.lukegrahamlandry.cosmetology.data.api.DataStore;
import com.google.gson.*;
import net.minecraft.util.ResourceLocation;

import java.util.*;
import java.util.stream.Collectors;

import static ca.lukegrahamlandry.cosmetology.util.EncodeUtil.GSON;

/**
 * An example implementation of a DataStore.
 * Tracks a group of cosmetics and maps each player to a PlayerCosmeticsCollection to store their individual settings.
 * Provides read/write methods to serialize the player information as json.
 * The available group of cosmetics are not serialized and must be registered again whenever the game restarts.
 */
public class DataStoreImpl implements DataStore {
    private final String id;
    protected Map<ResourceLocation, CosmeticInfo> cosmetics = new HashMap<>();
    protected Map<UUID, PlayerCosmeticsCollection> playerCosmetics = new HashMap<>();
    public Runnable sync = () -> {};

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
        if (cosmetic.isRegisterAllowed()) this.cosmetics.put(cosmetic.id, cosmetic);
    }

    @Override
    public CosmeticInfo getInfo(ResourceLocation cosmetic) {
        return this.cosmetics.get(cosmetic);
    }

    @Override
    public Set<ResourceLocation> getSlots() {
        return CosmeticSlots.getAll();
    }

    @Override
    public void set(UUID playerID, ResourceLocation slotKey, ResourceLocation cosmeticKey) {
        if (getOrCreateData(playerID).hasUnlocked(cosmeticKey) || cosmeticKey == null) getOrCreateData(playerID).equip(slotKey, cosmeticKey);
    }

    @Override
    public void clearCosmetic(UUID playerID, ResourceLocation cosmeticKey) {
        List<ResourceLocation> slotsToClear = new ArrayList<>();
        for (Map.Entry<ResourceLocation, ResourceLocation> data : getOrCreateData(playerID).equipped.entrySet()){
            if (data.getValue().equals(cosmeticKey)) {
                slotsToClear.add(data.getKey());
            }
        }

        for (ResourceLocation slot : slotsToClear){
            clearSlot(playerID, slot);
        }
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
    public void unequipAll(UUID player) {
        for (ResourceLocation slot : CosmeticSlots.getAll()){
            clearSlot(player, slot);
        }
    }

    @Override
    public void unlock(UUID player, ResourceLocation cosmeticKey) {
        getOrCreateData(player).unlock(cosmeticKey);
    }

    @Override
    public void lock(UUID player, ResourceLocation cosmeticKey) {
        getOrCreateData(player).lock(cosmeticKey);
    }

    @Override
    public boolean hasUnlocked(UUID player, ResourceLocation cosmetic){
        return getOrCreateData(player).hasUnlocked(cosmetic);
    }

    @Override
    public void lockAll(UUID player) {
        getOrCreateData(player).lockAll();
    }


    @Override
    public void favourite(UUID player, ResourceLocation cosmeticKey) {
        getOrCreateData(player).favourite(cosmeticKey);
    }

    @Override
    public void unfavourite(UUID player, ResourceLocation cosmeticKey) {
        getOrCreateData(player).unfavourite(cosmeticKey);
    }

    @Override
    public boolean isFavourite(UUID player, ResourceLocation cosmetic){
        return getOrCreateData(player).isFavourite(cosmetic);
    }

    @Override
    public void unfavouriteAll(UUID player) {
        getOrCreateData(player).unfavouriteAll();
    }


    public PlayerCosmeticsCollection getOrCreateData(UUID playerID){
        if (!this.playerCosmetics.containsKey(playerID)){
            this.playerCosmetics.put(playerID, new PlayerCosmeticsCollection());
        }
        return this.playerCosmetics.get(playerID);
    }

    public String write(){
        JsonObject data = GSON.toJsonTree(this.playerCosmetics).getAsJsonObject();
        return data.toString();
    }

    public void read(String data){
        JsonObject info = GSON.fromJson(data, JsonObject.class);
        this.playerCosmetics.clear();
        for (Map.Entry<String, JsonElement> entry : info.entrySet()){
            this.playerCosmetics.put(UUID.fromString(entry.getKey()), GSON.fromJson(entry.getValue().toString(), PlayerCosmeticsCollection.class));
        }
    }
}
