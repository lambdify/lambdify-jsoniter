package lambdify.aws.jsoniter;

import com.jsoniter.spi.Config;
import com.jsoniter.extra.JacksonCompatibilityMode;

/**
 *
 */
public interface JsoniterConf {

	Config JACKSON_SUPPORT = new JacksonCompatibilityMode.Builder().build();
}
