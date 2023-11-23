package ai.floom.tests;

import ai.floom.FloomClient;
import ai.floom.response.FloomResponse;
import ai.floom.response.FloomResponseValue;
import org.junit.jupiter.api.Test;

public class FloomClientTests {

    @Test
    public void testFloomClient() {
        FloomClient floomClient = new FloomClient("http://127.0.0.1:80", "COqRR8qLz4RrXygsDoYMXRvDJheXj3MO");
        try {
            FloomResponse response = floomClient.run("docs-pipeline-v1", "abcdefghijklmnop", "Who was the first US president?", null);
            // Print the response properties
            System.out.println("Pipeline Response Valid");
            System.out.println("Message ID: " + response.getMessageId());
            System.out.println("Chat ID: " + response.getChatId());
            System.out.println("Processing Time: " + response.getProcessingTime());
            for (FloomResponseValue value : response.getValues()) {
                System.out.println("Value - Format: " + value.getFormat() + ", Value: " + value.getValue());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Test
    public void testAsyncFloomClient() {
        FloomClient floomClient = new FloomClient("http://127.0.0.1:80", "COqRR8qLz4RrXygsDoYMXRvDJheXj3MO");

        floomClient.runAsync("docs-pipeline-v1", "abcdefghijklmnop", "Who was the first US president?", null)
                .thenAccept(response -> {
                    System.out.println("Pipeline Response Valid");
                    System.out.println("Message ID: " + response.getMessageId());
                    System.out.println("Chat ID: " + response.getChatId());
                    System.out.println("Processing Time: " + response.getProcessingTime());
                    for (FloomResponseValue value : response.getValues()) {
                        System.out.println("Value - Format: " + value.getFormat() + ", Value: " + value.getValue());
                    }
                    // Process response
                })
                .exceptionally(e -> {
                    System.out.println("Error: " + e.getMessage());
                    return null;
                }).join();
    }


}
