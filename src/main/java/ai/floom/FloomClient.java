package ai.floom;


import ai.floom.request.FloomRequest;
import ai.floom.response.FloomResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FloomClient {

    private final String url;
    private final String apiKey;

    public FloomClient(String url, String apiKey) {
        this.url = url;
        this.apiKey = apiKey;
    }

    private HttpRequest buildRequest(String pipelineId, String chatId, String input, Map<String, String> variables) throws Exception {
        FloomRequest floomRequest = new FloomRequest();
        floomRequest.setPipelineId(pipelineId);
        floomRequest.setChatId(chatId);
        floomRequest.setInput(input);
        floomRequest.setVariables(variables);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(floomRequest);

        return HttpRequest.newBuilder()
                .uri(URI.create(url + "/v1/Pipelines/Run"))
                .header("Api-Key", apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    }

    public FloomResponse run(String pipelineId, String chatId, String input, Map<String, String> variables) throws Exception {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(160))
                .build();

        HttpRequest request = buildRequest(pipelineId, chatId, input, variables);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), FloomResponse.class);
        } else {
            throw new Exception("Failed to run the pipeline: " + response.body());
        }
    }

    public CompletableFuture<FloomResponse> runAsync(String pipelineId, String chatId, String input, Map<String, String> variables) {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(160))
                .build();

        HttpRequest request;
        try {
            request = buildRequest(pipelineId, chatId, input, variables);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() >= 200 && response.statusCode() < 300) {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            return objectMapper.readValue(response.body(), FloomResponse.class);
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to parse response: " + response.body(), e);
                        }
                    } else {
                        throw new RuntimeException("Failed to run the pipeline: " + response.body());
                    }
                });
    }
}