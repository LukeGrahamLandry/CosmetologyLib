package ca.lukegrahamlandry.cosmetology.data.packet.network.serverbound;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;
import ca.lukegrahamlandry.cosmetology.data.packet.ServerPacketDataStore;
import ca.lukegrahamlandry.cosmetology.data.packet.network.BaseMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

import java.util.UUID;

public class FavouriteMsg extends BaseMessage {
    private final boolean isFavourite;
    private String sourceID;
    private final ResourceLocation slot;
    private ResourceLocation cosmetic;

    public FavouriteMsg(String sourceID, ResourceLocation slot, ResourceLocation cosmetic, boolean fav) {
        this.sourceID = sourceID;
        this.slot = slot;
        this.cosmetic = cosmetic;
        this.isFavourite = fav;
    }

    @Override
    public void handle(ServerPlayerEntity sender) {
        if (CosmetologyApi.serverPacketDataSources.containsKey(this.sourceID)){
            ServerPacketDataStore store = CosmetologyApi.serverPacketDataSources.get(this.sourceID);
            if (this.isFavourite){
                store.model.favourite(sender.getUUID(), this.cosmetic);
            } else {
                store.model.unfavourite(sender.getUUID(), this.cosmetic);
            }
            store.syncPlayerToAll(sender);
        }
    }
}
