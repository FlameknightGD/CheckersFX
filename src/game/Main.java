package game;

import java.nio.file.Paths;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class Main extends Application
{
	Stage primaryStage;
	Pane root;
	
	double width;
	double height;
	
	AudioClip its_raining_somewhere_else;
	
	public static void main(String[] args) 
	{
        launch(args);
    }
	
	@Override
	public void start(Stage primaryStage) 
	{
		//Width & Height Parameters
		width = 1280;
		height = 720;
		
		//Game Icon & Title
		primaryStage.setTitle("sansbox");
		primaryStage.getIcons().add(new Image("file:textures/sans.png"));
		
		//Width Configuration
		primaryStage.setWidth(1280);
		primaryStage.setMinWidth(1280);
		primaryStage.setMaxWidth(1920);
		
		//Height Configuration
		primaryStage.setHeight(720);
		primaryStage.setMinHeight(720);
		primaryStage.setMaxHeight(1080);
		
		//Dynamic Window Size
		primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> 
		{
            width = (double) newVal;
            updateWidth((double) newVal, (double) oldVal);
            updateScreen();
        });
		
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> 
        {
            height = (double) newVal;
            updateHeight((double) newVal, (double) oldVal);
            updateScreen();
        });
        
        //Background Music
        AudioClip its_raining_somewhere_else = new AudioClip(Paths.get("audio/its_raining_somewhere_else.wav").toUri().toString());
        
        its_raining_somewhere_else.setVolume(0.025);
        its_raining_somewhere_else.setCycleCount(2147483647);
        its_raining_somewhere_else.play();
		
        //Pane Creation
		root = new Pane();
        root.setId("root");
        root.getStylesheets().add(getClass().getResource("game.css").toExternalForm());
        
        //Scene Creation
        Scene scene = new Scene(root, 1280, 720);
        
        //Main Menu Creation
        main_menu();
        
        //Primary Stage Show
        primaryStage.setScene(scene);
        
        this.primaryStage = primaryStage;
        primaryStage.show();
    }
	
	//Dynamic Window Methods
	public void updateWidth(double newValue, double oldValue)
	{
		double multiplier = newValue/oldValue;
        root.setPrefWidth(oldValue * multiplier);;
	}
	
	public void updateHeight(double newValue, double oldValue)
	{
		double multiplier = newValue / oldValue;
        root.setPrefHeight(oldValue * multiplier);
	}
	
	//Screen Update
	public void updateScreen()
	{
		root.getChildren().remove(0);
		main_menu();
	}
	
	//Main Menu
	public void main_menu()
	{
		//Pane Creation
		BorderPane pain = new BorderPane();
		
		VBox sans = new VBox();
		
		root.getChildren().add(pain);
		pain.setCenter(sans);
		BorderPane.setAlignment(sans, Pos.CENTER);
		
		sans.setPrefSize(width, height);
		
		//Buttons
		Button button_sp = new Button("Singleplayer");
		Button button_mp = new Button("Multiplayer");
		Button button_settings = new Button("Settings");
		
		button_sp.setId("menu_button");
		button_sp.setMaxWidth(width / 4);
		button_sp.setAlignment(Pos.CENTER);
		
		button_mp.setId("menu_button");
		button_mp.setMaxWidth(width / 4);
		button_mp.setAlignment(Pos.CENTER);
		
		button_settings.setId("menu_button");
		button_settings.setMaxWidth(width / 4);
		button_settings.setAlignment(Pos.CENTER);
		
		sans.getChildren().addAll(button_sp, button_mp, button_settings);
		sans.setAlignment(Pos.CENTER);
		
		//Button Functions
		button_sp.setOnAction(new EventHandler<ActionEvent>() 
		{
            @Override
            public void handle(ActionEvent actionEvent) 
            {
            	menu_singleplayer();
            }
        });
		
		button_mp.setOnAction(new EventHandler<ActionEvent>() 
		{		
            @Override
            public void handle(ActionEvent actionEvent) 
            {
            	//Viel Spaß beim Programmieren des Mehrspielermodus', Niclas;
            	
            	menu_multiplayer();
            }
        });
		
		button_settings.setOnAction(new EventHandler<ActionEvent>() 
		{
            @Override
            public void handle(ActionEvent actionEvent) 
            {
            	menu_settings();
            }
        });
	}
	
	public void menu_singleplayer()
	{
		
	}
	
	public void menu_multiplayer()
	{

	}
	
	public void menu_settings()
	{
		
	}
}