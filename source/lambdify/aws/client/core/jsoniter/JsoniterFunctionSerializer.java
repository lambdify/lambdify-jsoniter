package lambdify.aws.client.core.jsoniter;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Base64;
import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.*;
import lambdify.core.FunctionSerializer;
import lombok.experimental.Accessors;
import lombok.val;

/**
 *
 */
@Accessors(fluent = true)
public class JsoniterFunctionSerializer implements FunctionSerializer {

	public JsoniterFunctionSerializer(){
		registerDecoderForByteBuffer();
		registerEncoderForByteBuffer();
	}

	private void registerDecoderForByteBuffer() {
		JsoniterSpi.registerTypeDecoder( ByteBuffer.class, new Decoder() {
			public Object decode(JsonIterator iter) throws IOException {
				Slice slice = iter.readStringAsSlice();
				byte[] bytes = Base64.getDecoder().decode( slice.toString() );
				return ByteBuffer.wrap( bytes );
			}
		} );
	}

	private void registerEncoderForByteBuffer() {
		JsoniterSpi.registerTypeEncoder( byte[].class, new Encoder() {
			public void encode(Object obj, JsonStream stream) throws IOException {
				stream.write( 34 );
				ByteBuffer buffer = (ByteBuffer) obj;
				ByteBuffer encoded = Base64.getEncoder().encode( buffer );
				stream.write( encoded.array() );
				stream.write( 34 );
			}
		} );
	}

	@Override
	public void serialize(Object object, OutputStream outputStream) throws IOException {
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
