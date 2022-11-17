package ca.lukegrahamlandry.cosmetology.data.packet;

import ca.lukegrahamlandry.cosmetology.data.DataStoreImpl;
import ca.lukegrahamlandry.cosmetology.data.PlayerCosmeticsCollection;
import ca.lukegrahamlandry.cosmetology.data.packet.network.BaseMessage;
import ca.lukegrahamlandry.cosmetology.data.packet.network.clientbound.RegisterMsg;
import ca.lukegrahamlandry.cosmetology.data.packet.network.clientbound.SyncPlayerCosmeticsMsg;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.WorldSavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

/**
 * A DataStore that saves data on the server and syncs it to clients with packets.
 * Requires the same version of the mod to be installed on the server and all connected clients
 * but does not rely on any external web services.
 */
public class ServerPacketDataStore {
    public BiConsumer<ServerPlayerEntity, BaseMessage> sendPacketToPlayer;
    public DataStoreImpl model;
    public ServerPacketDataStore(String id, BiConsumer<ServerPlayerEntity, BaseMessage> sendPacketToPlayer) {
        this.model = new DataStoreImpl(id);
        this.sendPacketToPlayer = sendPacketToPlayer;
    }

    /**
     * Must be called on the PlayerEvent.PlayerLoggedInEvent
     */
    public void onPlayerLogin(PlayerEntity player) {
        this.syncAllToPlayer((ServerPlayerEntity) player);
        this.syncPlayerToAll((ServerPlayerEntity) player);
        sendPacketToPlayer.accept((ServerPlayerEntity) player, new RegisterMsg(model.getStoreID(), model.getAll()));
    }

    /**
     * Syncs cosmetic information about the specified player to all clients.
     * Called to resync after a player changes their settings.
     */
    public void syncPlayerToAll(ServerPlayerEntity player) {
        Map<UUID, PlayerCosmeticsCollection> map = new HashMap<>();
        map.put(player.getUUID(), this.model.getOrCreateData(player.getUUID()));
        SyncPlayerCosmeticsMsg message = new SyncPlayerCosmeticsMsg(this.model.getStoreID(), map);

        for (ServerPlayerEntity other : player.level.getServer().getPlayerList().getPlayers()){
            sendPacketToPlayer.accept(other, message);
        }

        this.setStorageDirty(player.server);
    }

    /**
     * Syncs cosmetic information about all players to the specified player's client.
     * Called to resync when a player logs in.
     */
    public void syncAllToPlayer(ServerPlayerEntity player) {
        Map<UUID, PlayerCosmeticsCollection> map = new HashMap<>();

        for (ServerPlayerEntity other : player.level.getServer().getPlayerList().getPlayers()){
            map.put(other.getUUID(), this.model.getOrCreateData(other.getUUID()));
        }

        SyncPlayerCosmeticsMsg message = new SyncPlayerCosmeticsMsg(this.model.getStoreID(), map);
        sendPacketToPlayer.accept(player, message);
    }

    /**
     * Must be called to update the model from saved world data on the FMLServerStartingEvent
     */
    public DataStorage getStorage(MinecraftServer server){
        return server.overworld().getDataStorage().computeIfAbsent(DataStorage::new, this.model.getStoreID());
    }

    /**
     * Must be called whenever a player changes their settings to save when the server stops
     */
    public void setStorageDirty(MinecraftServer server){
        getStorage(server).setDirty();
    }

    /**
     * Encodes all player cosmetics information as json and saves it with the server's world data.
     * This reads/writes directly to the model of the parent ServerPacketDataStore
     * - It must be retrieved to update the model when the server starts
     * - It must be setDirty whenever a player changes their settings to save when the server stops
     */
    public class DataStorage extends WorldSavedData {
        public DataStorage() {
            super(ServerPacketDataStore.this.model.getStoreID());
        }

        @Override
        public void load(CompoundNBT tag) {
            ServerPacketDataStore.this.model.read(tag.getString("data"));
            System.out.println("load " + tag.getString("data"));
        }

        @Override
        public CompoundNBT save(CompoundNBT tag) {
            tag.putString("data", ServerPacketDataStore.this.model.write());
            System.out.println("save " + tag.getString("data"));
            return tag;
        }
    }
}
