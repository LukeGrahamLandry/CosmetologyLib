package ca.lukegrahamlandry.cosmetology.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class UrlFetchUtil {
    public static JsonElement getUrlJson(String url){
        String response = getUrlText(url);
        if (response == null) return null;
        return new JsonParser().parse(response);
    }

    public static String getUrlText(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            InputStream responseStream = connection.getInputStream();

            return new BufferedReader(
                    new InputStreamReader(responseStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
