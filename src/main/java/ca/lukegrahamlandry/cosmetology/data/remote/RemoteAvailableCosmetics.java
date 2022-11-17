package ca.lukegrahamlandry.cosmetology.data.remote;

import ca.lukegrahamlandry.cosmetology.data.impl.AvailableCosmeticsImpl;
import ca.lukegrahamlandry.cosmetology.util.UrlFetchUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

import static ca.lukegrahamlandry.cosmetology.util.EncodeUtil.GSON;

/**
 * An DataStoreAvailable that is loaded from a remote server.
 */
public class RemoteAvailableCosmetics extends AvailableCosmeticsImpl {
    private String id;
    private String url;

    /**
     * Loads a AvailableCosmeticsImpl from a url.
     * The url must lead to a plaintext json object with the 'id' and 'cosmetics' fields.
     * If the remote object defines the `url` key, its data will be replaced by the data fetched from the new url.
     */
    public static RemoteAvailableCosmetics get(String url){
        JsonElement data = UrlFetchUtil.getUrlJson(url);
        if (data == null) return null;

        try {
            RemoteAvailableCosmetics self = GSON.fromJson(data, RemoteAvailableCosmetics.class);
            if (self.url == null) self.url = url;
            if (!self.url.equals(url)) self.update();
            return self;
        } catch (JsonSyntaxException e){
            System.out.println("Failed to parse RemoteAvailableCosmetics from " + url);
            e.printStackTrace();
            return null;
        }
    }

    public RemoteAvailableCosmetics(String url){
        super();
        this.url = url;
        this.update();
    }

    /**
     * Reloads all data from the remote url
     */
    public void update(){
        RemoteAvailableCosmetics self = get(this.url);
        if (self != null){
            this.cosmetics.clear();
            this.cosmetics.putAll(self.cosmetics);
            this.id = self.id;
            this.url = self.url;
        }
    }
}
