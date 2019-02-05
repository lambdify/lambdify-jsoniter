package lambdify.aws.client.core.jsoniter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.nio.file.*;
import lambdify.aws.client.core.http.AwsClientJsonSerializer;
import lambdify.aws.dynamodb.*;
import lombok.*;
import org.junit.jupiter.api.Test;

/**
 *
 */
class JsoniterAwsClientJsonSerializerTest {

	static final String DYNAMO_DB_EVENT = "tests-resources/dynamo-event-records.json";

	final AwsClientJsonSerializer functionSerializer = new JsoniterAwsClientJsonSerializer();

	@Test @SneakyThrows
	void deserializeAsList() {
		val bytes = Files.readAllBytes( Paths.get( DYNAMO_DB_EVENT ) );
		val input = new String( bytes );
		val dbEvent = functionSerializer.deserializeAsList( input, Record.class );
		assertNotNull( dbEvent );
		assertEquals( 3, dbEvent.size() );
		System.out.println(dbEvent);
	}
}