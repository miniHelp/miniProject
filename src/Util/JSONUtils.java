package Util;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JSONUtils {

	private static final Logger log = Logger.getLogger(JSONUtils.class.getName());

	private final static ObjectMapper mapper = new ObjectMapper();
	private final static JsonFactory jsonFactory;
	public final static String EMPTY_JSON_STRING = "{}";
	public final static String EMPTY_JSON_ARRAY_STRING = "[]";

	public final static String STATUS_200 = "{\"status\" : 200}";
	
	static {
		jsonFactory = mapper.getFactory();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
		mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
		mapper.configure(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM, false);
		mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		DateFormat DATE_FORMAT_ISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		DATE_FORMAT_ISO8601.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
		mapper.setDateFormat(DATE_FORMAT_ISO8601);
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		mapper.setFilterProvider(
			new SimpleFilterProvider().setDefaultFilter(SimpleBeanPropertyFilter.serializeAll()));
	}

	public static JsonFactory getFactory(){
		return jsonFactory;
	}
	
	public static ObjectMapper getObjectMapper(){
		return mapper;
	}

	/**
	 * 
	 * create json String
	 * 
	 * @param args
	 *            key1, value1, key2, value2....
	 * @return
	 */
	public static String getJSONString(String... args) {
		if (args.length % 2 != 0) {
			throw new RuntimeException("error number of argements");
		}
		JsonFactory jfactory = getFactory();
		StringWriter out = new StringWriter();
		JsonGenerator jGenerator = null;

		try {
			jGenerator = jfactory.createGenerator(out);

			jGenerator.writeStartObject();
			for (int i = 0; i < args.length; i = i + 2) {
				jGenerator.writeStringField(args[i], args[i + 1]);
			}
			jGenerator.writeEndObject();
		} catch (IOException e) {
		}finally{
			JSONUtils.close(jGenerator);
		}
		return out.toString();
	}

	public static String getJSONString(Map<String, ?> args) {
		StringWriter out = new StringWriter();
		JsonGenerator jGenerator = null;

		try {
			jGenerator = jsonFactory.createGenerator(out);

			jGenerator.writeStartObject();
			for (Entry<String, ?> entry : args.entrySet()) {
				jGenerator.writeStringField(entry.getKey(), String.valueOf(entry.getValue()));
			}
			jGenerator.writeEndObject();
		} catch (IOException e) {
		}finally{
			JSONUtils.close(jGenerator);
		}
		return out.toString();
	}



	public static byte[] toJsonBytes(Object instance) {
		try {
			return mapper.writeValueAsBytes(instance);
		} catch (JsonProcessingException e) {
		}
		return null;
	}

	public static String toJsonString(Object instance) {
		if(null == instance){
			return null;
		}
		byte[] byteResult = toJsonBytes(instance);
		if(null == byteResult){
			return null;
		}
		try {
			return new String(byteResult, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return new String(byteResult);
	}

	public static String toJsonString(String key, Collection<String> values)
		throws JsonGenerationException, IOException {
		StringWriter out = new StringWriter();
		JsonGenerator jGenerator = null;

		try {
			jGenerator = JSONUtils.getFactory().createGenerator(out);
			jGenerator.writeStartObject();
			jGenerator.writeArrayFieldStart(key);
			for (String value : values) {
				jGenerator.writeString(value);
			}
			jGenerator.writeEndArray();
			jGenerator.writeEndObject();
		} finally {
			JSONUtils.close(jGenerator);
		}

		return out.toString();
	}
	
	/**
	 * map object to json string with include/exclude fields, object needs to
	 * declared @JsonFilter("BaseFilter")
	 * 
	 * @param instance
	 *            target object
	 * @param include
	 *            determine include fields or exclude fields
	 * @param fields
	 *            fields include/exclude to serialize
	 * @return
	 * @throws com.fasterxml.jackson.core.JsonProcessingException
	 */
	public static String toJsonWithFields(Object instance, boolean include, String... fields)
		throws JsonProcessingException {
		SimpleBeanPropertyFilter filter = include
			? SimpleBeanPropertyFilter.filterOutAllExcept(fields)
			: SimpleBeanPropertyFilter.serializeAllExcept(fields);
		FilterProvider provider = new SimpleFilterProvider().addFilter("BaseFilter", filter);
		return mapper.writer(provider).writeValueAsString(instance);
	}

	public static void serializeObject(Object instance, Writer writer) {
		JsonGenerator jg = null;
		try {
			jg = jsonFactory.createGenerator(writer);
			mapper.writeValue(jg, instance);
			jg.flush();// flush in mapper.writeValue
		} catch (IOException e) {
		} finally {
			JSONUtils.close(jg);
		}

	}

	/**
	 * parse string of JSONArray to List with special clazz
	 *
	 * @param jsonInput
	 * @param clazz The class must have no-args-constructor and all needed setter.
	 * @return
	 * @throws java.io.IOException
	 */
	public static <T> List<T> parseJsonToObjectList(String jsonInput, Class<T> clazz) throws IOException {

		try {
			return JSONUtils.readValue(jsonInput,
				TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, clazz));
		} catch (IOException e) {
			log.log(Level.WARNING, "jsonInput: " + jsonInput);
			log.log(Level.WARNING, e.getMessage(), e);
			throw e;
		}
	}

	public static <T> List<T> parseJsonToMapList(String jsonInput) throws IOException {

		try {
			return JSONUtils.readValue(jsonInput,
				TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, Map.class));
		} catch (IOException e) {
			log.log(Level.WARNING, "jsonInput: " + jsonInput);
			log.log(Level.WARNING, e.getMessage(), e);
			throw e;
		}
	}

	private static <T> T readValue(String content, JavaType valueType) throws IOException,
            JsonParseException, JsonMappingException {
		return mapper.readValue(content, valueType);
	}

	public static <T> T jsonToObject(String jsonStr, Class<T> requiredType) {
		return jsonToObject(jsonStr.getBytes(), requiredType);
	}

	public static <T> T jsonToObject(byte[] jsonBytes, Class<T> requiredType) {
		JsonParser jp = null;
		try {
			jp = jsonFactory.createParser(jsonBytes);
			return mapper.readValue(jp, requiredType);
		} catch (IOException e) {
		} finally {
			JSONUtils.close(jp);
		}
		return null;
	}
	public static <K, V> Map<K, V> jsonToMap(String jsonStr, Class<K> keyType, Class<V> valueType) {
		try{
			return jsonToMap(jsonStr.getBytes(), keyType, valueType);
		} catch (IOException e) {
			//Tracing the bug of error exception in production
			//[05/26 15:30:43:452][JSONUtils.java:239][ERROR] Unexpected character 
			//('P' (code 80)): expected a valid value (number, String, array, object, 'true', 
			//'false' or 'null') at [Source: [B@58c75305; line: 1, column: 2]

		}
		return null;
	}
	private static <K, V> Map<K, V> jsonToMap(byte[] jsonBytes, Class<K> keyType, Class<V> valueType) throws IOException {
		JsonParser jp = null;
		try {
			jp = jsonFactory.createParser(jsonBytes);
			return mapper.readValue(jp, mapper.getTypeFactory().constructMapType(Map.class, keyType, valueType));
//		} catch (IOException e) {
//			LogUtils.cfbook.error(e.getMessage(), e);
		} finally {
			JSONUtils.close(jp);
		}
		//return null;
	}

	public static <T> T jsonToObject(String json, TypeReference<T> typeReference) {

		try {
			return mapper.readValue(json, typeReference);
		} catch (IOException e) {
		}
		return null;
	}

	public static void close(JsonGenerator jGenerator) {
		if (null != jGenerator) {
			try {
				jGenerator.close();
			} catch (IOException e) {
			}
		}
	}

	public static void close(JsonParser jp) {
		if (jp != null) {
			try {
				jp.close();
			} catch (IOException e) {
			}
		}
	}

	public static void close(StringWriter sw) {
		if (sw != null) {
			try {
				sw.close();
			} catch (IOException e) {
			}
		}
	}
}
