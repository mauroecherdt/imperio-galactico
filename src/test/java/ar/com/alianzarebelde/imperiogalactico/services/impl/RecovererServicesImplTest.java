package ar.com.alianzarebelde.imperiogalactico.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

@SpringBootTest
class RecovererServicesImplTest {
	
	@Autowired
	private RecovererServices recovererServices;
	
	@MockBean
	private RecovererRepository recovererRepository;

	@Test
	final void testDecodeSpaceCraftInformation() throws InvalidRequestException, SatelliteNotFoundException, InvalidMessageException, InvalidDistanceException {

		Satellite s1 = new Satellite();
		s1.setName(SatelliteType.KENOBI);
		s1.setDistance(600f);
		s1.setMessage(new String[]{"este", "", "", "mensaje", ""});
		
		Satellite s2 = new Satellite();
		s2.setName(SatelliteType.SKYWALKER);
		s2.setDistance(100f);
		s2.setMessage(new String[]{"", "es", "", "", "secreto"});
		
		Satellite s3 = new Satellite();
		s3.setName(SatelliteType.SATO);
		s3.setDistance(500f);
		s3.setMessage(new String[]{"este", "", "un", "", ""});
		
		SpaceCraft decodeSpaceCraftInformation = recovererServices.decodeSpaceCraftInformation(Arrays.asList(s1,s2,s3));
		assertEquals("este es un mensaje secreto", decodeSpaceCraftInformation.getMessage());
		
	}

	@Test
	final void testSaveSatellite() throws SatelliteAlreadyExistsException {
		
		Satellite s1 = new Satellite();
		s1.setName(SatelliteType.KENOBI);
		s1.setDistance(400f);
		s1.setMessage(new String[]{"este", "", "", "mensaje", ""});
		
		Mockito.when(recovererRepository.findByName(ArgumentMatchers.any(SatelliteType.class))).thenReturn(Optional.empty());
		Mockito.when(recovererRepository.save(ArgumentMatchers.any(Satellite.class))).thenReturn(s1);

		Satellite saveSatellite = recovererServices.saveSatellite(SatelliteType.KENOBI, s1);
		assertEquals(s1.getDistance(), saveSatellite.getDistance());
	}
	
	
	@Test
	final void testSaveSatellite_throwSatelliteAlreadyExistsException() throws SatelliteAlreadyExistsException {
		
		Satellite s1 = new Satellite();
		s1.setName(SatelliteType.KENOBI);
		s1.setDistance(400f);
		s1.setMessage(new String[]{"este", "", "", "mensaje", ""});
		
		Mockito.when(recovererRepository.findByName(ArgumentMatchers.any(SatelliteType.class))).thenReturn(Optional.of(s1));

		assertThrows(SatelliteAlreadyExistsException.class, () -> {
			recovererServices.saveSatellite(SatelliteType.KENOBI, s1);
			  });
	}

	@Test
	final void testGetSpaceCraftInformation() throws UnprocessableException, InvalidRequestException, InvalidMessageException, InvalidDistanceException {
		Satellite s1 = new Satellite();
		s1.setName(SatelliteType.KENOBI);
		s1.setDistance(600f);
		s1.setMessage(new String[]{"este", "", "", "mensaje", ""});
		
		Satellite s2 = new Satellite();
		s2.setName(SatelliteType.SKYWALKER);
		s2.setDistance(100f);
		s2.setMessage(new String[]{"", "es", "", "", "secreto"});
		
		Satellite s3 = new Satellite();
		s3.setName(SatelliteType.SATO);
		s3.setDistance(500f);
		s3.setMessage(new String[]{"este", "", "un", "", ""});
		
		Mockito.when(recovererRepository.findByName(SatelliteType.KENOBI)).thenReturn(Optional.of(s1));
		Mockito.when(recovererRepository.findByName(SatelliteType.SKYWALKER)).thenReturn(Optional.of(s2));
		Mockito.when(recovererRepository.findByName(SatelliteType.SATO)).thenReturn(Optional.of(s3));

		SpaceCraft spaceCraftInformation = recovererServices.getSpaceCraftInformation();
		assertEquals("este es un mensaje secreto", spaceCraftInformation.getMessage());
	}

	@Test
	final void testUpdateSatellite() throws SatelliteNotFoundException {
		
		Satellite s1 = new Satellite();
		s1.setName(SatelliteType.KENOBI);
		s1.setDistance(200f);
		s1.setMessage(new String[]{"este", "", "un", "", ""});
		
		Satellite satelliteSaved = new Satellite();
		satelliteSaved.setName(SatelliteType.KENOBI);
		satelliteSaved.setDistance(400f);
		satelliteSaved.setMessage(new String[]{"este", "", "", "mensaje", ""});
		
		Mockito.when(recovererRepository.findByName(ArgumentMatchers.any(SatelliteType.class))).thenReturn(Optional.of(satelliteSaved));
		Mockito.when(recovererRepository.save(ArgumentMatchers.any(Satellite.class))).thenReturn(s1);

		Satellite updateSatellite = recovererServices.updateSatellite(SatelliteType.KENOBI, s1);
		assertEquals(s1.getMessage(), updateSatellite.getMessage());
	}

	@Test
	final void testDeleteSatellite() {
		Satellite s1 = new Satellite();
		s1.setName(SatelliteType.KENOBI);
		s1.setDistance(400f);
		s1.setMessage(new String[]{"este", "", "", "mensaje", ""});
		
		Mockito.when(recovererRepository.findByName(ArgumentMatchers.any(SatelliteType.class))).thenReturn(Optional.of(s1));
    	Mockito.doNothing().when(recovererRepository).delete(ArgumentMatchers.any(Satellite.class));
	}

}
