package ar.com.alianzarebelde.imperiogalactico.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ar.com.alianzarebelde.imperiogalactico.enums.SatelliteType;

@Document(collection = "satellites")
public class Satellite {

	@Id
	@JsonIgnore
	private String id;
	
	private SatelliteType name;
	
	private float distance;
	
	private String[] message;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SatelliteType getName() {
		return name;
	}

	public void setName(SatelliteType name) {
		this.name = name;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public String[] getMessage() {
		return message;
	}

	public void setMessage(String[] message) {
		this.message = message;
	}

	
}
