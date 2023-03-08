package ca.lukegrahamlandry.cosmetology.forge;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;
import ca.lukegrahamlandry.cosmetology.data.packet.network.BaseMessage;
import com.google.gson.JsonElement;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static ca.lukegrahamlandry.cosmetology.util.EncodeUtil.GSON;

/**
 * A generic packet message.
 * Initialized with a data class, instances of which will automatically be json encoded to be sent over the network.
 * - The data class must implement BaseMessage#handle to react when the packet is received
 * - EncodeUtil#GSON must have all necessary type adapters to encode all fields of the data class
 */
public class PacketHolder<T extends BaseMessage> {
    private final Class<T> clazz;

    public PacketHolder(Class<T> clazz){
        this.clazz = clazz;
    }

    public void encode(T message, PacketBuffer buffer){
        JsonElement data = GSON.toJsonTree(message);
        CosmetologyApi.debugLog("encode " + data.toString());
        buffer.writeUtf(data.toString());
    }

    public T decode(PacketBuffer buffer){
        String data = buffer.readUtf();
        CosmetologyApi.debugLog("decode " + data);
        return GSON.fromJson(data, this.clazz);
    }

    public void handle(T message, Supplier<NetworkEvent.Context> context){
        context.get().enqueueWork(() -> {
            CosmetologyApi.debugLog("Handle packet: " + message.getClass());
            if (context.get().getSender() == null) message.handle();
            else message.handle(context.get().getSender());
        });
        context.get().setPacketHandled(true);
    }
}
