package ar.com.alianzarebelde.imperiogalactico.models;

import java.util.List;

public class PayloadMessage {

	private List<Satellite> satellites;

	public List<Satellite> getSatellites() {
		return satellites;
	}

	public void setSatellites(List<Satellite> satellites) {
		this.satellites = satellites;
	}
	
}
