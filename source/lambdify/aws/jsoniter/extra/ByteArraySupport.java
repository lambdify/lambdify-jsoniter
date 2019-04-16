package lambdify.aws.jsoniter.extra;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Base64;
import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.*;

/**
 *
 */
public class ByteArraySupport {

	private static boolean enabled = false;

	public static void enable(){
		if ( !enabled ) {
			registerDecoderForByteBuffer();
			registerEncoderForByteBuffer();
			enabled = true;
		}
	}

	private static void registerDecoderForByteBuffer() {
		JsoniterSpi.registerTypeDecoder( ByteBuffer.class, new Decoder() {
			public Object decode(JsonIterator iter) throws IOException {
				Slice slice = iter.readStringAsSlice();
				byte[] bytes = Base64.getDecoder().decode( slice.toString() );
				return ByteBuffer.wrap( bytes );
			}
		} );
	}

	private static void registerEncoderForByteBuffer() {
		JsoniterSpi.registerTypeEncoder( ByteBuffer.class, new Encoder() {
			public void encode(Object obj, JsonStream stream) throws IOException {
				stream.write( 34 );
				ByteBuffer buffer = (ByteBuffer) obj;
				ByteBuffer encoded = Base64.getEncoder().encode( buffer );
				stream.write( encoded.array() );
				stream.write( 34 );
			}
		} );
	}
}
