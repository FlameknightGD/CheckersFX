package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

import application.exceptions.InvalidParamException;
import application.game.Piece;
import application.game.Space;
import application.utils.ConfigHandler;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.stage.Stage;

public class Main extends Application {
	// Useless Comment

	// Initialize Primary Stage
	Stage primaryStage;

	// Initialize Panes
	Pane root;
	BorderPane menuPane = new BorderPane();
	public GridPane checkerBoard = new GridPane();

	// Initialize VBoxes
	VBox menuButtons = new VBox();

	VBox vBoxVolumeSlider = new VBox();

	// Initialize Config File
	File configFile = new File("config/config.txt");
	ConfigHandler configHandler = new ConfigHandler(configFile);

	// Media Players
	Media backgroundMusic = new Media(
			Paths.get("assets/audio/music/its_raining_somewhere_else.wav").toUri().toString());
	Media ingameMusic = new Media(Paths.get("assets/audio/music/megalovania.wav").toUri().toString());
	MediaPlayer musicPlayer = new MediaPlayer(backgroundMusic);

	Media moveSound = new Media(Paths.get("assets/audio/sound/move.wav").toUri().toString());
	MediaPlayer soundPlayer = new MediaPlayer(moveSound);

	// Global Variables
	double width;
	double height;

	int menuId;

	int[] selectedSpace = new int[2];

	boolean redSpaceActive = false;
	boolean greenSpaceActive = false;

	String turnColor;

	Space[][] boardSpaces = new Space[8][8];
	Piece[][] boardPieces = new Piece[8][8];

	boolean spaceLock = false;

	int coordUtilX;
	int coordUtilY;

	// Main Method
	public static void main(String[] args) {
		launch(args);
	}

