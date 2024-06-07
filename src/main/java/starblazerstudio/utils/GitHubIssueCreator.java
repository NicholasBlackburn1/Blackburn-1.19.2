package starblazerstudio.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GitHubIssueCreator is a Java class that provides functionality to create GitHub issues
 * programmatically using the GitHub API.
 * 
 * TODO: Remember that the GitHub key is in the key.java
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
     * @param labels List of labels/tags for the issue
     * @throws IOException If an I/O error occurs while making HTTP request
     */
    public void createIssue(String title, String body, List<String> labels) throws IOException {
        // Construct GitHub API URL for creating issues
        String apiUrl = String.format("https://api.github.com/repos/%s/%s/issues", repoOwner, repoName);

        // Create HTTP client
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(apiUrl);

        // Set request headers
        httpPost.setHeader("Authorization", "token " + token);
        httpPost.setHeader("Accept", "application/vnd.github.v3+json");

        // JSON payload for creating an issue
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\"title\":\"").append(title).append("\",");
        jsonBuilder.append("\"body\":\"").append(body).append("\",");
        jsonBuilder.append("\"labels\":").append(labelsToJsonArray(labels)).append("}");

        // Set request body
        StringEntity entity = new StringEntity(jsonBuilder.toString());
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");

        // Execute the request
        HttpResponse response = httpClient.execute(httpPost);

        // Check response status
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Response Code : " + statusCode);

        // Close the HttpClient
        httpClient.close();
    }

    /**
     * Converts a list of labels to a JSON array string.
     *
     * @param labels List of labels
     * @return JSON array string representing the labels
     */
    private String labelsToJsonArray(List<String> labels) {
        // Convert the list of labels to a JSON array string
        String labelsJsonArray = labels.stream()
                                       .map(label -> "\"" + label + "\"")
                                       .collect(Collectors.joining(",", "[", "]"));
        return labelsJsonArray;
    }
}
