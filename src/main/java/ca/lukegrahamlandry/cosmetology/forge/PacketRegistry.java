package ca.lukegrahamlandry.cosmetology.forge;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;
import ca.lukegrahamlandry.cosmetology.data.packet.network.BaseMessage;
import ca.lukegrahamlandry.cosmetology.data.packet.network.clientbound.RegisterMsg;
import ca.lukegrahamlandry.cosmetology.data.packet.network.clientbound.SyncPlayerCosmeticsMsg;
import ca.lukegrahamlandry.cosmetology.data.packet.network.serverbound.EquipMsg;
import ca.lukegrahamlandry.cosmetology.data.packet.network.serverbound.FavouriteMsg;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketRegistry {
    private SimpleChannel channel;
    int i = 0;

    public SimpleChannel getChannel(){
        return this.channel;
    }

    /**
     * Must be called on the FMLCommonSetupEvent (in an enqueueWork lambda)
     * Registers required packets.
     * @param id: a unique string, like your mod id, to allow multiple shadowing mods to use distinct channels
     */
    public void registerPackets(String id){
        this.channel = NetworkRegistry.newSimpleChannel(new ResourceLocation(CosmetologyApi.MOD_ID, id), () -> "1.0", s -> true, s -> true);

        registerPacket(RegisterMsg.class);
        registerPacket(SyncPlayerCosmeticsMsg.class);

        registerPacket(EquipMsg.class);
        registerPacket(FavouriteMsg.class);
    }

    /**
     * Registers a data class that may be sent as a packet.
     * The data will be automatically json encoded by PacketHolder and the handle method will be called when received.
     * Ensure that the order you register packets is deterministic on the client and server so the id values remain in sync.
     */
    public <T extends BaseMessage> void registerPacket(Class<T> clazz){
        PacketHolder<T> packet = new PacketHolder<>(clazz);
        this.channel.registerMessage(i++, clazz, packet::encode, packet::decode, packet::handle);
    }
}
