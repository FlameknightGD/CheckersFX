package application.game;

import javafx.scene.control.Button;

public class Space extends Button
{
	public int[] coordinates;
	public boolean containsPiece;
	
	public Space(String text, int[] coordinates) 
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
	
	//Setters
	public void setCoordinates(int[] coordinates) 
	{
		this.coordinates = coordinates;
	}
	
	public void setContainsPiece(boolean containsPiece) 
	{
		this.containsPiece = containsPiece;
	}
}