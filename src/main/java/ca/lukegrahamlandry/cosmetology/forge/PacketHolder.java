package ca.lukegrahamlandry.cosmetology.forge;

import ca.lukegrahamlandry.cosmetology.data.packet.network.BaseMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketHolder<T extends BaseMessage> {
    private static Gson gson = new GsonBuilder().create();
    private final Class<T> clazz;

    public PacketHolder(Class<T> clazz){
        this.clazz = clazz;
    }

    public void encode(T message, PacketBuffer buffer){
        JsonElement data = gson.toJsonTree(message);
        buffer.writeUtf(data.toString());
    }

    public T decode(PacketBuffer buffer){
        return gson.fromJson(buffer.readUtf(), this.clazz);
    }

    public void handle(T message, Supplier<NetworkEvent.Context> context){
        context.get().enqueueWork(message::handle);
        context.get().setPacketHandled(true);
    }
}
