package lambdify.aws.client.core.jsoniter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.*;

import lambdify.aws.dynamodb.DynamodbEvent;
import lambdify.core.AwsFunctionSerializer;
import lombok.*;
import org.junit.jupiter.api.*;

/**
 *
 */
class JsoniterAwsFunctionSerializerTest {

	static final String DYNAMO_DB_EVENT = "tests-resources/dynamo-event.json";

	final AwsFunctionSerializer functionSerializer = new JsoniterAwsFunctionSerializer();

	@SneakyThrows
	@Test @DisplayName( "Can deserialize entities which 'naming strategy' was defined with Jackson Annotation" )
	void deserialize() {
		val input = Files.readAllBytes( Paths.get( DYNAMO_DB_EVENT ) );
		val dbEvent = functionSerializer.deserialize( input, DynamodbEvent.class );
		assertNotNull( dbEvent.getRecords() );
	}
}