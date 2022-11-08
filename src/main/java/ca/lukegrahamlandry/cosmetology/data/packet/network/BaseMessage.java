package ca.lukegrahamlandry.cosmetology.data.packet.network;

import net.minecraft.entity.player.ServerPlayerEntity;

public abstract class BaseMessage {
    // for clientbound packets
    public void handle(){

    }

    // for serverbound packets
    public void handle(ServerPlayerEntity sender){
        handle();
    }
}
