package com.vistor.common.util;

import java.util.HashMap;
import org.json.JSONObject;

/**
 * a data class for ȫ�ִ�������
 */
public class ErrorType {

	public static Integer NETWORK_ERROR = 1000;
	public static Integer NETWORK_REQUEST_ERROR = 1001;

	/** all error types */
	static HashMap<Integer, ErrorType> allErrorTypes = new HashMap<Integer, ErrorType>();
	static {
		allErrorTypes.put(-1, new ErrorType(-1, "����������Ժ�����"));
		allErrorTypes.put(0,new ErrorType(0, "�ɹ�"));
		allErrorTypes.put(1,new ErrorType(1, "δ֪����"));
		allErrorTypes.put(49, new ErrorType(49, "��������"));
		allErrorTypes.put(100, new ErrorType(100, "δ��¼"));
		allErrorTypes.put(101, new ErrorType(101, "û��Ȩ��"));
		allErrorTypes.put(102, new ErrorType(102, "�û�������"));
		allErrorTypes.put(1000, new ErrorType(1000, "�����쳣��������������"));
		allErrorTypes.put(1001, new ErrorType(1001, "�����쳣�����Ժ�����"));
	}
	

	private Integer errorCode;
	private String errorBody;
	private String ext;

	public ErrorType(int errorCode, String errorBody, String ext) {
		this.setErrorCode(errorCode);
		this.setErrorBody(errorBody);
		this.setExt(ext);
	}

	public ErrorType(int errorCode, String errorBody) {
		this.setErrorCode(errorCode);
		this.setErrorBody(errorBody);
	}

	public ErrorType(int errorCode) {
		this.setErrorCode(errorCode);
		this.setErrorBody(getErrorBody(errorCode));
	}

	/**
	 * ��װ������Ϣ
	 */
	public static ErrorType constructErrorType(JSONObject json) {
		int errorCode = -1;
		return getErrorType(errorCode);
	}

	public static ErrorType constructErrorBody(JSONObject json) {
		if (json.has("message")) {
			int errorCode = getJsonIntegerValue(json, "error_code");
			String errorBody = getJsonStringValue(json, "message");
			return new ErrorType(errorCode, errorBody); // �Զ������
		} else {
			return new ErrorType(-1);
		}
	}

	public static ErrorType getErrorType(int errorCode) {
		ErrorType error = allErrorTypes.get(errorCode);
		if (error == null) {
			error = allErrorTypes.get(-1);
		}
		return error;
	}

	public static String getErrorBody(int error) {
		ErrorType type = allErrorTypes.get(error);
		if (type != null) {
			return type.getErrorBody();
		}
		return "";
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorBody() {
		return errorBody;
	}

	public void setErrorBody(String errorBody) {
		this.errorBody = errorBody;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}
	
	public static int getJsonIntegerValue(JSONObject json, String key) {
		return getJsonIntegerValue(json, key, 0);
	}
	
	public static int getJsonIntegerValue(JSONObject jsonObject, String key, int defaultValue) {
		try {
			if (jsonObject != null && jsonObject.has(key)) {
				return jsonObject.getInt(key);
			}
		} catch (Exception e) {
			return defaultValue;
		}
		return defaultValue;
	}
	

	public static String getJsonStringValue(JSONObject jsonObject, String key) {
		return getJsonStringValue(jsonObject, key, "");
	}
	
	public static String getJsonStringValue(JSONObject jsonObject, String key, String defaultValue) {
		try {
			if (jsonObject != null && jsonObject.has(key)) {
				String value = jsonObject.getString(key).trim();
				if (value.equals("null")) {
					value = "";
				}
				return value;
			}
		} catch (Exception e) {
			return defaultValue;
		}
		return defaultValue;
	}

}

