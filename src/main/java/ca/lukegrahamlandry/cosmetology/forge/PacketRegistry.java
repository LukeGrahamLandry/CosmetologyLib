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

    public void registerPackets(String id){
        this.channel = NetworkRegistry.newSimpleChannel(new ResourceLocation(CosmetologyApi.MOD_ID, id), () -> "1.0", s -> true, s -> true);

        registerPacket(RegisterMsg.class);
        registerPacket(SyncPlayerCosmeticsMsg.class);

        registerPacket(EquipMsg.class);
        registerPacket(FavouriteMsg.class);
    }

    private <T extends BaseMessage> void registerPacket(Class<T> clazz){
        PacketHolder<T> packet = new PacketHolder<>(clazz);
        this.channel.registerMessage(i++, clazz, packet::encode, packet::decode, packet::handle);
    }
}
