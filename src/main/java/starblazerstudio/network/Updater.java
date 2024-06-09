/***
 * this is the main updater class
 */
package starblazerstudio.network;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import starblazerstudio.utils.Consts;

public class Updater {

    // Method to check for updates from the FoxBurn API
    public void checkForUpdates() {
        try {
            URL url = new URL(Consts.githubReleaseUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            Consts.warn("Response Code: " + responseCode);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                Consts.warn("Response: " + response.toString());

                JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
                String latestVersion = jsonObject.get("latest_version").getAsString();
                Consts.warn("Latest version from API: " + latestVersion);

                compareVersions(latestVersion, Consts.currentGameVersion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to compare release versions from the repository and the game version
    private void compareVersions(String latestVersion, String currentVersion) {
        Consts.warn("Current game version: " + currentVersion);

        if (latestVersion.equals(currentVersion)) {
            Consts.warn("You are using the latest version.");
        } else {
            Consts.warn("A new version is available: " + latestVersion);
            Consts.warn("Please update your game to the latest version.");
        }
    }
}
