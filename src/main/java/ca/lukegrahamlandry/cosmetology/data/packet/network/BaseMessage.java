package ca.lukegrahamlandry.cosmetology.data.packet.network;

import net.minecraft.entity.player.ServerPlayerEntity;

public abstract class BaseMessage {
    /**
     * Called when a client-bound packet is received/
     * This can run client-sided code so the current player can be retrieved from the Minecraft instance.
     * Also called by server-bound packets that do not override the other handle method because they don't need to know who sent the packet.
     */
    public void handle(){

    }

    /**
     * Called when a server-bound packet is received.
     * Tells you which client it came from.
     */
    public void handle(ServerPlayerEntity sender){
        handle();
    }
}
