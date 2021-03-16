package ar.com.alianzarebelde.imperiogalactico.services;

import java.util.List;

import ar.com.alianzarebelde.imperiogalactico.enums.SatelliteType;
import ar.com.alianzarebelde.imperiogalactico.exceptions.InvalidRequestException;
import ar.com.alianzarebelde.imperiogalactico.exceptions.SatelliteAlreadyExistsException;
import ar.com.alianzarebelde.imperiogalactico.exceptions.SatelliteNotFoundException;
import ar.com.alianzarebelde.imperiogalactico.exceptions.UnprocessableException;
import ar.com.alianzarebelde.imperiogalactico.models.Satellite;
import ar.com.alianzarebelde.imperiogalactico.models.SpaceCraft;

public interface RecovererServices {

	/**
	 * 
	 * 
	 * @param satellites
	 * @return
	 * @throws InvalidRequestException
	 */
	public SpaceCraft decodeSpaceCraftInformation(List<Satellite> satellites) throws InvalidRequestException;
	
	/**
	 * 
	 * 
	 * @param satellite
	 * @return
	 */
	public Satellite saveSatellite(SatelliteType name, Satellite satellite) throws SatelliteAlreadyExistsException;
	
	/**
	 * 
	 * 
	 * @param name
	 * @param satellite
	 * @return
	 */
	public Satellite updateSatellite(SatelliteType name, Satellite satellite) throws SatelliteNotFoundException;
	
	/**
	 * 
	 * 
	 * @param name
	 * @param satellite
	 * @return
	 */
	public void deleteSatellite(SatelliteType name) throws SatelliteNotFoundException;
	
	/**
	 * 
	 * 
	 * @return
	 */
	public SpaceCraft getSpaceCraftInformation() throws UnprocessableException, InvalidRequestException;
	
	
}
