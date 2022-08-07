package ca.lukegrahamlandry.cosmetology.data.packet.network.clientbound;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;
import ca.lukegrahamlandry.cosmetology.data.CosmeticInfo;
import ca.lukegrahamlandry.cosmetology.data.DataStore;
import ca.lukegrahamlandry.cosmetology.data.packet.network.BaseMessage;

import java.util.Collection;
import java.util.List;

public class RegisterMsg extends BaseMessage {
    private String sourceID;
    private Collection<CosmeticInfo> cosmetics;

    public RegisterMsg(String sourceID, Collection<CosmeticInfo> cosmetics) {
        this.sourceID = sourceID;
        this.cosmetics = cosmetics;
    }

    @Override
    public void handle() {
        CosmetologyApi.LOGGER.debug("PACKETMSG register");
        DataStore store = CosmetologyApi.getSource(this.sourceID);
        this.cosmetics.forEach(store::register);
    }
}
