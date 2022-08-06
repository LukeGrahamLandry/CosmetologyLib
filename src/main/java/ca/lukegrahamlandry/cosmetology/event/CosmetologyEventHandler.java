package ca.lukegrahamlandry.cosmetology.event;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;
import ca.lukegrahamlandry.cosmetology.data.DataStore;
import ca.lukegrahamlandry.cosmetology.data.packet.ServerPacketDataStore;
import net.minecraft.entity.player.ServerPlayerEntity;

public class CosmetologyEventHandler {
    public static void addCosmeticLayers(){

    }

    public static void onPlayerLoginServer(ServerPlayerEntity player){
        for (DataStore data : CosmetologyApi.getSources()){
            if (data instanceof ServerPacketDataStore){
                ((ServerPacketDataStore) data).onPlayerLogin(player);
            }
        }
    }
}
