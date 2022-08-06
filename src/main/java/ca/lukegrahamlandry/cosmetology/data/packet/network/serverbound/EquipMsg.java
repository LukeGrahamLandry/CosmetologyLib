package ca.lukegrahamlandry.cosmetology.data.packet.network.serverbound;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;
import ca.lukegrahamlandry.cosmetology.data.CosmeticInfo;
import ca.lukegrahamlandry.cosmetology.data.DataStore;
import ca.lukegrahamlandry.cosmetology.data.packet.network.BaseMessage;
import net.minecraft.util.ResourceLocation;

import java.util.List;
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
    public void handle() {
        DataStore store = CosmetologyApi.getSource(this.sourceID);
        store.set(this.player, this.slot, this.cosmetic);
    }
}
