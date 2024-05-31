/***
 * 
 * this is the verifyer for the status of the users uuid (to see if they can access the freinds only  content)
 */

package starblazerstudio.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.client.Minecraft;

public class StatusVerifiyer {

    // generates respnce for users stats
    public void veryifiyUser(Minecraft mc){

        Consts.warn("Startinf to run verify answeer");
           try {
            // The URL of the endpoint
            URL url = new URL(Consts.verifiyerurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");

            // JSON payload
            String jsonInputString = "{\"uuid\": \""+mc.getUser().getUuid().toString()+"\"}";
            Consts.error("Json Paylod -> "+ jsonInputString.toString());

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Read the response
            int responseCode = conn.getResponseCode();
            Consts.warn("Response Code: " + responseCode);
            int status;

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
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
                Consts.warn("User can access the Lewd content.");
                Consts.ishorny = true;

            } else {
                Consts.warn("User cannot access the content."); // Print a warning or handle accordingly
               Consts.ishorny = false;
            }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
    