package ca.lukegrahamlandry.cosmetology.data.packet;

import ca.lukegrahamlandry.cosmetology.data.DataStoreImpl;
import ca.lukegrahamlandry.cosmetology.data.packet.network.BaseMessage;

import java.util.function.Consumer;

public class ClientPacketDataStore extends DataStoreImpl {
    private Consumer<BaseMessage> sendPacketToServer;

    public ClientPacketDataStore(String id, Consumer<BaseMessage> sendPacketToServer) {
        super(id);
        this.sendPacketToServer = sendPacketToServer;
    }
}
