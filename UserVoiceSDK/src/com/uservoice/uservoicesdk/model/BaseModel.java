package com.uservoice.uservoicesdk.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class BaseModel {
	
	private static final String TAG = "com.uservoice.uservoicesdk.model.BaseModel";
	
	protected int modelId;
	
	public void load(JSONObject object) throws JSONException {
		modelId = object.getInt("id");
	}
	
	public int getModelId() {
		return modelId;
	}
	
	protected String stringOrNull(JSONObject object, String key) throws JSONException {
		return object.isNull(key) ? null : object.getString(key);
	}

	public static String apiPath(String path, Object... args) {
		return "/api/v1" + String.format(path, args);
	}
	
	public static <T extends BaseModel> List<T> deserializeList(JSONObject object, String rootKey, Class<T> modelClass) {
		try {
			Method method = modelClass.getMethod("load", JSONObject.class);
			JSONArray array = object.getJSONArray(rootKey);
			List<T> list = new ArrayList<T>(array.length());
			for (int i = 0; i < array.length(); i++) {
				T model = modelClass.newInstance();
				method.invoke(model, array.getJSONObject(i));
				list.add(model);
			}
			return list;
		} catch (JSONException e) {
			Log.e(TAG, "JSON deserialization failure for " + modelClass + " " + e.getMessage() + " JSON: " + object.toString());
			return null;
		} catch (IllegalArgumentException e) {
			Log.e(TAG, "Reflection failed trying to call load on " + modelClass + " " + e.getMessage());
			return null;
		} catch (IllegalAccessException e) {
			Log.e(TAG, "Reflection failed trying to call load on " + modelClass + " " + e.getMessage());
			return null;
		} catch (InvocationTargetException e) {
			Log.e(TAG, "Reflection failed trying to call load on " + modelClass + " " + e.getMessage());
			return null;
		} catch (NoSuchMethodException e) {
			Log.e(TAG, "Reflection failed trying to call load on " + modelClass + " " + e.getMessage());
			return null;
		} catch (InstantiationException e) {
			Log.e(TAG, "Reflection failed trying to instantiate " + modelClass + " " + e.getMessage());
			return null;
		}
	}
	
	public static <T extends BaseModel> T deserializeObject(JSONObject object, String rootKey, Class<T> modelClass) {
		try {
			Method method = modelClass.getMethod("load", JSONObject.class);
			JSONObject singleObject = object.getJSONObject(rootKey);
			T model = modelClass.newInstance();
			method.invoke(model, singleObject);
			return modelClass.cast(model);
		} catch (JSONException e) {
			Log.e(TAG, "JSON deserialization failure for "  + modelClass + " " + e.getMessage() + " JSON: " + object.toString());
			return null;
		} catch (IllegalArgumentException e) {
			Log.e(TAG, "Reflection failed trying to call load on " + modelClass + " " + e.getMessage());
			return null;
		} catch (IllegalAccessException e) {
			Log.e(TAG, "Reflection failed trying to call load on " + modelClass + " " + e.getMessage());
			return null;
		} catch (InvocationTargetException e) {
			Log.e(TAG, "Reflection failed trying to call load on " + modelClass + " " + e.getMessage());
			return null;
		} catch (NoSuchMethodException e) {
			Log.e(TAG, "Reflection failed trying to call load on " + modelClass + " " + e.getMessage());
			return null;
		} catch (InstantiationException e) {
			Log.e(TAG, "Reflection failed trying to instantiate " + modelClass + " " + e.getMessage());
			return null;
		}
	}

}
