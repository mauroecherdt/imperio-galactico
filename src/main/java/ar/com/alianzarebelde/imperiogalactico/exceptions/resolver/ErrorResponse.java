package ar.com.alianzarebelde.imperiogalactico.exceptions.resolver;

import java.util.Date;

import org.slf4j.MDC;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private Date timestamp;

	private String errorCode;

	private String message;

	private String path;

	public ErrorResponse(String code, String message, String path) {
		super();
		this.timestamp = new Date();
		this.errorCode = code;
		this.message = message;
		this.path = path;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public void setMessage(String mensaje) {
		this.message = mensaje;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
