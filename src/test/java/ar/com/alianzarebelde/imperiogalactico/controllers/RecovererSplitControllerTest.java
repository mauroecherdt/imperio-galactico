package ar.com.alianzarebelde.imperiogalactico.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ar.com.alianzarebelde.imperiogalactico.enums.SatelliteType;
import ar.com.alianzarebelde.imperiogalactico.exceptions.InvalidRequestException;
import ar.com.alianzarebelde.imperiogalactico.exceptions.SatelliteAlreadyExistsException;
import ar.com.alianzarebelde.imperiogalactico.exceptions.UnprocessableException;
import ar.com.alianzarebelde.imperiogalactico.models.Satellite;
import ar.com.alianzarebelde.imperiogalactico.models.SpaceCraft;
import ar.com.alianzarebelde.imperiogalactico.services.RecovererServices;
import ar.com.alianzarebelde.messagerecovery.models.Coordinate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RecovererSplitControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private RecovererServices recovererServices; 
	
	
	@Test
	final void testAddSatelliteInformation() throws Exception {
		
		String request = "{\"distance\":142.7,\"message\":[\"este\",\"\",\"un\",\"\",\"\"]}";
		
		Satellite s1 = new Satellite();
		s1.setName(SatelliteType.KENOBI);
		s1.setDistance(400f);
		s1.setMessage(new String[]{"este", "", "", "mensaje", ""});
		
		Mockito.when(recovererServices.saveSatellite(ArgumentMatchers.any(SatelliteType.class),ArgumentMatchers.any(Satellite.class))).thenReturn(s1);
		
		mvc.perform(post("/topsecret_split/KENOBI")
				.contentType(MediaType.APPLICATION_JSON).content(request)).andExpect(status().isCreated());
	}

	@Test
	final void testGetDecodeSatellites() throws Exception {
		SpaceCraft spaceCraft = new SpaceCraft(new Coordinate(100f, 200f), "este es un mensaje secreto");
		
		Mockito.when(recovererServices.getSpaceCraftInformation()).thenReturn(spaceCraft);
		
		mvc.perform(get("/topsecret_split/")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

}
