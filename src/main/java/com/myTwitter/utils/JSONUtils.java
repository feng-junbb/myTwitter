package com.myTwitter.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONUtils {
	private static Logger logger = LoggerFactory.getLogger(JSONUtils.class);
	
	public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
		Map<String, Object> retMap = new HashMap<String, Object>();

		if (json != JSONObject.NULL) {
			retMap = toMap(json);
		}
		return retMap;
	}
	
	public static Map<String, String> jsonToStringMap(JSONObject json) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> strMap = new HashMap<String, String> ();
		
		if (json != JSONObject.NULL) {
			map = toMap(json);
		}
		
	    for (String key : map.keySet()) {
	    	// remove space, [, ] from the value
	    	String value = map.get(key).toString().trim().replace("[", "").replace("]", "");
	    	strMap.put(key, value);
	    }
		return strMap;
	}
	
	public static Map<String, List<String>> jsonToStringListMap(JSONObject json) throws JSONException {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		
		@SuppressWarnings("unchecked")
		Iterator<String> keysItr = json.keys();
		
		while (keysItr.hasNext()) {
			String key = keysItr.next();
			JSONArray jArray = json.getJSONArray(key);
			List<String> list = jsonToStringList(jArray);
			map.put(key, list);
		}
		
		return map;
	}
	
	static Map<String, Object> toMap(JSONObject object) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();

		@SuppressWarnings("unchecked")
		Iterator<String> keysItr = object.keys();
		
		while (keysItr.hasNext()) {
			String key = keysItr.next();
			Object value = object.get(key);

			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}
			else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			} else if(JSONObject.NULL.equals(value)) {
				value="";
			} else if(value.getClass().getName().equals("org.json.JSONObject$Null")) {
				value="";
			}
			map.put(key, value);
		}
		return map;
	}
	
	public static ArrayList<String> jsonToStringList(JSONArray array) throws JSONException {
		ArrayList<String> retList = new ArrayList<String> ();
		List<Object> list = toList(array);
		for (Object o : list) {
			retList.add(o.toString());
		}
		return retList;
	}

	public static List<Object> toList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.length(); i++) {
			Object value = array.get(i);
			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			list.add(value);
		}
		return list;
	}
	
	public static List<String> toStringList(JSONArray array) throws JSONException {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < array.length(); i++) {
			String value = (String)array.get(i);
			list.add(value);
		}
		return list;
	}

	public static String decodeUTF8String(String str) {
		try {
			String newStr =  URLDecoder.decode(str, "UTF-8");
			return newStr;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	public static JSONObject convertStringToJSON(String jsonString, String skipBeginning) {
		JSONObject jObject = null;
		try {
			jObject = new JSONObject(jsonString);
			return jObject;
		} catch (Exception e1) {	
			String newString = decodeUTF8String(jsonString);
			
			if (newString == null) {
				return null;
			} else {
				try {
					if (!StringUtils.isEmpty(skipBeginning)) {
						newString = newString.substring(skipBeginning.length());
					} else {
						if (newString.contains("=")) {
							newString = newString.substring(newString.indexOf("=")+1);
						}
					}
					jObject = new JSONObject(newString);
					return jObject;
				} catch (Exception e2) {
					return null;
				}
				
			}
		}
	}

	public static JSONObject convertStringToJSON(String jsonString) throws JSONException {
		JSONObject jObject = null;
		try {
			jObject = new JSONObject(jsonString);
		} catch (Exception e) {	
//			e.printStackTrace();
			String newString = decodeUTF8String(jsonString);
			int pos1 = newString.indexOf("{");
			int pos2 = newString.lastIndexOf("}");
			newString = newString.substring(pos1, pos2+1);
			if (newString == null) {
				return null;
			} else {
				if (newString.contains("=")) {
					newString = newString.substring(newString.indexOf("=")+1);
				}
				logger.info("newString:"+newString);
				jObject = new JSONObject(newString);
			}
		}

		return jObject;		
	}
	
	public static JSONArray convertStringToJSONArray(String jsonString) throws JSONException{
		JSONArray jArray = null;
		
		try {
			jArray = new JSONArray(jsonString);
		} catch (Exception e) {	
			String newString = decodeUTF8String(jsonString);
			int pos1 = newString.indexOf("[");
			int pos2 = newString.lastIndexOf("]");
			newString = newString.substring(pos1, pos2+1);
			if (newString == null) {
				return null;
			} else {
				if (newString.contains("=")) {
					newString = newString.substring(newString.indexOf("=")+1);
				}
				logger.info("newString:"+newString);
				jArray = new JSONArray(newString);
			}
		}

		return jArray;		
	}
}
