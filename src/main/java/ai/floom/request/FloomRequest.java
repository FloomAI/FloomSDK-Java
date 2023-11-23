package ai.floom.request;

import lombok.Data;

import java.util.Map;

@Data
public class FloomRequest {
    private String pipelineId;
    private String chatId;
    private String input;
    private Map<String, String> variables;
}
