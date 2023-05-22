package ca.lukegrahamlandry.cosmetology.forge.events;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;
import ca.lukegrahamlandry.cosmetology.data.packet.ServerPacketDataStore;
import ca.lukegrahamlandry.cosmetology.event.CosmetologyEventHandler;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEventHandler {
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getPlayer().level.isClientSide()){
            for (ServerPacketDataStore data : CosmetologyApi.serverPacketDataSources.values()) {
                data.onPlayerLogin(event.getPlayer());
            }
        } else {
            CosmetologyEventHandler.onPlayerLogin(event.getPlayer());
        }
    }
}
