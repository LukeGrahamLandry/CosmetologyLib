package ca.lukegrahamlandry.cosmetology.forge;

import ca.lukegrahamlandry.cosmetology.data.packet.network.BaseMessage;
import com.google.gson.JsonElement;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static ca.lukegrahamlandry.cosmetology.util.EncodeUtil.GSON;

public class PacketHolder<T extends BaseMessage> {
    private final Class<T> clazz;

    public PacketHolder(Class<T> clazz){
        this.clazz = clazz;
    }

    public void encode(T message, PacketBuffer buffer){
        JsonElement data = GSON.toJsonTree(message);
        System.out.println("encode " + data.toString());
        buffer.writeUtf(data.toString());
    }

    public T decode(PacketBuffer buffer){
        String data = buffer.readUtf();
        System.out.println("decode " + data);
        return GSON.fromJson(data, this.clazz);
    }

    public void handle(T message, Supplier<NetworkEvent.Context> context){
        context.get().enqueueWork(() -> {
            System.out.println(message.getClass());
            if (context.get().getSender() == null) message.handle();
            else message.handle(context.get().getSender());
        });
        context.get().setPacketHandled(true);
    }
}
