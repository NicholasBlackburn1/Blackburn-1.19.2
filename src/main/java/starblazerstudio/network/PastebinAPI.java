package starblazerstudio.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import starblazerstudio.utils.Consts;

public class PastebinAPI {
    private String apiDevKey;
    private String apiEndpoint = "https://pastebin.com/api/api_post.php";

    public PastebinAPI(String apiDevKey) {
        this.apiDevKey = apiDevKey;
    }

    public String createPaste(String pasteContent, String pasteName, String pasteFormat, int pastePrivate) {
        Consts.warn("trying to make a new past for Psastbin...");
        try {
            URL url = new URL(apiEndpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String postData = "api_dev_key=" + apiDevKey +
                              "&api_option=paste" +
                              "&api_paste_code=" + URLEncoder.encode(pasteContent, "UTF-8") +
                              "&api_paste_name=" + URLEncoder.encode(pasteName, "UTF-8") +
                              "&api_paste_format=" + pasteFormat +
                              "&api_paste_private=" + pastePrivate;

            Consts.warn("data fpor past bin "+ postData);

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(postData);
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            writer.close();
            reader.close();

            return response.toString().trim();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
