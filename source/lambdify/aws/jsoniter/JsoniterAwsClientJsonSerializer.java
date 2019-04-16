package lambdify.aws.jsoniter;

import java.io.*;
import java.util.*;
import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import lambdify.aws.client.core.http.AwsClientJsonSerializer;
import lombok.experimental.Accessors;
import lombok.val;

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
	public byte[] serializeAsBytes(Object object) {
		try ( val byteArray = new ByteArrayOutputStream()) {
			JsonStream.serialize( JsoniterConf.JACKSON_SUPPORT, object, byteArray );
			return byteArray.toByteArray();
		} catch ( IOException e ) {
			throw new IllegalStateException( e );
		}
	}

	@Override
	public <T> T deserialize(String input, Class<T> clazz) {
		return JsonIterator.deserialize( JsoniterConf.JACKSON_SUPPORT, input, clazz );
	}

	@Override @SuppressWarnings( "unchecked" )
	public <T> List<T> deserializeAsList(String s, Class<T> aClass) {
		val parsedList = JsonIterator.deserialize( s ).asList();
		val list = new ArrayList<T>();
		for ( val entry : parsedList ) {
			list.add( entry.as( aClass ) );
		}
		return list;
	}
}
