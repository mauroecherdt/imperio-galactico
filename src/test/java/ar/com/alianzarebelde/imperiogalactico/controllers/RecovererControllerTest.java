package ar.com.alianzarebelde.imperiogalactico.controllers;

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

import ar.com.alianzarebelde.imperiogalactico.models.SpaceCraft;
import ar.com.alianzarebelde.imperiogalactico.services.RecovererServices;
import ar.com.alianzarebelde.messagerecovery.models.Coordinate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RecovererControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private RecovererServices recovererServices; 
	
	@Test
	final void testDecodeInformation() throws Exception {

		String requestBody = "{\"satellites\":[{\"name\":\"kenobi\",\"distance\":100.0,\"message\":[\"este\",\"\",\"\",\"mensaje\",\"\"]},{\"name\":\"skywalker\",\"distance\":115.5,\"message\":[\"\",\"es\",\"\",\"\",\"secreto\"]},{\"name\":\"sato\",\"distance\":142.7,\"message\":[\"este\",\"\",\"un\",\"\",\"\"]}]}";
		SpaceCraft spaceCraft = new SpaceCraft(new Coordinate(100f, 200f), "este es un mensaje secreto");
		Mockito.when(recovererServices.decodeSpaceCraftInformation(ArgumentMatchers.anyList())).thenReturn(spaceCraft);
		
		mvc.perform(post("/topsecret/")
				.contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(status().isOk());
		
	}

}
