package game;

import java.nio.file.Paths;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
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
	
	BorderPane pain = new BorderPane();
	VBox menu_buttons = new VBox();
	VBox back_button = new VBox();
	
	AudioClip its_raining_somewhere_else;
	
	int menu_id;
	
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
		
		//Listenerst
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
        
        root.getChildren().add(pain);
		pain.setCenter(menu_buttons);
		BorderPane.setAlignment(menu_buttons, Pos.CENTER);
		
		menu_buttons.setPrefSize(width, height);
        
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
		double multiplier = newValue / oldValue;
        root.setPrefWidth(oldValue * multiplier);
        //pain.setPrefWidth(oldValue * multiplier);
        menu_buttons.setPrefWidth(oldValue * multiplier);
	}
	
	public void updateHeight(double newValue, double oldValue)
	{
		double multiplier = newValue / oldValue;
        root.setPrefHeight(oldValue * multiplier);
        //pain.setPrefHeight(oldValue * multiplier);
        menu_buttons.setPrefHeight(oldValue * multiplier);
	}
	
	//Screen Update
	public void updateScreen()
	{
		menu_buttons.getChildren().remove(0, menu_buttons.getChildren().size());
		
		if(menu_id == 1)
		{
			main_menu();
		}
		else if(menu_id == 2)
		{
			menu_singleplayer();
		}
		else if(menu_id == 3)
		{
			menu_multiplayer();
		}
		else if(menu_id == 4)
		{
			menu_settings();
		}
	}
	
	//Main Menu
	public void main_menu()
	{
		//Menu ID change
		menu_id = 1;
		
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
		
		menu_buttons.getChildren().addAll(button_sp, button_mp, button_settings);
		menu_buttons.setAlignment(Pos.CENTER);
		
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
		//Menu ID change
		this.menu_id = 2;
		
		if(menu_buttons.getChildren().size() != 0)
		{
			menu_buttons.getChildren().remove(0, menu_buttons.getChildren().size());
		}
		
		//Buttons
		Button button_white = new Button("White");
		Button button_black = new Button("Black");
		Button button_back = new Button("Back");
		
		button_white.setId("menu_button");
		button_white.setMaxWidth(width / 4);
		button_white.setAlignment(Pos.CENTER);
		
		button_black.setId("menu_button");
		button_black.setMaxWidth(width / 4);
		button_black.setAlignment(Pos.CENTER);
		
		button_back.setId("menu_button");
		button_back.setMaxWidth(width / 4);
		button_back.setAlignment(Pos.CENTER);
		
		menu_buttons.getChildren().addAll(button_white, button_black);
		menu_buttons.setAlignment(Pos.CENTER);
		
		back_button.getChildren().addAll(button_back);
		back_button.setAlignment(Pos.TOP_LEFT);
	}
	
	public void menu_multiplayer()
	{
		this.menu_id = 3;
	}
	
	public void menu_settings()
	{
		this.menu_id = 4;
	}
	
	public void back()
	{
		if(menu_id >= 2 && menu_id <= 4)
		{
			main_menu();
		}

	}
}