package starblazerstudio.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import starblazerstudio.utils.Consts;

public class Updater {

    // Method to check for updates from the custom API
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
                JsonArray releases = jsonObject.getAsJsonArray("releases");

                if (releases.size() > 0) {
                    // Find the latest version by comparing all versions
                    String latestVersion = "";
                    String commitUrl = "";

                    for (int i = 0; i < releases.size(); i++) {
                        JsonObject release = releases.get(i).getAsJsonObject();
                        String version = release.get("version").getAsString().replace("B", "");
                        String commiturl = release.get("commit_url").getAsString();

                        if (compareVersions(version, latestVersion) > 0) {
                            latestVersion = version;
                           
                        }
                    }

                    Consts.warn("Latest version from API: " + latestVersion);
                    Consts.warn("Latest version commit URL: " + commitUrl);

                    if (compareVersions(latestVersion, Consts.currentGameVersion.replace("B", "")) > 0) {
                        Consts.warn("A new version is available: " + latestVersion);
                        Consts.warn("Please update your game to the latest version.");
                    } else {
                        Consts.warn("You are using the latest version.");
                    }
                } else {
                    Consts.warn("No releases found.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to compare two version strings
    private int compareVersions(String version1, String version2) {
        String[] levels1 = version1.split("\\.");
        String[] levels2 = version2.split("\\.");

        int length = Math.max(levels1.length, levels2.length);
        for (int i = 0; i < length; i++) {
            int v1 = i < levels1.length ? Integer.parseInt(levels1[i]) : 0;
            int v2 = i < levels2.length ? Integer.parseInt(levels2[i]) : 0;
            if (v1 < v2) {
                return -1;
            }
            if (v1 > v2) {
                return 1;
            }
        }
        return 0;
    }
}
