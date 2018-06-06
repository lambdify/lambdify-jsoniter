package lambdify.aws.client.core.jsoniter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.*;
import lambdify.aws.dynamodb.DynamodbEvent;
import lambdify.core.FunctionSerializer;
import lombok.*;
import org.junit.jupiter.api.*;

/**
 *
 */
class JsoniterFunctionSerializerTest {

	static final String DYNAMO_DB_EVENT = "tests-resources/dynamo-event.json";

	final FunctionSerializer functionSerializer = new JsoniterFunctionSerializer();

	@SneakyThrows
	@Test @DisplayName( "Can deserialize entities which 'naming strategy' was defined with Jackson Annotation" )
	void deserialize() {
		try ( val input = new FileInputStream( DYNAMO_DB_EVENT ) ) {
			val dbEvent = functionSerializer.deserialize( input, DynamodbEvent.class );
			assertNotNull( dbEvent.getRecords() );
		}
	}
}