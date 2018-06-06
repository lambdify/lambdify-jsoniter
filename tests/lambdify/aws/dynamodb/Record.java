package lambdify.aws.dynamodb;

import java.io.Serializable;
import lombok.*;

@Data
public class Record implements Serializable {
    private String eventID;
    private String eventName;
    private String eventVersion;
    private String eventSource;
    private String awsRegion;
}
