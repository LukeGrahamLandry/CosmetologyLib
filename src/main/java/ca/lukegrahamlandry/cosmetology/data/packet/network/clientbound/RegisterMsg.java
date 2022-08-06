package ca.lukegrahamlandry.cosmetology.data.packet.network.clientbound;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;
import ca.lukegrahamlandry.cosmetology.data.CosmeticInfo;
import ca.lukegrahamlandry.cosmetology.data.DataStore;
import ca.lukegrahamlandry.cosmetology.data.packet.network.BaseMessage;

import java.util.List;

public class RegisterMsg extends BaseMessage {
    private String sourceID;
    private List<CosmeticInfo> cosmetics;

    public RegisterMsg(String sourceID, List<CosmeticInfo> cosmetics) {
        this.sourceID = sourceID;
        this.cosmetics = cosmetics;
    }

    @Override
    public void handle() {
        DataStore store = CosmetologyApi.getSource(this.sourceID);
        this.cosmetics.forEach(store::register);
    }
}
