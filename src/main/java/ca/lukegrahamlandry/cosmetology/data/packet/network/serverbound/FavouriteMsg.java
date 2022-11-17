package ca.lukegrahamlandry.cosmetology.data.packet.network.serverbound;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;
import ca.lukegrahamlandry.cosmetology.data.packet.ServerPacketDataStore;
import ca.lukegrahamlandry.cosmetology.data.packet.network.BaseMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

import java.util.UUID;

/**
 * A server-bound packet that notifies that a client would like to mark a specified cosmetic as a favourite.
 * Causes the server to update its model and re-sync the player to all clients.
 * TODO: could probably not re-sync since other players do not need to know your favourites.
 */
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
