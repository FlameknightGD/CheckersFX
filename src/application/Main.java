package application;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import application.utils.Config;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application
{	
	Stage primaryStage;
	Pane root;
	
	double width;
	double height;
	
	Config config = new Config("config\\config.txt");
	
	BorderPane menuPane = new BorderPane();
	GridPane checkerBoard = new GridPane();
	
	VBox menuButtons = new VBox();
	VBox volSlider = new VBox();
	
	Media backgroundMusic = new Media(Paths.get("assets/audio/its_raining_somewhere_else.wav").toUri().toString());
	MediaPlayer backgroundMusicPlayer = new MediaPlayer(backgroundMusic);
	double backgroundMusicVolume;

	
	int menuId;
	
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
		primaryStage.setTitle("CheckersFX");
		primaryStage.getIcons().add(new Image("file:assets/textures/icon.png"));
		
		//Width Configuration
		primaryStage.setWidth(1280);
		primaryStage.setMinWidth(1280);
		primaryStage.setMaxWidth(1920);
		
		//Height Configuration
		primaryStage.setHeight(720);
		primaryStage.setMinHeight(720);
		primaryStage.setMaxHeight(1080);
		
		//Listeners
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
        
        System.out.println(config.get("volume"));
        
        //Background Music
        backgroundMusicPlayer.setAutoPlay(true);
        //backgroundMusicVolume =  Double.parseDouble(config.get("volume"));
        backgroundMusicPlayer.setVolume(backgroundMusicVolume);
        backgroundMusicPlayer.setCycleCount(2147483647);
        
        checkerBoard.setPadding(new Insets(28, 448, 28, 448));
        checkerBoard.setId("board");
        
        //Pane Creation
		root = new Pane();
        root.setId("root");
        root.getStylesheets().add(getClass().getResource("game.css").toExternalForm());
        
        root.getChildren().add(menuPane);
		menuPane.setCenter(menuButtons);
		BorderPane.setAlignment(menuButtons, Pos.CENTER);
		
		menuButtons.setPrefSize(width, height);
        
        //Scene Creation
        Scene scene = new Scene(root, 1280, 720);
        
        //ACHTUNG DIESER LISTENER WURDE UNS VON ADRIAN GEKLAUT!!!
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() 
        {
            @Override
            public void handle(KeyEvent t) 
            {
                KeyCode key = t.getCode();
                if (key == KeyCode.ESCAPE) 
                {
                	back();
                }
            }
        });
        
        //Main Menu Creation
        mainMenu();
        
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
        menuButtons.setPrefWidth(oldValue * multiplier);
	}
	
	public void updateHeight(double newValue, double oldValue)
	{
		double multiplier = newValue / oldValue;
        root.setPrefHeight(oldValue * multiplier);
        menuButtons.setPrefHeight(oldValue * multiplier);
	}
	
	//Screen Update
	public void updateScreen()
	{
		menuButtons.getChildren().remove(0, menuButtons.getChildren().size());

		switch (menuId) {
			case 1:
				mainMenu();
				break;
			case 2:
				menuSingleplayer();
				break;
			case 3:
				menuMultiplayer();
				break;
			case 4:
				menuSettings();
				volSlider.setPrefSize(width / 3, height / 1.5);
				break;
		}
		
		if(menuId >= 1 && menuId <= 3)
		{
			if(menuPane.getChildren().size() == 1)
			{
				menuPane.getChildren().remove(volSlider);
			}
		}
	}
	
	//Main Menu
	public void mainMenu()
	{
		//Menu ID change
		menuId = 1;
		
		//Buttons
		Button buttonSingleplayer = new Button("Singleplayer");
		Button buttonMultiplayer = new Button("Multiplayer");
		Button buttonHowToPlay = new Button("How To Play");
		Button buttonSettings = new Button("Settings");
		Button buttonClose = new Button("Close Game");
		
		buttonSingleplayer.setId("menu_button");
		buttonSingleplayer.setMaxWidth(width / 3);
		buttonSingleplayer.setAlignment(Pos.CENTER);
		
		buttonMultiplayer.setId("menu_button");
		buttonMultiplayer.setMaxWidth(width / 3);
		buttonMultiplayer.setAlignment(Pos.CENTER);
		
		buttonHowToPlay.setId("menu_button");
		buttonHowToPlay.setMaxWidth(width / 3);
		buttonHowToPlay.setAlignment(Pos.CENTER);
		
		buttonSettings.setId("menu_button");
		buttonSettings.setMaxWidth(width / 3);
		buttonSettings.setAlignment(Pos.CENTER);
		
		buttonClose.setId("menu_button");
		buttonClose.setMaxWidth(width / 3);
		buttonClose.setAlignment(Pos.CENTER);
		
		menuButtons.getChildren().addAll(buttonSingleplayer, buttonMultiplayer, buttonHowToPlay, buttonSettings, buttonClose);
		menuButtons.setAlignment(Pos.CENTER);
		
		//Main Menu Button Functions
		buttonSingleplayer.setOnAction(e -> 
		{
			menuSingleplayer();
		});
		
		buttonMultiplayer.setOnAction(e -> 
		{
			menuMultiplayer();
		});

		buttonHowToPlay.setOnAction(e -> 
		{
			menuHowToPlay();
		});
		
		buttonSettings.setOnAction(e -> 
		{
			menuSettings();
		});
		
		buttonClose.setOnAction(e -> 
		{
			back();
		});
	}
	
	public void menuSingleplayer()
	{
		//Menu ID change
		this.menuId = 2;
		
		//Remove Buttons from previous Menu
		if(menuButtons.getChildren().size() != 0)
		{
			menuButtons.getChildren().remove(0, menuButtons.getChildren().size());
		}
		
		//Buttons
		Button buttonWhite = new Button("Play As White");
		Button buttonBlack = new Button("Play As Black");
		
		buttonWhite.setId("menu_button");
		buttonWhite.setMaxWidth(width / 3);
		buttonWhite.setAlignment(Pos.CENTER);
		
		buttonBlack.setId("menu_button");
		buttonBlack.setMaxWidth(width / 3);
		buttonBlack.setAlignment(Pos.CENTER);
		
		menuButtons.getChildren().addAll(buttonWhite, buttonBlack);
		menuButtons.setAlignment(Pos.CENTER);
		
		buttonWhite.setOnAction(new EventHandler<ActionEvent>() 
		{
            @Override
            public void handle(ActionEvent actionEvent) 
            {
            	checkerBoard.setStyle("-fx-background-color: #000000");
            	Scene checkersBoardWhite = new Scene(checkerBoard);
            	
            	primaryStage.setScene(checkersBoardWhite);

            	Button[][] boardSpaces = new Button[8][8];
            	
            	for(int i = 0; i < 8; i++)
            	{
            		for(int j = 0; j < 8; j++)
            		{
            			Button space = new Button("");
            			space.setId("boardSpace");
            			space.setPrefSize(128, 128);
            			
            			if(i % 2 == 0 && j % 2 == 0 || i % 2 == 1 && j % 2 == 1)
            			{
            				space.setStyle("-fx-background-color: #d3a74d");
            			}
            			else
            			{
            				space.setStyle("-fx-background-color: #312206");
            			}
            			
            			boardSpaces[i][j] = space;
            			checkerBoard.add(space, i, j);
            		}
            	}
            }});
	}
	
	public void menuHowToPlay()
	{
		Alert alertHowToPlay = new Alert(AlertType.INFORMATION);
		
		alertHowToPlay.setTitle("How To Play");
		alertHowToPlay.setHeaderText("How To Play");
		alertHowToPlay.setContentText(
				"In Checkers you will face of your opponent on the board and going to try to defeat them. "
				+ "A move consists of moving a piece diagonally to an adjacent unoccupied square. If the "
				+ "adjacent square contains an opponent's piece, and the square immediately beyond it is "
				+ "vacant, the piece may be captured (and removed from the game) by jumping over it.");
		
		alertHowToPlay.show();
	}
	
	public void menuMultiplayer()
	{
		this.menuId = 3;
	}
	
	public void menuSettings()
	{
		this.menuId = 4;
		
		//Remove Buttons from previous Menu
		if(menuButtons.getChildren().size() != 0)
		{
			menuButtons.getChildren().remove(0, menuButtons.getChildren().size());
		}
		
		Slider music_volume = new Slider(0, 100, 100);
		
		if(volSlider.getChildren().size() == 0)
		{
			volSlider.getChildren().add(music_volume);
		}
		
		volSlider.setPrefSize(width / 3, height / 1.5);
		volSlider.setAlignment(Pos.CENTER);
		volSlider.setId("vbox_visible_border");
		menuPane.setCenter(volSlider);
		
		if(menuPane.getChildren().contains(volSlider) == false)
		{
			menuPane.getChildren().add(volSlider);
		}
		
		music_volume.valueProperty().addListener(new ChangeListener<Number>() 
		{
            public void changed(ObservableValue <? extends Number> ov, Number oldValue, Number newValue) 
            {
            	double newVolume = newValue.doubleValue() * 0.0025;
            	backgroundMusicPlayer.setVolume(newVolume);
            	backgroundMusicVolume = newVolume;
            	
            	config.put("volume", String.valueOf(newVolume));
            	config.write();
            }
        });
	}
	
	public void back()
	{
		switch(menuId) 
		{
			//Close Game
			case 1:
				primaryStage.close();
				break;
				
			//Go Back To Main Menu
			case 2:
				menuId = 1;
				updateScreen();
				break;
			case 3:
				menuId = 1;
				updateScreen();
				break;
			case 4:
				menuId = 1;
				updateScreen();
				break;
			case 5:
				menuId = 1;
				updateScreen();
				break;
		}
	}
}