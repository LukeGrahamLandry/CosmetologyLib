package ca.lukegrahamlandry.cosmetology.data.packet.network.clientbound;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;
import ca.lukegrahamlandry.cosmetology.data.DataStoreImpl;
import ca.lukegrahamlandry.cosmetology.data.api.DataStore;
import ca.lukegrahamlandry.cosmetology.data.PlayerCosmeticsCollection;
import ca.lukegrahamlandry.cosmetology.data.packet.network.BaseMessage;
import ca.lukegrahamlandry.cosmetology.util.EncodeUtil;
import net.minecraft.util.ResourceLocation;

import java.util.Map;
import java.util.UUID;

/**
 * A client-bound packet that updates the cosmetics settings of a group of players.
 * Sent when someone logs in or updates their settings (equipping a new hat for example).
 */
public class SyncPlayerCosmeticsMsg extends BaseMessage {
    private String sourceID;
    private Map<UUID, PlayerCosmeticsCollection> cosmetics;

    public SyncPlayerCosmeticsMsg(String sourceID, Map<UUID, PlayerCosmeticsCollection> cosmetics) {
        this.sourceID = sourceID;
        this.cosmetics = cosmetics;
    }

    @Override
    public void handle() {
        DataStore store = CosmetologyApi.getSource(this.sourceID);
        for (UUID player : this.cosmetics.keySet()){
            PlayerCosmeticsCollection data = this.cosmetics.get(player);

            store.lockAll(player);
            for (ResourceLocation cosmeticKey : data.unlocked){
                store.unlock(player, cosmeticKey);
            }

            store.unfavouriteAll(player);
            for (ResourceLocation cosmeticKey : data.favourites){
                store.favourite(player, cosmeticKey);
            }

            store.unequipAll(player);
            for (Map.Entry<ResourceLocation , ResourceLocation> slotData : data.equipped.entrySet()){
                store.set(player, slotData.getKey(), slotData.getValue());
            }
        }
    }
}
