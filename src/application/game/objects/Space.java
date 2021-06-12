package application.game.objects;

import javafx.scene.control.Button;

public class Space extends Button
{
	public int[] coordinates;
	public boolean containsPiece;
	public String color;
	
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
	
	public String getColor()
	{
		return this.color;
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
	
	public void setColor(String color) {
		this.color = color;
	}
}