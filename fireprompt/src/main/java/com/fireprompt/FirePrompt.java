package com.fireprompt;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.net.URI;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
public class FirePrompt {
    private final String _fireEngineUrl;
    private final String _apiKey;

    // Constructor with environment variable-based initialization
    public FirePrompt() {
        _fireEngineUrl = System.getenv("FIRE_ENGINE_URL");
        _apiKey = System.getenv("FIRE_ENGINE_API_KEY");

        if (_fireEngineUrl == null || _apiKey == null) {
            throw new IllegalArgumentException("FIRE_ENGINE_URL and FIRE_ENGINE_API_KEY environment variables must be set.");
        }
    }

    // Explicit constructor to pass fireEngineUrl and apiKey as arguments
    public FirePrompt(String fireEngineUrl, String apiKey) {
        _fireEngineUrl = fireEngineUrl;
        _apiKey = apiKey;
    }

    public String Fire(String pipelineId, Object input, Map<String, Object> variables) throws IOException, InterruptedException, ExecutionException {
        // Synchronous method to make the HTTP request
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(_fireEngineUrl + "/pipelines/fire?pipelineId=" + pipelineId))
                .header("Api-Key", _apiKey);

        if (input != null) {
            String jsonInput = new Gson().toJson(input);
            requestBuilder.POST(HttpRequest.BodyPublishers.ofString(jsonInput, StandardCharsets.UTF_8));
            requestBuilder.setHeader("Content-Type", "application/json");
        } else {
            requestBuilder.GET();
        }

        HttpRequest request = requestBuilder.build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();

        if (statusCode >= 200 && statusCode < 300) {
            return response.body();
        } else {
            throw new RuntimeException("HTTP request failed with status code: " + statusCode + ", Response: " + response.body());
        }
    }

    public CompletableFuture<String> FireAsync(String pipelineId, Object input, Map<String, Object> variables) {
        // Asynchronous method to make the HTTP request
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(_fireEngineUrl + "/pipelines/fire?pipelineId=" + pipelineId))
                .header("Api-Key", _apiKey);

        if (input != null) {
            String jsonInput = new Gson().toJson(input);
            requestBuilder.POST(HttpRequest.BodyPublishers.ofString(jsonInput, StandardCharsets.UTF_8));
            requestBuilder.setHeader("Content-Type", "application/json");
        } else {
            requestBuilder.GET();
        }

        HttpRequest request = requestBuilder.build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    int statusCode = response.statusCode();

                    if (statusCode >= 200 && statusCode < 300) {
                        return response.body();
                    } else {
                        throw new RuntimeException("HTTP request failed with status code: " + statusCode + ", Response: " + response.body());
                    }
                });
    }
}
