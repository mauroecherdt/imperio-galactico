package ar.com.alianzarebelde.imperiogalactico.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import ar.com.alianzarebelde.imperiogalactico.exceptions.InvalidSatelliteException;


public enum SatelliteType {

	KENOBI("kenobi"),
	
	SKYWALKER("skywalker"),
	
	SATO("sato");
	
	
	private String name;
	

	private SatelliteType(String name) {
		this.name = name;
	}
	
	
    @JsonCreator
    public static SatelliteType fromString(String key) throws InvalidSatelliteException {
        for(SatelliteType disp : SatelliteType.values()) {
            if(disp.name().equalsIgnoreCase(key)) {
                return disp;
            }
        }
        throw new InvalidSatelliteException("Nombre de satelite inválido (Kenobi/Skywalker/Sato).");
    }
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
