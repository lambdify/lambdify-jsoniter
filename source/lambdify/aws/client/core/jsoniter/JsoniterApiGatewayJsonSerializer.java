package lambdify.aws.client.core.jsoniter;

import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 *
 */
@Getter @Accessors(fluent = true)
public class JsoniterApiGatewayJsonSerializer implements lambdify.apigateway.Serializer {

	final String contentType = "application/json";

	@Override
	public Stringified toString(Object object) {
		return Stringified.plainText( JsonStream.serialize( JsoniterConf.JACKSON_SUPPORT, object ) );
	}

	@Override
	public <T> T toObject(String input, Class<T> clazz, boolean b) {
		return JsonIterator.deserialize( JsoniterConf.JACKSON_SUPPORT, input, clazz );
	}
}
