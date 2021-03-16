package ar.com.alianzarebelde.imperiogalactico.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ar.com.alianzarebelde.imperiogalactico.enums.SatelliteType;
import ar.com.alianzarebelde.imperiogalactico.exceptions.InvalidRequestException;
import ar.com.alianzarebelde.imperiogalactico.exceptions.InvalidSatelliteException;
import ar.com.alianzarebelde.imperiogalactico.exceptions.SatelliteAlreadyExistsException;
import ar.com.alianzarebelde.imperiogalactico.exceptions.SatelliteNotFoundException;
import ar.com.alianzarebelde.imperiogalactico.exceptions.UnprocessableException;
import ar.com.alianzarebelde.imperiogalactico.exceptions.resolver.ErrorResponse;
import ar.com.alianzarebelde.imperiogalactico.models.Satellite;
import ar.com.alianzarebelde.imperiogalactico.models.SpaceCraft;
import ar.com.alianzarebelde.imperiogalactico.services.RecovererServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(tags = "Top Secret Split")
public class RecovererSplitController {

	@Autowired
	private RecovererServices recovererServices;
	
	
	@PostMapping("/topsecret_split/{satelliteName}")
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiOperation(value = "Guarda información del satelite.")
	@ApiResponses({
			@ApiResponse(code = 201, message = "Satelite guardado.", response = SpaceCraft.class),
			@ApiResponse(code = 400, message = "Request inválido.", response = ErrorResponse.class),
			@ApiResponse(code = 409, message = "Satelite ya existente.", response = ErrorResponse.class),
			@ApiResponse(code = 500, message = "Error interno.", response = ErrorResponse.class)})
	public Satellite addSatelliteInformation(@PathVariable String satelliteName, @RequestBody Satellite satellite) throws InvalidSatelliteException, InvalidRequestException, SatelliteAlreadyExistsException {
		validate(satellite);
		return recovererServices.saveSatellite(SatelliteType.fromString(satelliteName), satellite);
	}

	
	@GetMapping("/topsecret_split/")
	@ResponseStatus(value = HttpStatus.OK)
	@ApiOperation(value = "Obtiene información de la nave que emite el mensaje.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Se devuelve información de la nave.", response = SpaceCraft.class),
			@ApiResponse(code = 400, message = "Request inválido.", response = ErrorResponse.class),
			@ApiResponse(code = 422, message = "No procesable.", response = ErrorResponse.class),
			@ApiResponse(code = 500, message = "Error interno.", response = ErrorResponse.class)})
	public SpaceCraft getDecodeSatellites() throws InvalidSatelliteException, InvalidRequestException, UnprocessableException {
		return recovererServices.getSpaceCraftInformation();
	}
	
	
	@PutMapping("/topsecret_split/{satelliteName}")
	@ResponseStatus(value = HttpStatus.CREATED)
	@ApiOperation(value = "Modifica información del satelite.")
	@ApiResponses({
			@ApiResponse(code = 201, message = "Satelite modificado.", response = Satellite.class),
			@ApiResponse(code = 400, message = "Request inválido.", response = ErrorResponse.class),
			@ApiResponse(code = 404, message = "Satelite inexistente.", response = ErrorResponse.class),
			@ApiResponse(code = 500, message = "Error interno.", response = ErrorResponse.class)})
	public Satellite updateSatellite(@PathVariable String satelliteName, @RequestBody Satellite satellite) throws SatelliteNotFoundException, InvalidSatelliteException, InvalidRequestException {
		validate(satellite);
		return recovererServices.updateSatellite(SatelliteType.fromString(satelliteName), satellite);
	}
	
	@DeleteMapping("/topsecret_split/{satelliteName}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Elimina satelite.")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Satelite eliminado."),
			@ApiResponse(code = 400, message = "Request inválido.", response = ErrorResponse.class),
			@ApiResponse(code = 404, message = "Satelite inexistente.", response = ErrorResponse.class),
			@ApiResponse(code = 500, message = "Error interno.", response = ErrorResponse.class)})
	public void updateSatellite(@PathVariable String satelliteName) throws SatelliteNotFoundException, InvalidSatelliteException {
		recovererServices.deleteSatellite(SatelliteType.fromString(satelliteName));
	}
	
	
	private void validate(Satellite satellite) throws InvalidRequestException {
		if(satellite.getDistance() <= 0) {
			throw new InvalidRequestException("La distancia del satelite debe ser mayor a cero.");
		}
		
		if(satellite.getMessage() == null || satellite.getMessage().length == 0) {
			throw new InvalidRequestException("La cantidad de palabras del mensaje debe ser mayor a cero.");
		}
	}
	
}
