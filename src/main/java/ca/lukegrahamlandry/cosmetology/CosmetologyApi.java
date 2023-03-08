package ca.lukegrahamlandry.cosmetology;

import ca.lukegrahamlandry.cosmetology.data.api.DataStore;
import ca.lukegrahamlandry.cosmetology.data.packet.ServerPacketDataStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CosmetologyApi {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "cosmetology";
    public static boolean debugMode = false;

    static Map<String, DataStore> clientDataSources = new HashMap<>();
    public static Map<String, ServerPacketDataStore> serverPacketDataSources = new HashMap<>();

    // this should ONLY be called from the CLIENT side
    public static void registerSource(DataStore data){
        clientDataSources.put(data.getStoreID(), data);
    }

    public static Collection<DataStore> getSources() {
        return clientDataSources.values();
    }

    public static DataStore getSource(String sourceID) {
        return clientDataSources.get(sourceID);
    }

    public static void debugLog(String msg) {
        if (debugMode) LOGGER.debug(msg);
    }

    public static void errorLog(String msg) {
        LOGGER.error(msg);
    }
}
