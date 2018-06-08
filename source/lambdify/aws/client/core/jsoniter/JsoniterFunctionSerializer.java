package lambdify.aws.client.core.jsoniter;

import java.io.*;
import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.JsoniterSpi;
import lambdify.aws.client.core.jsoniter.extra.*;
import lambdify.core.FunctionSerializer;
import lombok.experimental.Accessors;
import lombok.val;

/**
 *
 */
@Accessors(fluent = true)
public class JsoniterFunctionSerializer implements FunctionSerializer {

	public JsoniterFunctionSerializer(){
		ByteArraySupport.enable();
		Java8DateTimeSupport.enable();
	}

	@Override
	public void serialize(Object object, OutputStream outputStream) {
		JsonStream.serialize( JsoniterConf.JACKSON_SUPPORT, object, outputStream );
	}

	@Override
	public <T> T deserialize(InputStream inputStream, Class<T> clazz) throws IOException {
		try {
			JsoniterSpi.setCurrentConfig(JsoniterConf.JACKSON_SUPPORT);
			val content = readAsString( inputStream );
			return JsonIterator.deserialize( content, clazz );
		} finally {
			JsoniterSpi.clearCurrentConfig();
		}
	}

	private static String readAsString( InputStream inputStream ) throws IOException {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) != -1) {
			result.write(buffer, 0, length);
		}
		return result.toString("UTF-8");
	}
}
