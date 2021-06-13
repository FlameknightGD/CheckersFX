package application;

import java.nio.file.Paths;

import application.game.Piece;
import application.game.Space;
import application.utils.Amogus;
import application.utils.Config;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {
	// Useless Comment

	// Initialize Primary Stage
	Stage primaryStage;

	// Initialize Panes
	Pane root;
	BorderPane menuPane = new BorderPane();
	public static GridPane checkerBoard = new GridPane();

	// Initialize VBoxes
	VBox menuButtons = new VBox();
	VBox vBoxVolumeSlider = new VBox();

	// Initialize Config File
	Config configFile = new Config("config\\config.txt");

	// Background Music
	Media backgroundMusic = new Media(Paths.get("assets/audio/its_raining_somewhere_else.wav").toUri().toString());
	MediaPlayer musicPlayer = new MediaPlayer(backgroundMusic);

	double backgroundMusicVolume = 0.0;
	// TODO Fix The ConfigParser Get Method Ffs

	// Global Variables
	double width;
	double height;

	int menuId;

	int[] selectedSpace = new int[2];

	boolean redSpace = false;
	boolean greenSpace = false;

	String playerColor;

	public static Space[][] boardSpaces = new Space[8][8];
	Piece[][] boardPieces = new Piece[8][8];

	boolean selectedSpaceLocked = false;

	int sus;
	int amogus;
	
	Amogus sussy = new Amogus();

	// Main Method
	public static void main(String[] args) {
		launch(args);
	}

	// Start Method
	@Override
	public void start(Stage primaryStage) {
		// Set Initial Witdh And Height
		width = 1280;
		height = 720;

		// Set Game Title And Icon
		primaryStage.setTitle("CheckersFX");
		primaryStage.getIcons().add(new Image("file:assets/textures/icon.png"));

		// Configure Width
		primaryStage.setWidth(1280);
		primaryStage.setMinWidth(1280);
		primaryStage.setMaxWidth(1920);

		// Configure Height
		primaryStage.setHeight(720);
		primaryStage.setMinHeight(720);
		primaryStage.setMaxHeight(1080);

		// Add Listeners To Change Width And Height
		primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
			width = (double) newVal;
			updateWidth((double) newVal, (double) oldVal);
			updateScreen();
		});

		primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
			height = (double) newVal;
			updateHeight((double) newVal, (double) oldVal);
			updateScreen();
		});

		// Set Size Of VBox
		menuButtons.setPrefSize(width, height);

		// Configure Music Player
		musicPlayer.setAutoPlay(true);
		musicPlayer.setCycleCount(2147483647);
		musicPlayer.setVolume(backgroundMusicVolume);

		// Configure CheckerBoard GridPane
		checkerBoard.setPadding(new Insets(28, 448, 28, 448));
		checkerBoard.setId("board");

		// Initialize Root
		root = new Pane();
		root.setId("root");

		// Add CSS And Children To Root
		root.getStylesheets().add(getClass().getResource("css/menu.css").toExternalForm());
		root.getChildren().add(menuPane);

		// Configure Panes & VBoxes
		menuPane.setCenter(menuButtons);
		BorderPane.setAlignment(menuButtons, Pos.CENTER);

		menuButtons.setPrefSize(width, height);

		checkerBoard.setPadding(new Insets(28, 448, 28, 448));
		checkerBoard.setId("board");

		// Initialize Scene
		Scene menuScene = new Scene(root, 1280, 720);

		// Listener For Escape Key
		menuScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent t) {
				KeyCode key = t.getCode();
				if (key == KeyCode.ESCAPE) {
					back();
				}
			}
		});

		// Activate Main Menu
		mainMenu();

		// Set Scene & Show PrimaryStage
		primaryStage.setScene(menuScene);

		this.primaryStage = primaryStage;
		primaryStage.show();
	}

	// Update Width
	public void updateWidth(double newValue, double oldValue) {
		double multiplier = newValue / oldValue;
		root.setPrefWidth(oldValue * multiplier);
		menuButtons.setPrefWidth(oldValue * multiplier);
	}

	// Update Height
	public void updateHeight(double newValue, double oldValue) {
		double multiplier = newValue / oldValue;
		root.setPrefHeight(oldValue * multiplier);
		menuButtons.setPrefHeight(oldValue * multiplier);
	}

	// Update Screen
	public void updateScreen() {
		// Remove Children From Previous Menu
		menuButtons.getChildren().remove(0, menuButtons.getChildren().size());

		// Update Screen And Delete Unwanted Children From MenuPane
		switch (menuId) {
		case 1:
			mainMenu();
			switch (menuPane.getChildren().size()) {
			case 1:
				menuPane.getChildren().remove(vBoxVolumeSlider);
				break;
			}
			break;
		case 2:
			menuSingleplayer();
			switch (menuPane.getChildren().size()) {
			case 1:
				menuPane.getChildren().remove(vBoxVolumeSlider);
				break;
			}
			break;
		case 3:
			menuMultiplayer();
			switch (menuPane.getChildren().size()) {
			case 1:
				menuPane.getChildren().remove(vBoxVolumeSlider);
				break;
			}
			break;
		case 4:
			menuSettings();
			vBoxVolumeSlider.setPrefSize(width / 3, height / 1.5);
			break;
		}
	}

	// Main Menu Method
	public void mainMenu() {
		// Set Menu ID
		setMenuId(1);

		// Initialize Buttons
		Button buttonSingleplayer = new Button("Singleplayer");
		Button buttonMultiplayer = new Button("Multiplayer");
		Button buttonHowToPlay = new Button("How To Play");
		Button buttonSettings = new Button("Settings");
		Button buttonClose = new Button("Close Game");

		// Configure Buttons
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

		// Add Buttons To MenuButtons VBox
		menuButtons.getChildren().addAll(buttonSingleplayer, buttonMultiplayer, buttonHowToPlay, buttonSettings,
				buttonClose);
		menuButtons.setAlignment(Pos.CENTER);

		// Main Menu Button Functions
		buttonSingleplayer.setOnAction(e -> {
			menuSingleplayer();
		});

		buttonMultiplayer.setOnAction(e -> {
			menuMultiplayer();
		});

		buttonHowToPlay.setOnAction(e -> {
			menuHowToPlay();
		});

		buttonSettings.setOnAction(e -> {
			menuSettings();
		});

		buttonClose.setOnAction(e -> {
			back();
		});
	}

	// Singleplayer Menu Method
	public void menuSingleplayer() {
		// Set Menu ID
		setMenuId(2);

		// Remove Buttons from previous Menu
		if (menuButtons.getChildren().size() != 0) {
			menuButtons.getChildren().remove(0, menuButtons.getChildren().size());
		}

		// Initialize Buttons
		Button buttonWhite = new Button("Play As White");
		Button buttonBlack = new Button("Play As Black");

		// Configure Buttons
		buttonWhite.setId("menu_button");
		buttonWhite.setMaxWidth(width / 3);
		buttonWhite.setAlignment(Pos.CENTER);

		buttonBlack.setId("menu_button");
		buttonBlack.setMaxWidth(width / 3);
		buttonBlack.setAlignment(Pos.CENTER);

		menuButtons.getChildren().addAll(buttonWhite, buttonBlack);
		menuButtons.setAlignment(Pos.CENTER);

		// Singleplayer Menu Button Functions
		buttonWhite.setOnAction(e -> {
			setPlayerColor("white");
			initializeBoard();

			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					// Coordinates
					int[] pieceCoordinates = { i, j };

					// Initialize Pieces
					Piece pieceWhite = new Piece(52, Color.WHITE, pieceCoordinates);
					Piece pieceBlack = new Piece(52, Color.BLACK, pieceCoordinates);

					// Add Pieces
					if (i % 2 == 0 && j % 2 == 1 || i % 2 == 1 && j % 2 == 0) {
						if (j < 3) {
							pieceBlack.setCoordinates(pieceCoordinates);
							checkerBoard.add(pieceBlack, i, j);
							boardPieces[i][j] = pieceBlack;
						} else if (j > 4) {
							pieceWhite.setCoordinates(pieceCoordinates);
							checkerBoard.add(pieceWhite, i, j);
							boardPieces[i][j] = pieceWhite;
						}
					}
				}
			}
		});

		buttonBlack.setOnAction(e -> {
			setPlayerColor("black");
			initializeBoard();

			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					// Coordinates
					int[] pieceCoordinates = { i, j };

					// Initialize Pieces
					Piece pieceWhite = new Piece(52, Color.WHITE, pieceCoordinates);
					Piece pieceBlack = new Piece(52, Color.BLACK, pieceCoordinates);

					// Add Pieces
					if (i % 2 == 0 && j % 2 == 1 || i % 2 == 1 && j % 2 == 0) {
						if (j < 3) {
							pieceWhite.setCoordinates(pieceCoordinates);
							checkerBoard.add(pieceWhite, i, j);
							boardPieces[i][j] = pieceWhite;
						} else if (j > 4) {
							pieceBlack.setCoordinates(pieceCoordinates);
							checkerBoard.add(pieceBlack, i, j);
							boardPieces[i][j] = pieceBlack;
						}
					}
				}
			}
		});
	}

	public void menuHowToPlay() {
		// Initialize AlertBox
		Alert alertHowToPlay = new Alert(AlertType.INFORMATION);

		// Alert Box Text
		alertHowToPlay.setTitle("How To Play");
		alertHowToPlay.setHeaderText("How To Play");
		alertHowToPlay.setContentText(
				"In Checkers you will face of your opponent on the board and going to try to defeat them. "
						+ "A move consists of moving a piece diagonally to an adjacent unoccupied square. If the "
						+ "adjacent square contains an opponent's piece, and the square immediately beyond it is "
						+ "vacant, the piece may be captured (and removed from the game) by jumping over it.");

		// Show AlertBox
		alertHowToPlay.show();
	}

	public void menuMultiplayer() {
		// Set Menu ID
		setMenuId(3);

		// TODO Implement Multiplayer Mode
	}

	public void menuSettings() {
		// Set Menu ID
		setMenuId(4);

		// Remove Buttons From Previous Menu
		if (menuButtons.getChildren().size() != 0) {
			menuButtons.getChildren().remove(0, menuButtons.getChildren().size());
		}

		// Initialize Volume Slider And Add To VBox
		Slider volumeSlider = new Slider(0, 100, 100);

		if (vBoxVolumeSlider.getChildren().size() == 0) {
			vBoxVolumeSlider.getChildren().add(volumeSlider);
		}

		// Configure Volume Slider VBox
		vBoxVolumeSlider.setPrefSize(width / 3, height / 1.5);
		vBoxVolumeSlider.setAlignment(Pos.CENTER);
		vBoxVolumeSlider.setId("vbox_visible_border");

		menuPane.setCenter(vBoxVolumeSlider);

		if (menuPane.getChildren().contains(vBoxVolumeSlider) == false) {
			menuPane.getChildren().add(vBoxVolumeSlider);
		}

		// Listener For Volume Slider
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
				double newVolume = newValue.doubleValue() * 0.0025;
				musicPlayer.setVolume(newVolume);

				configFile.put("volume", String.valueOf(newVolume));
				configFile.write();
			}
		});

		// TODO Fix VBox Placement
	}

	// Back Method
	public void back() {
		switch (menuId) {
		// Close Game
		case 1:
			primaryStage.close();
			break;

		// Go Back To Main Menu
		case 2:
			setMenuId(1);
			updateScreen();
			break;
		case 3:
			setMenuId(1);
			updateScreen();
			break;
		case 4:
			setMenuId(1);
			updateScreen();
			break;
		case 5:
			setMenuId(1);
			updateScreen();
			break;
		}
	}

	public void initializeBoard() {
		Scene checkerBoardScene = new Scene(checkerBoard);
		checkerBoard.setId("checkerBoardPane");
		checkerBoard.getStylesheets().add(getClass().getResource("css/board.css").toExternalForm());

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				int[] spaceCoordinates = { i, j };

				final int x = i;
				final int y = j;

				Space space = new Space("", spaceCoordinates);
				space.setPrefSize(128, 128);

				if (i % 2 == 0 && j % 2 == 0 || i % 2 == 1 && j % 2 == 1) {
					space.setId("boardSpaceBeige");
					
					sussy.amogus(space);
				} else {
					if (j < 3 || j > 4) {
						space.setContainsPiece(true);
						if (getPlayerColor() == "white") {
							if (j > 4) {
								space.setColor("white");
							} else {
								space.setColor("black");
							}
						} else {
							if (j < 3) {
								space.setColor("black");
							} else {
								space.setColor("white");
							}
						}
					}

					space.setId("boardSpaceBrown");
					space.setCoordinates(spaceCoordinates);

					space.setOnAction(e -> {
						setSelectedSpace(spaceCoordinates);

						final int sus2 = x;
						final int amogus2 = y;

						if (1 != 187) {
							if (redSpace == false && space.getContainsPiece() == true
									&& space.getColor() == getPlayerColor()) {
								space.setOnKeyPressed(new EventHandler<KeyEvent>() {
									@Override
									public void handle(KeyEvent t) {
										KeyCode key = t.getCode();
										if (key == KeyCode.ENTER) {
											space.setId("spaceSelectedRed");
											setRedSpace(true);

											int[] crewmate = space.getCoordinates();

											setSelectedSpace(crewmate);

											if (selectedSpaceLocked == false) {
												System.out.println(selectedSpace[0] + ", " + selectedSpace[1]);
												sus = sus2;
												amogus = amogus2;
												setSelectedSpaceLocked(true);
												space.setContainsPiece(false);
											}
										}
									}
								});
							} else if (getRedSpace() == true && getGreenSpace() == false
									&& space.getContainsPiece() == false) {
								space.setOnKeyPressed(new EventHandler<KeyEvent>() {
									@Override
									public void handle(KeyEvent t) {
										KeyCode key = t.getCode();
										if (key == KeyCode.ENTER) {
											space.setId("spaceSelectedGreen");
											setGreenSpace(true);

											updateBoard();
										}
									}
								});
							} else if (getRedSpace() == true && getGreenSpace() == true) {
								space.setOnKeyPressed(new EventHandler<KeyEvent>() {
									@Override
									public void handle(KeyEvent t) {
										KeyCode key = t.getCode();
										if (key == KeyCode.SPACE) {
											int[] z = { x, y };

											if (getPlayerColor() == "white") {
												boardPieces[x][y] = new Piece(52, Color.WHITE, z);
											} else if (getPlayerColor() == "black") {
												boardPieces[x][y] = new Piece(52, Color.BLACK, z);
											}

											boardPieces[sus][amogus] = null;
											boardSpaces[x][y].setId("boardSpaceBrown");

											setGreenSpace(false);
											setRedSpace(false);
											setSelectedSpaceLocked(false);

											space.setContainsPiece(true);

											for (Space[] imposterSus : boardSpaces) {
												for (int i = 0; i < 8; i++) {
													if (imposterSus[i].getId() != "boardSpaceBeige"
															&& imposterSus[i].getId() != "boardSpaceBrown") {
														imposterSus[i].setId("boardSpaceBrown");
													}
												}
											}

											updateBoard();

											System.out.println(boardPieces[selectedSpace[0]][selectedSpace[1]]);
										}
									}
								});
							}
						}
					});
				}

				boardSpaces[i][j] = space;
				checkerBoard.add(space, i, j);
			}
		}

		primaryStage.setScene(checkerBoardScene);
	}

	public void waitForTurn(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void updateBoard() {
		checkerBoard.getChildren().remove(0, checkerBoard.getChildren().size());

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (boardSpaces[i][j] instanceof Space) {
					checkerBoard.add(boardSpaces[i][j], i, j);
				}
			}
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (boardPieces[i][j] instanceof Piece) {
					checkerBoard.add(boardPieces[i][j], i, j);
				}
			}
		}
	}

	// Setters
	public void setGreenSpace(boolean greenSpace) {
		this.greenSpace = greenSpace;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public void setRedSpace(boolean redSpace) {
		this.redSpace = redSpace;
	}

	public void setSelectedSpace(int[] selectedSpace) {
		this.selectedSpace = selectedSpace;
	}

	public void setPlayerColor(String playerColor) {
		this.playerColor = playerColor;
	}

	public void setBoardPieces(Piece[][] boardPieces) {
		this.boardPieces = boardPieces;
	}

	public void setSelectedSpaceLocked(boolean locked) {
		this.selectedSpaceLocked = locked;
	}

	// Getters
	public boolean getGreenSpace() {
		return this.greenSpace;
	}

	public int getMenuId() {
		return this.menuId;
	}

	public boolean getRedSpace() {
		return this.redSpace;
	}

	public String getPlayerColor() {
		return this.playerColor;
	}
}