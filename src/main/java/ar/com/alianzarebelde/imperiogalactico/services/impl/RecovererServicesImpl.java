package ar.com.alianzarebelde.imperiogalactico.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.alianzarebelde.imperiogalactico.enums.SatelliteType;
import ar.com.alianzarebelde.imperiogalactico.exceptions.InvalidRequestException;
import ar.com.alianzarebelde.imperiogalactico.exceptions.SatelliteAlreadyExistsException;
import ar.com.alianzarebelde.imperiogalactico.exceptions.SatelliteNotFoundException;
import ar.com.alianzarebelde.imperiogalactico.exceptions.UnprocessableException;
import ar.com.alianzarebelde.imperiogalactico.models.Satellite;
import ar.com.alianzarebelde.imperiogalactico.models.SpaceCraft;
import ar.com.alianzarebelde.imperiogalactico.repository.RecovererRepository;
import ar.com.alianzarebelde.imperiogalactico.services.RecovererServices;
import ar.com.alianzarebelde.messagerecovery.exceptions.InvalidDistanceException;
import ar.com.alianzarebelde.messagerecovery.exceptions.InvalidMessageException;
import ar.com.alianzarebelde.messagerecovery.models.Coordinate;
import ar.com.alianzarebelde.messagerecovery.resolvers.LocationResolver;
import ar.com.alianzarebelde.messagerecovery.resolvers.MessageResolver;


@Service
public class RecovererServicesImpl implements RecovererServices{

	@Autowired
	private RecovererRepository recovererRepository;
	
	@Override
	public SpaceCraft decodeSpaceCraftInformation(List<Satellite> satellites) throws InvalidRequestException, SatelliteNotFoundException {
		Satellite kenobi = getSatelliteByType(satellites, SatelliteType.KENOBI);
		Satellite skywalker = getSatelliteByType(satellites, SatelliteType.SKYWALKER);
		Satellite sato = getSatelliteByType(satellites, SatelliteType.SATO);
		return decodeInformation(kenobi, skywalker, sato);
	}

	@Override
	public Satellite saveSatellite(SatelliteType name, Satellite satellite) throws SatelliteAlreadyExistsException {

		Optional<Satellite> optionalSatellite = recovererRepository.findByName(name);
		Satellite satelliteSave = optionalSatellite.orElse(null);
		if(satelliteSave != null) {
			throw new SatelliteAlreadyExistsException("El satelite " + name + " ya existe en la base.");
		}
		
		satellite.setName(name);
		return recovererRepository.save(satellite);
	}

	@Override
	public SpaceCraft getSpaceCraftInformation() throws UnprocessableException, InvalidRequestException {
		Satellite kenobi = recovererRepository.findByName(SatelliteType.KENOBI).orElseThrow(() -> new UnprocessableException("Satelite " + SatelliteType.KENOBI + " no existe en la base."));
		Satellite skywalker = recovererRepository.findByName(SatelliteType.SKYWALKER).orElseThrow(() -> new UnprocessableException("Satelite " + SatelliteType.SKYWALKER + " no existe en la base."));
		Satellite sato = recovererRepository.findByName(SatelliteType.SATO).orElseThrow(() -> new UnprocessableException("Satelite " + SatelliteType.SATO + " no existe en la base."));
		return decodeInformation(kenobi, skywalker, sato);
	}

	@Override
	public Satellite updateSatellite(SatelliteType name, Satellite satellite) throws SatelliteNotFoundException {
		Satellite satelliteDb = recovererRepository.findByName(name).orElseThrow(() -> new SatelliteNotFoundException("Satelite " + name.getName() + " inexistente."));
		satelliteDb.setDistance(satellite.getDistance());
		satelliteDb.setMessage(satellite.getMessage());
		return recovererRepository.save(satelliteDb);
	}

	@Override
	public void deleteSatellite(SatelliteType name) throws SatelliteNotFoundException {
		Satellite satelliteDb = recovererRepository.findByName(name).orElseThrow(() -> new SatelliteNotFoundException("Satelite " + name.getName() + " inexistente."));
		recovererRepository.delete(satelliteDb);
	}
	
	
	private SpaceCraft decodeInformation(Satellite kenobi, Satellite skywalker, Satellite sato) throws InvalidRequestException {

		SpaceCraft spaceCraft = null;
		try {
			Coordinate location = LocationResolver.getLocation(kenobi.getDistance(), skywalker.getDistance(), sato.getDistance());
			String message = MessageResolver.getMessage(kenobi.getMessage(), skywalker.getMessage(), sato.getMessage());
			spaceCraft = new SpaceCraft(location.getX(), location.getY(), message);
			
		} catch (InvalidDistanceException | InvalidMessageException e) {
			e.printStackTrace();
			throw new InvalidRequestException(e.getMessage());
		} 
		
		return spaceCraft;
	}
	
	
	private Satellite getSatelliteByType(List<Satellite> satellites, SatelliteType satelliteType) throws SatelliteNotFoundException {
		return satellites.stream()
				.filter(sat -> sat.getName().equals(satelliteType))
				.findFirst()
				.orElseThrow(() -> new SatelliteNotFoundException("Satelite " + satelliteType + " no encontrado."));
	} 
	
}
