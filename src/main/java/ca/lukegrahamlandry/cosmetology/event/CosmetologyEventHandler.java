package ca.lukegrahamlandry.cosmetology.event;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;
import ca.lukegrahamlandry.cosmetology.data.api.DataStore;
import net.minecraft.entity.player.PlayerEntity;

public class CosmetologyEventHandler {
    public static void onPlayerLogin(PlayerEntity player){
        for (DataStore data : CosmetologyApi.getSources()){
            data.onPlayerLogin(player);
        }
    }
}
