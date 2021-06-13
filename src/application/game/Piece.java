package application.game;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Piece extends Circle
{
	public int[] coordinates;
	public int childNumber;
	
	public Piece(double radius, Paint fill, int[] coordinates) 
	{
		super(radius, fill);
		
		setCoordinates(coordinates);
	}
	
	//Getters
	public int[] getCoordinates()
	{
		return this.coordinates;
	}
	
	//Setters
	public void setChildNumber(int childNumber) 
	{
		this.childNumber = childNumber;
	}
	
	public void setCoordinates(int[] coordinates) 
	{
		this.coordinates = coordinates;
	}
}