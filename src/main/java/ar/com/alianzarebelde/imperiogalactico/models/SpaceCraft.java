package ar.com.alianzarebelde.imperiogalactico.models;

import ar.com.alianzarebelde.messagerecovery.models.Coordinate;

public class SpaceCraft {

	private Coordinate position;
	
	private String message;

	
	public SpaceCraft(Coordinate position, String message) {
		super();
		this.position = position;
		this.message = message;
	}

	public Coordinate getPosition() {
		return position;
	}

	public void setPosition(Coordinate position) {
		this.position = position;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
