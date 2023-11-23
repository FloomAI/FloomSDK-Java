package ai.floom.response;

import lombok.Data;

@Data
public class FloomResponseValue {
    public String format;
    public String value;
    public String type;
    public String b64;
    public String url;
}
