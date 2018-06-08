package lambdify.aws.client.core.jsoniter;

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
	public <T> T unserialize(String input, Class<T> clazz) {
		return JsonIterator.deserialize( JsoniterConf.JACKSON_SUPPORT, input, clazz );
	}

	@Override @SuppressWarnings( "unchecked" )
	public <T> List<T> unserializeAsList(String s, Class<T> aClass) {
		val parsedList = JsonIterator.deserialize( s ).asList();
		val list = new ArrayList<T>();
		for ( val entry : parsedList ) {
			list.add( entry.as( aClass ) );
		}
		return list;
	}
}
