package ca.lukegrahamlandry.cosmetology;

import ca.lukegrahamlandry.cosmetology.data.api.DataStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CosmetologyApi {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "cosmetology";

    static Map<String, DataStore> clientDataSources = new HashMap<>();

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
}
