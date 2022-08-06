package ca.lukegrahamlandry.cosmetology.data.packet;

import ca.lukegrahamlandry.cosmetology.data.BasicDataStore;
import ca.lukegrahamlandry.cosmetology.data.PlayerCosmeticsCollection;
import ca.lukegrahamlandry.cosmetology.data.packet.network.BaseMessage;
import ca.lukegrahamlandry.cosmetology.data.packet.network.clientbound.SyncPlayerCosmeticsMsg;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.storage.WorldSavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

public class ServerPacketDataStore {
    public BiConsumer<ServerPlayerEntity, BaseMessage> sendPacketToPlayer;
    public BasicDataStore model;
    public ServerPacketDataStore(String id, BiConsumer<ServerPlayerEntity, BaseMessage> sendPacketToPlayer) {
        this.model = new BasicDataStore(id);
        this.sendPacketToPlayer = sendPacketToPlayer;
    }

    public void onPlayerLogin(PlayerEntity player) {
        this.syncAllToPlayer((ServerPlayerEntity) player);
        this.syncPlayerToAll((ServerPlayerEntity) player);
    }

    public void syncPlayerToAll(ServerPlayerEntity player) {
        Map<UUID, PlayerCosmeticsCollection> map = new HashMap<>();
        map.put(player.getUUID(), this.model.getOrCreateData(player.getUUID()));
        SyncPlayerCosmeticsMsg message = new SyncPlayerCosmeticsMsg(this.model.getStoreID(), map);

        for (ServerPlayerEntity other : player.level.getServer().getPlayerList().getPlayers()){
            sendPacketToPlayer.accept(other, message);
        }
    }

    public void syncAllToPlayer(ServerPlayerEntity player) {
        Map<UUID, PlayerCosmeticsCollection> map = new HashMap<>();

        for (ServerPlayerEntity other : player.level.getServer().getPlayerList().getPlayers()){
            map.put(other.getUUID(), this.model.getOrCreateData(other.getUUID()));
        }

        SyncPlayerCosmeticsMsg message = new SyncPlayerCosmeticsMsg(this.model.getStoreID(), map);
        sendPacketToPlayer.accept(player, message);
    }

    // TODO: impliment saving the state
    class DataStorage extends WorldSavedData {
        public DataStorage(String p_i2141_1_) {
            super(p_i2141_1_);
        }

        @Override
        public void load(CompoundNBT tag) {

        }

        @Override
        public CompoundNBT save(CompoundNBT tag) {
            return null;
        }
    }
}
