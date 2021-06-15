package application.game;

import application.exceptions.InvalidParamException;
import javafx.scene.control.Button;

public class Space extends Button
{
	public boolean containsPiece;
	public int[] coordinates;
	public String pieceColor;
	
	public Space(String text, int[] coordinates) throws InvalidParamException 
	{
		super(text);
		
		setCoordinates(coordinates);
	}
	
	//Getters
	public int[] getCoordinates()
	{
		return this.coordinates;
	}
	
	public boolean getContainsPiece()
	{
		return this.containsPiece;
	}
	
	public String getPieceColor()
	{
		return this.pieceColor;
	}
	
	//Setters
	public void setContainsPiece(boolean containsPiece) 
	{
		this.containsPiece = containsPiece;
	}
	
	public void setCoordinates(int[] coordinates) throws InvalidParamException 
	{
		if (coordinates[0] > -1 && coordinates[0] < 8 && coordinates[1] > -1 && coordinates[1] < 8) {
			this.coordinates = coordinates;
		} else {
			throw new InvalidParamException("error: invalid parameter exception: the coordinates " + coordinates[0]
					+ ", " + coordinates[1] + " are out of bounds");
		}
	}
	
	public void setPieceColor(String pieceColor) throws InvalidParamException {
		if (pieceColor == "white" || pieceColor == "black") {
			this.pieceColor = pieceColor;
		} else {
			throw new InvalidParamException("error: invalid parameter exception: piece color '" + pieceColor + "' doesn't exist");
		}
	}
}