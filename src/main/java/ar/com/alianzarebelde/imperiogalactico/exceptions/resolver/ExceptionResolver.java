package ar.com.alianzarebelde.imperiogalactico.exceptions.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ar.com.alianzarebelde.imperiogalactico.exceptions.InvalidRequestException;
import ar.com.alianzarebelde.imperiogalactico.exceptions.InvalidSatelliteException;
import ar.com.alianzarebelde.imperiogalactico.exceptions.SatelliteAlreadyExistsException;
import ar.com.alianzarebelde.imperiogalactico.exceptions.SatelliteNotFoundException;
import ar.com.alianzarebelde.imperiogalactico.exceptions.UnprocessableException;


@ControllerAdvice
@RestController
public class ExceptionResolver extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

	@ExceptionHandler(InvalidRequestException.class)
	public final ResponseEntity<ErrorResponse> handleInvalidRequestException(InvalidRequestException ex,
			WebRequest request) {
		logger.error(ex.getMessage());
		
		ErrorResponse exceptionResponse = new ErrorResponse("INVALID_REQUEST",
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidSatelliteException.class)
	public final ResponseEntity<ErrorResponse> handleInvalidSatelliteException(InvalidSatelliteException ex,
			WebRequest request) {
		logger.error(ex.getMessage());
		
		ErrorResponse exceptionResponse = new ErrorResponse("INVALID_REQUEST",
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorResponse>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SatelliteNotFoundException.class)
	public final ResponseEntity<ErrorResponse> handleInvalidSatelliteException(SatelliteNotFoundException ex,
			WebRequest request) {
		logger.error(ex.getMessage());
		
		ErrorResponse exceptionResponse = new ErrorResponse("NOT_FOUND",
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UnprocessableException.class)
	public final ResponseEntity<ErrorResponse> handleUnprocessableException(UnprocessableException ex,
			WebRequest request) {
		logger.error(ex.getMessage());
		
		ErrorResponse exceptionResponse = new ErrorResponse("INVALID",
				"No hay información suficiente para procesar la petición.", request.getDescription(false));
		return new ResponseEntity<ErrorResponse>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(SatelliteAlreadyExistsException.class)
	public final ResponseEntity<ErrorResponse> handleSatelliteAlreadyExistsException(SatelliteAlreadyExistsException ex,
			WebRequest request) {
		logger.error(ex.getMessage());
		
		ErrorResponse exceptionResponse = new ErrorResponse("CONFLICT", ex.getMessage()
				, request.getDescription(false));
		return new ResponseEntity<ErrorResponse>(exceptionResponse, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponse> handleInternalServerError(Exception ex, WebRequest request) {
		ex.printStackTrace();
		logger.error("Ha ocurrido un error inesperado.");

		ErrorResponse exceptionResponse = new ErrorResponse( "INTERNAL_ERROR",
				"Error interno.", request.getDescription(false));
		return new ResponseEntity<ErrorResponse>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
