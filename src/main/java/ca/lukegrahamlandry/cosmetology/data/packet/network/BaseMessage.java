package ca.lukegrahamlandry.cosmetology.data.packet.network;

import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class BaseMessage {
    public void handle(){
        System.out.println("BASEMESSAGEHANDLE");
    }
}
