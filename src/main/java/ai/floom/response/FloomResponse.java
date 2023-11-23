package ai.floom.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class FloomResponse {
    private String messageId;
    private String chatId;
    private List<FloomResponseValue> values;
    private int processingTime;
    @JsonIgnore
    private Object tokenUsage;
}
