package ar.com.alianzarebelde.imperiogalactico.exceptions;

public class InvalidRequestException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -595726022481683078L;

	public InvalidRequestException(String errorMessage) {
		super(errorMessage);
	}
	
}
