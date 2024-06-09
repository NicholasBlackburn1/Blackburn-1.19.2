/***
 * 
 * this is the verifyer for the status of the users uuid (to see if they can access the freinds only  content)
 */

 package starblazerstudio.network;

 import java.io.BufferedReader;
 import java.io.InputStreamReader;
 import java.io.OutputStream;
 import java.net.HttpURLConnection;
 import java.net.URL;
 
 import com.google.gson.JsonObject;
 import com.google.gson.JsonParser;
 
 import net.minecraft.client.Minecraft;
 import starblazerstudio.utils.Consts;
 
 public class StatusVerifier {
 
     // Generates response for user's status
     public void verifyPreimumUser(Minecraft mc) {
         Consts.warn("Starting to run verify answer");
         try {
             // The URL of the endpoint
             URL url = new URL(Consts.verifiyerurl);
             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             conn.setDoOutput(true);
             conn.setRequestMethod("POST");
             conn.setRequestProperty("Content-Type", "application/json; utf-8");
             conn.setRequestProperty("Accept", "application/json");
 
             // JSON payload
             String jsonInputString = "{\"uuid\": \"" + mc.getUser().getUuid().toString() + "\"}";
             Consts.error("Json Payload -> " + jsonInputString);
 
             try (OutputStream os = conn.getOutputStream()) {
                 byte[] input = jsonInputString.getBytes("utf-8");
                 os.write(input, 0, input.length);
             }
 
             // Read the response
             int responseCode = conn.getResponseCode();
             Consts.warn("Response Code: " + responseCode);
 
             try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                 StringBuilder response = new StringBuilder();
                 String responseLine;
                 while ((responseLine = br.readLine()) != null) {
                     response.append(responseLine.trim());
                 }
                 Consts.warn("Response: " + response.toString());
 
                 // Parse the JSON string
                 JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
 
                 // Extract the integer value
                 int canAccess = jsonObject.get("can_access").getAsInt();
 
                 if (canAccess == 1) {
                     Consts.warn("User can access the content.");
                     Consts.ishorny = true;
                 } else {
                     Consts.warn("User cannot access the content.");
                     Consts.ishorny = false;
                 }
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
 
     // Sets the online status of the user
     public void setOnlineStatus(Minecraft mc, int isConnected) {
         try {
             // The URL of the endpoint
             URL url = new URL(Consts.setOnlineurl);
             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             conn.setDoOutput(true);
             conn.setRequestMethod("POST");
             conn.setRequestProperty("Content-Type", "application/json; utf-8");
             conn.setRequestProperty("Accept", "application/json");
 
             // JSON payload
             String jsonInputString = "{\"uuid\": \"" + mc.getUser().getUuid().toString() + "\", \"is_online\": " + isConnected + "}";
             Consts.error("Json Payload -> " + jsonInputString);
 
             try (OutputStream os = conn.getOutputStream()) {
                 byte[] input = jsonInputString.getBytes("utf-8");
                 os.write(input, 0, input.length);
             }
 
             // Read the response
             int responseCode = conn.getResponseCode();
             Consts.warn("Response Code: " + responseCode);
 
             try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                 StringBuilder response = new StringBuilder();
                 String responseLine;
                 while ((responseLine = br.readLine()) != null) {
                     response.append(responseLine.trim());
                 }
                 Consts.warn("Response: " + response.toString());
 
                 // Parse the JSON string
                 JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
 
                 // Check if the response contains the expected data
                 if (jsonObject.get("is_online").getAsInt() == 1)  {
                     Consts.warn("Online status updated successfully.");
                 } else {
                     Consts.warn("Failed to update online status.");
                 }
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

     // check release 
     public void checkGithubRelease() {
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
                String latestRelease = jsonObject.get("latest_release").getAsString();

                Consts.warn("Latest GitHub release: " + latestRelease);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 }
 