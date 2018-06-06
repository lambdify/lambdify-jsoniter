package lambdify.aws.client.core.jsoniter;

import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import lambdify.aws.client.core.http.AwsClientJsonSerializer;
import lombok.experimental.Accessors;

/**
 *
 */
@Accessors(fluent = true)
public class JsoniterAwsClientJsonSerializer implements AwsClientJsonSerializer {

	@Override
	public String serialize( Object object ) {
		return JsonStream.serialize( JsoniterConf.JACKSON_SUPPORT, object );
	}

	@Override
	public <T> T unserialize(String input, Class<T> clazz) {
		return JsonIterator.deserialize( JsoniterConf.JACKSON_SUPPORT, input, clazz );
	}
}
