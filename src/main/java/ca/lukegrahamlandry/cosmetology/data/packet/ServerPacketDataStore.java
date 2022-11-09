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

public class ServerPacketDataStore {
    public BiConsumer<ServerPlayerEntity, BaseMessage> sendPacketToPlayer;
    public DataStoreImpl model;
    public ServerPacketDataStore(String id, BiConsumer<ServerPlayerEntity, BaseMessage> sendPacketToPlayer) {
        this.model = new DataStoreImpl(id);
        this.sendPacketToPlayer = sendPacketToPlayer;
    }

    public void onPlayerLogin(PlayerEntity player) {
        this.syncAllToPlayer((ServerPlayerEntity) player);
        this.syncPlayerToAll((ServerPlayerEntity) player);
        sendPacketToPlayer.accept((ServerPlayerEntity) player, new RegisterMsg(model.getStoreID(), model.getAll()));
    }

    public void syncPlayerToAll(ServerPlayerEntity player) {
        Map<UUID, PlayerCosmeticsCollection> map = new HashMap<>();
        map.put(player.getUUID(), this.model.getOrCreateData(player.getUUID()));
        SyncPlayerCosmeticsMsg message = new SyncPlayerCosmeticsMsg(this.model.getStoreID(), map);

        for (ServerPlayerEntity other : player.level.getServer().getPlayerList().getPlayers()){
            sendPacketToPlayer.accept(other, message);
        }

        this.getStorage(player.server).setDirty();
    }

    public void syncAllToPlayer(ServerPlayerEntity player) {
        Map<UUID, PlayerCosmeticsCollection> map = new HashMap<>();

        for (ServerPlayerEntity other : player.level.getServer().getPlayerList().getPlayers()){
            System.out.println(other.getScoreboardName());
            map.put(other.getUUID(), this.model.getOrCreateData(other.getUUID()));
        }

        SyncPlayerCosmeticsMsg message = new SyncPlayerCosmeticsMsg(this.model.getStoreID(), map);
        sendPacketToPlayer.accept(player, message);
    }

    public DataStorage getStorage(MinecraftServer server){
        return server.overworld().getDataStorage().computeIfAbsent(DataStorage::new, this.model.getStoreID());
    }

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
