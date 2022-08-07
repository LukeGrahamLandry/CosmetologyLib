package ca.lukegrahamlandry.cosmetology.event;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;
import ca.lukegrahamlandry.cosmetology.data.DataStore;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Collection;

public class CosmetologyEventHandler {
    public static void onPlayerLogin(PlayerEntity player){
        for (DataStore data : CosmetologyApi.getSources()){
            data.onPlayerLogin(player);
        }
    }
}