	// Start Method
	@Override
	public void start(Stage primaryStage) throws InvalidParamException, IOException {
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
			try {
				updateScreen();
			} catch (InvalidParamException e) {
				e.printStackTrace();
			}
		});

		primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
			height = (double) newVal;
			updateHeight((double) newVal, (double) oldVal);
			try {
				updateScreen();
			} catch (InvalidParamException e) {
				e.printStackTrace();
			}
		});

		// Set Size Of VBox
		menuButtons.setPrefSize(width, height);

		// Configure Music Player
		musicPlayer.setAutoPlay(true);
		musicPlayer.setCycleCount(2147483647);
		musicPlayer.setVolume(configHandler.getVolumeFromConfig(configFile));

		soundPlayer.setVolume(configHandler.getVolumeFromConfig(configFile) * 7);

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
					try {
						back();
					} catch (InvalidParamException e) {
						e.printStackTrace();
					}
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
	public void updateScreen() throws InvalidParamException {
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
			menuPlay();
			switch (menuPane.getChildren().size()) {
			case 1:
				menuPane.getChildren().remove(vBoxVolumeSlider);
				break;
			}
			break;
		case 3:
			menuHowToPlay();
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
	public void mainMenu() throws InvalidParamException {
		// Set Menu ID
		setMenuId(1);

		// Initialize Buttons
		Button buttonPlay = new Button("Play");
		Button buttonHowToPlay = new Button("How To Play");
		Button buttonSettings = new Button("Settings");
		Button buttonClose = new Button("Close Game");

		// Configure Buttons
		buttonPlay.setId("menu_button");
		buttonPlay.setMaxWidth(width / 3);
		buttonPlay.setAlignment(Pos.CENTER);

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
		menuButtons.getChildren().addAll(buttonPlay, buttonHowToPlay, buttonSettings, buttonClose);
		menuButtons.setAlignment(Pos.CENTER);

		// Main Menu Button Functions
		buttonPlay.setOnAction(e -> {
			try {
				menuPlay();
			} catch (InvalidParamException invParamException) {
				invParamException.printStackTrace();
			}
		});

		buttonHowToPlay.setOnAction(e -> {
			try {
				menuHowToPlay();
			} catch (InvalidParamException invParamException) {
				invParamException.printStackTrace();
			}
		});

		buttonSettings.setOnAction(e -> {
			try {
				menuSettings();
			} catch (InvalidParamException invParamException) {
				invParamException.printStackTrace();
			}
		});

		buttonClose.setOnAction(e -> {
			try {
				back();
			} catch (InvalidParamException invParamException) {
				invParamException.printStackTrace();
			}
		});
	}

	// Play Menu Method
	public void menuPlay() throws InvalidParamException {
		// Set Menu ID
		setMenuId(2);

		// Remove Buttons from previous Menu
		if (menuButtons.getChildren().size() != 0) {
			menuButtons.getChildren().remove(0, menuButtons.getChildren().size());
		}

		/*
		 * // Initialize Buttons Button buttonWhite = new Button("Play As White");
		 * Button buttonBlack = new Button("Play As Black");
		 * 
		 * // Configure Buttons buttonWhite.setId("menu_button");
		 * buttonWhite.setMaxWidth(width / 3); buttonWhite.setAlignment(Pos.CENTER);
		 * 
		 * buttonBlack.setId("menu_button"); buttonBlack.setMaxWidth(width / 3);
		 * buttonBlack.setAlignment(Pos.CENTER);
		 * 
		 * menuButtons.getChildren().addAll(buttonWhite, buttonBlack);
		 * menuButtons.setAlignment(Pos.CENTER);
		 */

		/*
		 * Singleplayer Menu Button Functions buttonWhite.setOnAction(e -> {
		 * musicPlayer.stop(); musicPlayer = new MediaPlayer(ingameMusic);
		 * musicPlayer.play(); musicPlayer.setCycleCount(999);
		 * 
		 * try { setPlayerColor("white"); } catch (InvalidParamException
		 * invParamException) { invParamException.printStackTrace(); }
		 * 
		 * try { initializeBoard(); } catch (InvalidParamException invParamException) {
		 * invParamException.printStackTrace(); }
		 * 
		 * for (int i = 0; i < 8; i++) { for (int j = 0; j < 8; j++) { // Coordinates
		 * int[] pieceCoordinates = { i, j };
		 * 
		 * try { // Initialize Pieces Piece pieceWhite = new Piece(52, Color.WHITE,
		 * pieceCoordinates); Piece pieceBlack = new Piece(52, Color.BLACK,
		 * pieceCoordinates);
		 * 
		 * // Add Pieces if (i % 2 == 0 && j % 2 == 1 || i % 2 == 1 && j % 2 == 0) { if
		 * (j < 3) { pieceBlack.setCoordinates(pieceCoordinates);
		 * checkerBoard.add(pieceBlack, i, j); boardPieces[i][j] = pieceBlack; } else if
		 * (j > 4) { pieceWhite.setCoordinates(pieceCoordinates);
		 * checkerBoard.add(pieceWhite, i, j); boardPieces[i][j] = pieceWhite; } } }
		 * catch (InvalidParamException invalidParamException) {
		 * invalidParamException.printStackTrace(); } } } });
		 * 
		 * buttonBlack.setOnAction(e -> { try { setPlayerColor("black"); } catch
		 * (InvalidParamException invParamException) {
		 * invParamException.printStackTrace(); }
		 * 
		 * try { initializeBoard(); } catch (InvalidParamException invParamException) {
		 * invParamException.printStackTrace(); }
		 * 
		 * for (int i = 0; i < 8; i++) { for (int j = 0; j < 8; j++) { // Coordinates
		 * int[] pieceCoordinates = { i, j };
		 * 
		 * try { // Initialize Pieces Piece pieceWhite = new Piece(52, Color.WHITE,
		 * pieceCoordinates); Piece pieceBlack = new Piece(52, Color.BLACK,
		 * pieceCoordinates);
		 * 
		 * // Add Pieces if (i % 2 == 0 && j % 2 == 1 || i % 2 == 1 && j % 2 == 0) { if
		 * (j < 3) { pieceWhite.setCoordinates(pieceCoordinates);
		 * checkerBoard.add(pieceWhite, i, j); boardPieces[i][j] = pieceWhite; } else if
		 * (j > 4) { pieceBlack.setCoordinates(pieceCoordinates);
		 * checkerBoard.add(pieceBlack, i, j); boardPieces[i][j] = pieceBlack; } } }
		 * catch (InvalidParamException invParamException) {
		 * invParamException.printStackTrace(); } } } });
		 */

		musicPlayer.stop();
		musicPlayer = new MediaPlayer(ingameMusic);
		musicPlayer.play();
		musicPlayer.setCycleCount(999);

		try {
			setPlayerColor("white");
		} catch (InvalidParamException invParamException) {
			invParamException.printStackTrace();
		}

		try {
			initializeBoard();
		} catch (InvalidParamException invParamException) {
			invParamException.printStackTrace();
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				// Coordinates
				int[] pieceCoordinates = { i, j };

				try {
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
				} catch (InvalidParamException invalidParamException) {
					invalidParamException.printStackTrace();
				}
			}
		}
	}

	public void menuHowToPlay() throws InvalidParamException {
		// Set Menu ID
		setMenuId(3);

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

	public void menuSettings() throws InvalidParamException {
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
		vBoxVolumeSlider.setPrefSize(width / 3, height);
		vBoxVolumeSlider.setAlignment(Pos.CENTER);
		vBoxVolumeSlider.setId("vbox_visible_border");

		vBoxVolumeSlider.setPrefSize(width / 3, height);
		vBoxVolumeSlider.setAlignment(Pos.CENTER_LEFT);
		vBoxVolumeSlider.setId("vbox_visible_border");

		menuPane.setCenter(vBoxVolumeSlider);

		if (menuPane.getChildren().contains(vBoxVolumeSlider) == false) {
			menuPane.getChildren().add(vBoxVolumeSlider);
		}

		HashMap<String, String> values = new HashMap<String, String>();

		// Listener For Volume Slider
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
				double newVolume = newValue.doubleValue() * 0.0025;
				musicPlayer.setVolume(newVolume);
				soundPlayer.setVolume(newVolume * 7);

				values.put("volume", String.valueOf(newVolume));

				try {
					configHandler.writeConfig(values);
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
		});

		// TODO Fix VBox Placement
	}

	// Back Method
	public void back() throws InvalidParamException {
		switch (menuId) {
		// Close Game
		case 1:
			primaryStage.close();
			break;

		// Go Back To Main Menu
		case 2:
			setMenuId(6);
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

	public void initializeBoard() throws InvalidParamException {
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
				} else {
					if (j < 3 || j > 4) {
						space.setContainsPiece(true);
						if (getPlayerColor() == "white") {
							if (j > 4) {
								space.setPieceColor("white");
							} else {
								space.setPieceColor("black");
							}
						} else {
							if (j < 3) {
								space.setPieceColor("black");
							} else {
								space.setPieceColor("white");
							}
						}
					}

					space.setId("boardSpaceBrown");
					space.setCoordinates(spaceCoordinates);

					space.setOnAction(e -> {
						try {
							setSelectedSpace(spaceCoordinates);
						} catch (InvalidParamException invParamException) {
							invParamException.printStackTrace();
						}

						final int coordUtilXTemp = x;
						final int coordUtilYTemp = y;

						if (1 != 187) {
							if (getRedSpaceActive() == false && space.getContainsPiece() == true
									&& space.getPieceColor() == getPlayerColor()) {
								space.setOnKeyPressed(new EventHandler<KeyEvent>() {
									@Override
									public void handle(KeyEvent t) {
										KeyCode key = t.getCode();
										if (key == KeyCode.ENTER) {
											space.setId("spaceSelectedRed");
											setRedSpaceActive(true);

											int[] coordsTemp = space.getCoordinates();
											try {
												setSelectedSpace(coordsTemp);
											} catch (InvalidParamException invParamException) {
												invParamException.printStackTrace();
											}

											if (getSpaceLock() == false) {
												coordUtilX = coordUtilXTemp;
												coordUtilY = coordUtilYTemp;

												setSelectedSpaceLocked(true);
												space.setContainsPiece(false);
											}
										}
									}
								});
							} else if (getRedSpaceActive() == true && getGreenSpaceActive() == false
									&& space.getContainsPiece() == false) {
								space.setOnKeyPressed(new EventHandler<KeyEvent>() {
									@Override
									public void handle(KeyEvent t) {
										KeyCode key = t.getCode();
										if (key == KeyCode.ENTER) {
											space.setId("spaceSelectedGreen");
											setGreenSpaceActive(true);

											if (getPlayerColor() == "white") {
												try {
													space.setPieceColor("white");
												} catch (InvalidParamException invParamException) {
													invParamException.printStackTrace();
												}
											} else if (getPlayerColor() == "black") {
												try {
													space.setPieceColor("black");
												} catch (InvalidParamException invParamException) {
													invParamException.printStackTrace();
												}
											}

											System.out.println(space.getContainsPiece());

											updateBoard();
										}
									}
								});
							} else if (getRedSpaceActive() == true && getGreenSpaceActive() == true) {
								space.setOnKeyPressed(new EventHandler<KeyEvent>() {
									@Override
									public void handle(KeyEvent t) {
										KeyCode key = t.getCode();
										if (key == KeyCode.SPACE) {
											int[] z = { x, y };

											if (getPlayerColor() == "white") {
												try {
													boardPieces[x][y] = new Piece(52, Color.WHITE, z);
												} catch (InvalidParamException invParamException) {
													invParamException.printStackTrace();
												}
											} else if (getPlayerColor() == "black") {
												try {
													boardPieces[x][y] = new Piece(52, Color.BLACK, z);
												} catch (InvalidParamException invParamException) {
													invParamException.printStackTrace();
												}
											}

											boardSpaces[coordUtilX][coordUtilY].setContainsPiece(false);
											boardPieces[coordUtilX][coordUtilY] = null;

											boardSpaces[x][y].setId("boardSpaceBrown");

											setGreenSpaceActive(false);
											setRedSpaceActive(false);
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
											soundPlayer.play();

											if (getPlayerColor() == "white") {
												try {
													setPlayerColor("black");
												} catch (InvalidParamException invParamException) {
													invParamException.printStackTrace();
												}
											} else if (getPlayerColor() == "black") {
												try {
													setPlayerColor("white");
												} catch (InvalidParamException invParamException) {
													invParamException.printStackTrace();
												}
											}

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
		
		if (primaryStage.getScene().getRoot() instanceof GridPane == false) {
			primaryStage.setScene(checkerBoardScene);
		}
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
	public void setGreenSpaceActive(boolean greenSpaceActive) {
		this.greenSpaceActive = greenSpaceActive;
	}

	public void setMenuId(int menuId) throws InvalidParamException {
		if (menuId > 0 && menuId < 6) {
			this.menuId = menuId;
		} else {
			throw new InvalidParamException("error: invalid parameter exception: " + menuId + " is not a valid menuId");
		}
	}

	public void setRedSpaceActive(boolean redSpaceActive) {
		this.redSpaceActive = redSpaceActive;
	}

	public void setSelectedSpace(int[] selectedSpace) throws InvalidParamException {
		if (selectedSpace[0] > -1 && selectedSpace[0] < 8 && selectedSpace[1] > -1 && selectedSpace[1] < 8) {
			this.selectedSpace = selectedSpace;
		} else {
			throw new InvalidParamException("error: invalid parameter exception: the coordinates " + selectedSpace[0]
					+ ", " + selectedSpace[1] + " are out of bounds");
		}
	}

	public void setPlayerColor(String turnColor) throws InvalidParamException {
		if (turnColor == "white" || turnColor == "black") {
			this.turnColor = turnColor;
		} else {
			throw new InvalidParamException(
					"error: invalid parameter exception: player color '" + turnColor + "' doesn't exist");
		}
	}

	public void setSelectedSpaceLocked(boolean spaceLock) {
		this.spaceLock = spaceLock;
	}

	// Getters
	public boolean getGreenSpaceActive() {
		return this.greenSpaceActive;
	}

	public int getMenuId() {
		return this.menuId;
	}

	public boolean getRedSpaceActive() {
		return this.redSpaceActive;
	}

	public String getPlayerColor() {
		return this.turnColor;
	}

	public boolean getSpaceLock() {
		return this.spaceLock;
	}
}