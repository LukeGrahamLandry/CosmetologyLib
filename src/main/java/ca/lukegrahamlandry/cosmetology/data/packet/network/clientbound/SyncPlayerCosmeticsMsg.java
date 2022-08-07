package ca.lukegrahamlandry.cosmetology.data.packet.network.clientbound;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;
import ca.lukegrahamlandry.cosmetology.data.CosmeticInfo;
import ca.lukegrahamlandry.cosmetology.data.DataStore;
import ca.lukegrahamlandry.cosmetology.data.PlayerCosmeticsCollection;
import ca.lukegrahamlandry.cosmetology.data.packet.network.BaseMessage;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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

            for (Map.Entry<String , ResourceLocation> slotData : data.equipped.entrySet()){
                store.set(player, new ResourceLocation(slotData.getKey()), slotData.getValue());
            }

            for (ResourceLocation cosmeticKey : data.unlocked){
                store.unlock(player, cosmeticKey);
            }
        }
    }
}
