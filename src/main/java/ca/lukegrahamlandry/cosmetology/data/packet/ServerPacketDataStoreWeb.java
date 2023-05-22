package ca.lukegrahamlandry.cosmetology.data.packet;

import ca.lukegrahamlandry.cosmetology.CosmetologyApi;
import ca.lukegrahamlandry.cosmetology.data.DataStoreImpl;
import ca.lukegrahamlandry.cosmetology.data.PlayerCosmeticsCollection;
import ca.lukegrahamlandry.cosmetology.data.packet.network.BaseMessage;
import ca.lukegrahamlandry.cosmetology.data.packet.network.clientbound.RegisterMsg;
import ca.lukegrahamlandry.cosmetology.data.packet.network.clientbound.SyncPlayerCosmeticsMsg;
import ca.lukegrahamlandry.cosmetology.data.remote.RemoteAvailableCosmetics;
import ca.lukegrahamlandry.cosmetology.util.UrlFetchUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.WorldSavedData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

import static ca.lukegrahamlandry.cosmetology.util.EncodeUtil.GSON;

/**
 * A ServerPacketDataStore but it loads unlocked cosmetics from a url.
 */
public class ServerPacketDataStoreWeb extends ServerPacketDataStore {
    protected String unlockedUrl;
    public ServerPacketDataStoreWeb(String id, BiConsumer<ServerPlayerEntity, BaseMessage> sendPacketToPlayer, String unlockedUrl) {
        super(id, sendPacketToPlayer);
        this.unlockedUrl = unlockedUrl;
    }

    // TODO: its silly that the unlocks are still stored in world data even through they get read every time.
    @Override
    public void onPlayerLogin(PlayerEntity player) {
        // Retrieve latest unlocks and apply this player's new available cosmetics list.
        UnlockedCosmetics data = UnlockedCosmetics.get(this.unlockedUrl);
        if (data != null && data.unlocked.containsKey(player.getUUID())){
            PlayerCosmeticsCollection cosmetics = this.model.getOrCreateData(player.getUUID());
            cosmetics.unlocked = data.unlocked.get(player.getUUID());
        }
        // Then sync everything as usual.
        // The packet handlers deal with removing any equipped cosmetics that are no longer unlocked.
        super.onPlayerLogin(player);
    }

    static class UnlockedCosmetics {
        HashMap<UUID, ArrayList<ResourceLocation>> unlocked = new HashMap<>();

        public static UnlockedCosmetics get(String url){
            try {
                JsonElement data = UrlFetchUtil.getUrlJson(url);
                if (data == null) return null;
                CosmetologyApi.debugLog("Got json for UnlockedCosmetics from " + url + ": " + data);
                return GSON.fromJson(data, UnlockedCosmetics.class);
            } catch (JsonSyntaxException e){
                CosmetologyApi.errorLog("Failed to parse UnlockedCosmetics from " + url);
                e.printStackTrace();
                return null;
            }
        }
    }
}
