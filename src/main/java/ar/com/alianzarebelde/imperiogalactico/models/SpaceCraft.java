package ar.com.alianzarebelde.imperiogalactico.models;

public class SpaceCraft {

	private Position position;
	
	private String message;

	
	public SpaceCraft(float coordX, float coordY, String message) {
		super();
		this.position = new Position(coordX, coordY);
		this.message = message;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
