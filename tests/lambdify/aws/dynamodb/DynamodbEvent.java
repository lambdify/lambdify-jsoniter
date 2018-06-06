package lambdify.aws.dynamodb;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
public class DynamodbEvent implements Serializable, Cloneable {

    @JsonProperty("Records")
    private List<DynamodbStreamRecord> records;

    @NoArgsConstructor @Getter @Setter
    @EqualsAndHashCode(callSuper = true) @ToString
    public static class DynamodbStreamRecord extends Record {

        private String eventSourceARN;
    }
}
