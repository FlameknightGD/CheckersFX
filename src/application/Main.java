package application;

import java.nio.file.Paths;

import application.utils.Config;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
	
	BorderPane pain = new BorderPane();
	GridPane obunga = new GridPane();
	
	VBox menu_buttons = new VBox();
	VBox back_button = new VBox();	
	VBox vol_slider = new VBox();
	
	Media bgm = new Media(Paths.get("assets/audio/its_raining_somewhere_else.wav").toUri().toString());
	MediaPlayer mediaPlayer = new MediaPlayer(bgm);
	double bgm_volume;

	
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
        mediaPlayer.setAutoPlay(true);
        //bgm_volume =  Double.parseDouble(config.get("volume"));
        mediaPlayer.setVolume(bgm_volume);
        mediaPlayer.setCycleCount(2147483647);
        
        obunga.setPadding(new Insets(28, 448, 28, 448));
        obunga.setId("board");
        
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

		switch (menu_id) {
			case 1:
				main_menu();
				break;
			case 2:
				menu_singleplayer();
				break;
			case 3:
				menu_multiplayer();
				break;
			case 4:
				menu_settings();
				vol_slider.setPrefSize(width / 3, height / 1.5);
				break;
		}
		
		if(menu_id >= 1 && menu_id <= 3)
		{
			if(pain.getChildren().size() == 1)
			{
				pain.getChildren().remove(vol_slider);
			}
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
		Button button_htp = new Button("How To Play");
		Button button_close = new Button("Close Game");
		
		button_sp.setId("menu_button");
		button_sp.setMaxWidth(width / 3);
		button_sp.setAlignment(Pos.CENTER);
		
		button_mp.setId("menu_button");
		button_mp.setMaxWidth(width / 3);
		button_mp.setAlignment(Pos.CENTER);
		
		button_htp.setId("menu_button");
		button_htp.setMaxWidth(width / 3);
		button_htp.setAlignment(Pos.CENTER);
		
		button_settings.setId("menu_button");
		button_settings.setMaxWidth(width / 3);
		button_settings.setAlignment(Pos.CENTER);
		
		button_close.setId("menu_button");
		button_close.setMaxWidth(width / 3);
		button_close.setAlignment(Pos.CENTER);
		
		menu_buttons.getChildren().addAll(button_sp, button_mp, button_htp, button_settings, button_close);
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
            	//Viel Spaß beim Programmieren des Mehrspielermodus, Niclas
            	
            	menu_multiplayer();
            }
        });

		button_htp.setOnAction(new EventHandler<ActionEvent>() 
		{
            @Override
            public void handle(ActionEvent actionEvent) 
            {
            	menu_htp();
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
		
		button_close.setOnAction(new EventHandler<ActionEvent>() 
		{
            @Override
            public void handle(ActionEvent actionEvent) 
            {
            	primaryStage.close();
            }
        });
	}
	
	public void menu_singleplayer()
	{
		//Menu ID change
		this.menu_id = 2;
		
		//Remove Buttons from previous Menu
		if(menu_buttons.getChildren().size() != 0)
		{
			menu_buttons.getChildren().remove(0, menu_buttons.getChildren().size());
		}
		
		//Buttons
		Button button_white = new Button("White");
		Button button_black = new Button("Black");
		Button button_back = new Button("Back");
		
		button_white.setId("menu_button");
		button_white.setMaxWidth(width / 3);
		button_white.setAlignment(Pos.CENTER);
		
		button_black.setId("menu_button");
		button_black.setMaxWidth(width / 3);
		button_black.setAlignment(Pos.CENTER);
		
		button_back.setId("menu_button");
		button_back.setMaxWidth(width / 3);
		button_back.setAlignment(Pos.CENTER);
		
		menu_buttons.getChildren().addAll(button_white, button_black);
		menu_buttons.setAlignment(Pos.CENTER);
		
		button_white.setOnAction(new EventHandler<ActionEvent>() 
		{
            @Override
            public void handle(ActionEvent actionEvent) 
            {
            	Scene checkersBoardWhite = new Scene(obunga);
            	primaryStage.setScene(checkersBoardWhite);
            	
            	//Spaces
            	Button space00 = new Button("");
            	Button space01 = new Button("");
            	Button space02 = new Button("");
            	Button space03 = new Button("");
            	Button space04 = new Button("");
            	Button space05 = new Button("");
            	Button space06 = new Button("");
            	Button space07 = new Button("");
            	
            	Button space10 = new Button("");
            	Button space11 = new Button("");
            	Button space12 = new Button("");
            	Button space13 = new Button("");
            	Button space14 = new Button("");
            	Button space15 = new Button("");
            	Button space16 = new Button("");
            	Button space17 = new Button("");
            	
            	Button space20 = new Button("");
            	Button space21 = new Button("");
            	Button space22 = new Button("");
            	Button space23 = new Button("");
            	Button space24 = new Button("");
            	Button space25 = new Button("");
            	Button space26 = new Button("");
            	Button space27 = new Button("");
            	
            	Button space30 = new Button("");
            	Button space31 = new Button("");
            	Button space32 = new Button("");
            	Button space33 = new Button("");
            	Button space34 = new Button("");
            	Button space35 = new Button("");
            	Button space36 = new Button("");
            	Button space37 = new Button("");
            	
            	Button space40 = new Button("");
            	Button space41 = new Button("");
            	Button space42 = new Button("");
            	Button space43 = new Button("");
            	Button space44 = new Button("");
            	Button space45 = new Button("");
            	Button space46 = new Button("");
            	Button space47 = new Button("");
            	
            	Button space50 = new Button("");
            	Button space51 = new Button("");
            	Button space52 = new Button("");
            	Button space53 = new Button("");
            	Button space54 = new Button("");
            	Button space55 = new Button("");
            	Button space56 = new Button("");
            	Button space57 = new Button("");
            	
            	Button space60 = new Button("");
            	Button space61 = new Button("");
            	Button space62 = new Button("");
            	Button space63 = new Button("");
            	Button space64 = new Button("");
            	Button space65 = new Button("");
            	Button space66 = new Button("");
            	Button space67 = new Button("");
            	
            	Button space70 = new Button("");
            	Button space71 = new Button("");
            	Button space72 = new Button("");
            	Button space73 = new Button("");
            	Button space74 = new Button("");
            	Button space75 = new Button("");
            	Button space76 = new Button("");
            	Button space77 = new Button("");
            	
            	space00.setId("boardSpace");
            	space00.setPrefSize(128, 128);
            	
            	space01.setId("space00");
            	space01.setPrefSize(128, 128);
            	
            	space02.setId("space00");
            	space02.setPrefSize(128, 128);
            }
        });
	}
	
	public void menu_htp()
	{
		//Menu ID change
		this.menu_id = 5;

		//Remove Buttons from previous Menu
		if(menu_buttons.getChildren().size() != 0)
		{
			menu_buttons.getChildren().remove(0, menu_buttons.getChildren().size());
		}
		
		if(vol_slider.getChildren().size() != 0)
		{
			vol_slider.getChildren().remove(0, menu_buttons.getChildren().size());
		}
		
		Text htp = new Text();
		
		htp.setFont(Font.loadFont("file:assets/fonts/", 120));
	}
	
	public void menu_multiplayer()
	{
		this.menu_id = 3;
	}
	
	public void menu_settings()
	{
		this.menu_id = 4;
		
		//Remove Buttons from previous Menu
		if(menu_buttons.getChildren().size() != 0)
		{
			menu_buttons.getChildren().remove(0, menu_buttons.getChildren().size());
		}
		
		Slider music_volume = new Slider(0, 100, 100);
		
		if(vol_slider.getChildren().size() == 0)
		{
			vol_slider.getChildren().add(music_volume);
		}
		
		vol_slider.setPrefSize(width / 3, height / 1.5);
		vol_slider.setAlignment(Pos.CENTER);
		vol_slider.setId("vbox_visible_border");
		pain.setCenter(vol_slider);
		
		if(pain.getChildren().contains(vol_slider) == false)
		{
			pain.getChildren().add(vol_slider);
		}
		
		music_volume.valueProperty().addListener(new ChangeListener<Number>() 
		{
            public void changed(ObservableValue <? extends Number> ov, Number oldValue, Number newValue) 
            {
            	double newVolume = newValue.doubleValue() * 0.0025;
            	mediaPlayer.setVolume(newVolume);
            	bgm_volume = newVolume;
            	
            	config.put("volume", String.valueOf(newVolume));
            	config.write();
            }
        });
	}
	
	public void back()
	{
		if(menu_id == 1)
		{
			primaryStage.close();
		}
		
		if(menu_id >= 2 && menu_id <= 5)
		{
			menu_id = 1;
			updateScreen();
		}
		
	}
}