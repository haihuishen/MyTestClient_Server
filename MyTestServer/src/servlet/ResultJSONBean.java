package servlet;

import java.io.Serializable;

/**
 * ���ؽ���� JSON
 * 
 * implements Serializable  ���л���Ҳ���Բ�д
 * @author shen
 *
 */
public class ResultJSONBean implements Serializable {

	private static final long serialVersionUID = 2740440249422567441L;
	
	private String result;
	private String errorMsg;
	
	public ResultJSONBean(){}
	
	/**
	 * ���ؽ���� JSON
	 * @param result			���صĽ��
	 * @param errorMsg			����ʱ���ص�"������Ϣ"
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
