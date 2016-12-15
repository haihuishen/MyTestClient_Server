package com.shen.client.service;

/**
 * ServiceRulesException extends Exception
 */
public class ServiceRulesException extends Exception {

	/**
	 * 抛异常显示的"异常信息"
	 */
	private static final long serialVersionUID = -4177425965405885196L;

    /**
     * ServiceRulesException extends Exception <p>
     * @param message 抛异常显示的"异常信息"
     */
	public ServiceRulesException(String message) {
		super(message);
	}
	
	

}
