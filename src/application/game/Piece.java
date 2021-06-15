package application.game;

import application.exceptions.InvalidParamException;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Piece extends Circle {
	public int[] coordinates;
	public int childNumber;
	public boolean onSpace;

	public Piece(double radius, Paint fill, int[] coordinates) throws InvalidParamException {
		super(radius, fill);

		setCoordinates(coordinates);
	}

	// Getters
	public int[] getCoordinates() {
		return this.coordinates;
	}

	public boolean getOnSpace() {
		return this.onSpace;
	}

	// Setters
	public void setCoordinates(int[] coordinates) throws InvalidParamException {
		if (coordinates[0] > -1 && coordinates[0] < 8 && coordinates[1] > -1 && coordinates[1] < 8) {
			this.coordinates = coordinates;
		} else {
			throw new InvalidParamException("error: invalid parameter exception: the coordinates " + coordinates[0]
					+ ", " + coordinates[1] + " are out of bounds");
		}
	}
	
	public void setOnSpace(boolean onSpace) {
		this.onSpace = onSpace;
	}
}