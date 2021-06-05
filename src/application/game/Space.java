package application.game;

import javafx.scene.control.Button;

public class Space extends Button
{
	public int[] coordinates;
	
	public Space(String text, int[] coordinates) 
	{
		super(text);
		
		setCoordinates(coordinates);
	}
	
	//Getters
	public int[] getCoordinates()
	{
		return coordinates;
	}
	
	//Setters
	public void setCoordinates(int[] coordinates) 
	{
		this.coordinates = coordinates;
	}
}