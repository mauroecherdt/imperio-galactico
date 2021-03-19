package ar.com.alianzarebelde.imperiogalactico.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ar.com.alianzarebelde.imperiogalactico.exceptions.InvalidRequestException;
import ar.com.alianzarebelde.imperiogalactico.exceptions.SatelliteNotFoundException;
import ar.com.alianzarebelde.imperiogalactico.exceptions.resolver.ErrorResponse;
import ar.com.alianzarebelde.imperiogalactico.models.PayloadMessage;
import ar.com.alianzarebelde.imperiogalactico.models.SpaceCraft;
import ar.com.alianzarebelde.imperiogalactico.services.RecovererServices;
import ar.com.alianzarebelde.messagerecovery.exceptions.InvalidDistanceException;
import ar.com.alianzarebelde.messagerecovery.exceptions.InvalidMessageException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(tags = "Top Secret")
public class RecovererController {

	@Autowired
	private RecovererServices recovererServices;
	
	@PostMapping("/topsecret/")
	@ResponseStatus(value = HttpStatus.OK)
	@ApiOperation(value = "Obtiene información de la nave que emite el mensaje.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Se devuelve información de la nave.", response = SpaceCraft.class),
			@ApiResponse(code = 400, message = "Request inválido.", response = ErrorResponse.class),
			@ApiResponse(code = 404, message = "No se pude determinar.", response = ErrorResponse.class),
			@ApiResponse(code = 500, message = "Error interno.", response = ErrorResponse.class)})
	public SpaceCraft decodeInformation(@RequestBody PayloadMessage request) throws InvalidRequestException, SatelliteNotFoundException, InvalidMessageException, InvalidDistanceException {
		validate(request);
		return recovererServices.decodeSpaceCraftInformation(request.getSatellites());
	}
	
	private void validate(PayloadMessage reqMessage) throws InvalidRequestException {
		if(reqMessage.getSatellites() == null || reqMessage.getSatellites().size() != 3) {
			throw new InvalidRequestException("La cantidad de satelites debe ser 3.");
		}
	}
	
}
