package servlet;

import java.io.Serializable;

/**
 * 返回结果的 JSON
 * 
 * implements Serializable  序列化：也可以不写
 * @author shen
 *
 */
public class ResultJSONBean implements Serializable {

	private static final long serialVersionUID = 2740440249422567441L;
	
	private String result;
	private String errorMsg;
	
	public ResultJSONBean(){}
	
	/**
	 * 返回结果的 JSON
	 * @param result			返回的结果
	 * @param errorMsg			错误时返回的"错误信息"
	 */
	public ResultJSONBean(String result, String errorMsg) {
		this.result = result;
		this.errorMsg = errorMsg;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
	

}
