package Util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;


public class method {
	/**
	 * 從inputStream中讀取參數資料
	 *
	 * @param is
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String getRequestDataFromStream(InputStream is, String encoding) throws IOException {
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(is);
			String result = IOUtils.toString(bis, encoding);
			return result;
		} finally {
			if (bis != null)
				bis.close();
		}
		
		
	}
	
	
	/**
	 * 將取的InputStream 將josn 轉為 map
	 * @throws JSONException 
	 * 
	 * @return
	 */
	public static Map<String,String> getJsonToMap(String  jsonString) throws JSONException {
		Map<String, String> rtnMap = new HashMap<String, String>();
	       int k = jsonString.indexOf("{");
	       jsonString = jsonString.substring(k);
	       JSONObject jsons = new JSONObject(jsonString.trim());
	       String[] keys = jsons.getNames(jsons);

	       for (int i = 0; i < keys.length; i++) {
	           rtnMap.put(keys[i], "" + jsons.get(keys[i]));
	       }
	       return rtnMap;
	}
	
	/**
	 * 從inputStream中讀取參數資料
	 *
	 * @param inputStream
	 * @param encoding
	 * @param 您要從map的 key
	 * @return 您要從map中的 value
	 * @throws IOException
	 */
	
	public static String getString(InputStream is, String encoding,String str){
		String re;
		Map<String,String>  map = null;
		try {
			re = getRequestDataFromStream(is,encoding);
			map = getJsonToMap(re);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map.get("str");
	}
}
