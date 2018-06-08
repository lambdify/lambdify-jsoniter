package lambdify.aws.client.core.jsoniter.extra;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.*;
import lombok.val;

/**
 *
 */
public class Java8DateTimeSupport {

	public static String AWS_DATE_FORMAT = "yyyyMMdd'T'HHmmss'Z'";
	private static boolean enabled = false;

	public static void enable() {
		enable( AWS_DATE_FORMAT );
	}

	public static void enable( String format ) {
		if ( enabled )
			throw new UnsupportedOperationException( "Java8 DateTime support is already enabled" );

		val formatter = DateTimeFormatter.ofPattern( format );
		registerLocalDateTimeDecoder( formatter );
		registerLocalDateTimeEncoder( formatter );
	}

	private static void registerLocalDateTimeDecoder( final DateTimeFormatter formatter ) {
		JsoniterSpi.registerTypeDecoder( LocalDateTime.class, new Decoder() {
			public LocalDateTime decode(JsonIterator iter) throws IOException {
				val slice = iter.readStringAsSlice();
				return LocalDateTime.parse( slice.toString(), formatter );
			}
		} );
	}

	private static void registerLocalDateTimeEncoder( final DateTimeFormatter formatter ) {
		JsoniterSpi.registerTypeEncoder( LocalDateTime.class, new Encoder() {
			public void encode(Object obj, JsonStream stream) throws IOException {
				stream.write( 34 );
				LocalDateTime buffer = (LocalDateTime) obj;
				stream.write( buffer.format( formatter ).getBytes( "UTF-8" ) );
				stream.write( 34 );
			}
		} );
	}
}
