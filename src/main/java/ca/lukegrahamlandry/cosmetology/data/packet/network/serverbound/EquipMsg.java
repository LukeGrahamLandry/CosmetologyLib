package ca.lukegrahamlandry.cosmetology.data.packet.network.serverbound;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;
import ca.lukegrahamlandry.cosmetology.data.api.DataStore;
import ca.lukegrahamlandry.cosmetology.data.packet.ServerPacketDataStore;
import ca.lukegrahamlandry.cosmetology.data.packet.network.BaseMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

import java.util.UUID;

public class EquipMsg extends BaseMessage {
    private String sourceID;
    private UUID player;
    private final ResourceLocation slot;
    private ResourceLocation cosmetic;

    // TODO: handle must check that it actually came from that player or clients can set any player's cosmetic
    public EquipMsg(String sourceID, UUID player, ResourceLocation slot, ResourceLocation cosmetic) {
        this.sourceID = sourceID;
        this.player = player;
        this.slot = slot;
        this.cosmetic = cosmetic;
    }

    @Override
    public void handle(ServerPlayerEntity sender) {
        if (!sender.getUUID().equals(this.player)){
            System.out.println(sender.getName() + "" + sender.getUUID() + " is trying to set cosmetics for " + this.player);
            return;
        }
        if (CosmetologyApi.serverPacketDataSources.containsKey(this.sourceID)){
            ServerPacketDataStore store = CosmetologyApi.serverPacketDataSources.get(this.sourceID);
            store.model.set(this.player, this.slot, this.cosmetic);
            store.syncPlayerToAll(sender);
        }
    }
}
