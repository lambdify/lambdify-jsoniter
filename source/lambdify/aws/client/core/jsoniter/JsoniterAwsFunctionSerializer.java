package lambdify.aws.client.core.jsoniter;

import java.io.*;
import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.JsoniterSpi;
import lambdify.aws.client.core.jsoniter.extra.*;
import lambdify.core.AwsFunctionSerializer;
import lombok.experimental.Accessors;
import lombok.val;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 *
 */
@Accessors(fluent = true)
public class JsoniterAwsFunctionSerializer implements AwsFunctionSerializer {

	public JsoniterAwsFunctionSerializer(){
		ByteArraySupport.enable();
		Java8DateTimeSupport.enable();
	}

	@Override
	public byte[] serialize(Object object) {
		val bytes = new ByteArrayOutputStream();
		JsonStream.serialize( JsoniterConf.JACKSON_SUPPORT, object, bytes );
		return bytes.toByteArray();
	}

	@Override
	public <T> T deserialize(byte[] input, Class<T> clazz) {
		try {
			JsoniterSpi.setCurrentConfig(JsoniterConf.JACKSON_SUPPORT);
			val content = new String( input, UTF_8 );
			return JsonIterator.deserialize( content, clazz );
		} finally {
			JsoniterSpi.clearCurrentConfig();
		}
	}
}
