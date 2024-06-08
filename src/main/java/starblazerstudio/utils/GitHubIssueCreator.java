package starblazerstudio.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * GitHubIssueCreator is a Java class that provides functionality to create GitHub issues
 * programmatically using the GitHub API.
 */
public class GitHubIssueCreator {

    private final String repoOwner; // GitHub repository owner's username
    private final String repoName; // GitHub repository name
    private final String token; // Personal access token for authentication

    /**
     * Constructor for GitHubIssueCreator class.
     *
     * @param repoOwner GitHub repository owner's username
     * @param repoName  GitHub repository name
     * @param token     Personal access token for authentication
     */
    public GitHubIssueCreator(String repoOwner, String repoName, String token) {
        this.repoOwner = repoOwner;
        this.repoName = repoName;
        this.token = token;
    }

    /**
     * Creates a new GitHub issue with the specified title, body, and labels.
     *
     * @param title  Title of the issue
     * @param body   Description or body of the issue
     * @param label  Label of the issue
     * @throws IOException If an I/O error occurs while making HTTP request
     */
    public void createIssue(String title, String body, String label) throws IOException {
        // Construct GitHub API URL for creating issues
        String apiUrl = String.format("https://api.github.com/repos/%s/%s/issues", repoOwner, repoName);

        // Log the URL and the API key
        Consts.warn("GITHUB URL: " + apiUrl);
        Consts.warn("Token: " + key.githubkey);

        // Create HTTP client
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(apiUrl);

        // Set request headers
        httpPost.setHeader("Authorization", "Bearer " + key.githubkey);
        httpPost.setHeader("Accept", "application/vnd.github+json");
        httpPost.setHeader("X-GitHub-Api-Version", "2022-11-28");

        // Validate and prepare labels
        String jsonPayload;
        if (label == null || label.trim().isEmpty()) {
            jsonPayload = String.format("{\"title\":\"%s\",\"body\":\"%s\"}", title, body);
        } else {
            jsonPayload = String.format("{\"title\":\"%s\",\"body\":\"%s\",\"labels\":[\"%s\"]}", title, body, label);
        }

        Consts.warn("Json Payload to Github -> " + jsonPayload);

        // Set request body
        StringEntity entity = new StringEntity(jsonPayload);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");

        // Execute the request
        HttpResponse response = httpClient.execute(httpPost);

        // Check response status
        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println("Response Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);

        // Close the HttpClient
        httpClient.close();

        // Log response for debugging
        if (statusCode != 201) {
            System.err.println("Failed to create issue. Response: " + responseBody);
        } else {
            System.out.println("Issue created successfully.");
        }
    }
}
