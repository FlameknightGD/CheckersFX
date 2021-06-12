package application.game.scenes.menu.main;

import application.game.StageController;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Root extends VBox
{
    private Button buttonSingleplayer;
    private Button buttonMultiplayer;
    private Button buttonHowTo;
    private Button buttonSettings;
    private Button buttonClose;

    /**
     * Initialises an extension of VBox which is used as root for the "MENU_MAIN" / MenuMain scene.
     * @param stageController Reference to the stage controller.
     */

    public Root(StageController stageController)
    {
        super();

        /**
         * Sets up menu items.
         */

        this.setButtonSingleplayer(new Button("Singleplayer"));
        this.setButtonMultiplayer(new Button("Multiplayer"));
        this.setButtonHowTo(new Button("Multiplayer"));
        this.setButtonSettings(new Button("Settings"));
        this.setButtonClose(new Button("Close Game"));

		this.getButtonSingleplayer().setId("MENU_MAIN_BUTTON_SP");
		this.getButtonSingleplayer().setMaxWidth(stageController.getStage().getMaxWidth() / 3);
		this.getButtonSingleplayer().setAlignment(Pos.CENTER);

		this.getButtonMultiplayer().setId("MENU_MAIN_BUTTON_MP");
		this.getButtonMultiplayer().setMaxWidth(stageController.getStage().getMaxWidth() / 3);
		this.getButtonMultiplayer().setAlignment(Pos.CENTER);

		this.getButtonHowTo().setId("MENU_MAIN_BUTTON_HOW_TO");
		this.getButtonHowTo().setMaxWidth(stageController.getStage().getMaxWidth() / 3);
		this.getButtonHowTo().setAlignment(Pos.CENTER);

		this.getButtonSettings().setId("MENU_MAIN_BUTTON_SETTINGS");
		this.getButtonSettings().setMaxWidth(stageController.getStage().getMaxWidth() / 3);
		this.getButtonSettings().setAlignment(Pos.CENTER);

		this.getButtonClose().setId("MENU_MAIN_BUTTON_CLOSE");
		this.getButtonClose().setMaxWidth(stageController.getStage().getMaxWidth() / 3);
		this.getButtonClose().setAlignment(Pos.CENTER);

        /**
         * Event listener for buttonSingleplayer; loads "MENU_SP" scene.
         */

		this.getButtonSingleplayer().setOnAction(e -> {
            stageController.loadScene("MENU_SP");
		});

        /**
         * Event listener for buttonMultiplayer; loads "MENU_MP" scene.
         */

		this.getButtonMultiplayer().setOnAction(e -> {
            stageController.loadScene("MENU_MP");
		});

        /**
         * Event listener for buttonHowTo; shows an alert box with game instructions.
         */

		this.getButtonHowTo().setOnAction(e -> {
            Alert alertHowTo = new Alert(AlertType.INFORMATION);

            // alert box text
            alertHowTo.setTitle("How To Play");
            alertHowTo.setHeaderText("How To Play");
            alertHowTo.setContentText(
                    "In Checkers you will face of your opponent on the board and going to try to defeat them. "
                            + "A move consists of moving a piece diagonally to an adjacent unoccupied square. If the "
                            + "adjacent square contains an opponent's piece, and the square immediately beyond it is "
                            + "vacant, the piece may be captured (and removed from the game) by jumping over it.");
    
            // show alert box
            alertHowTo.show();
        });

        /**
         * Event listener for buttonSettings; loads "MENU_SETTINGS" scene.
         */

		this.getButtonSettings().setOnAction(e -> {
            stageController.loadScene("MENU_SETTINGS");
		});

        /**
         * Event listener for buttonClose; exits game.
         */

		this.getButtonClose().setOnAction(e -> {
            stageController.exit();
		});

        /**
         * Appends buttons to extended VBox and sets button alignment.
         */

        this.getChildren().addAll(this.getButtonSingleplayer(), this.getButtonMultiplayer(), this.getButtonHowTo(), this.getButtonSettings(), this.getButtonClose());
        this.setAlignment(Pos.CENTER);
    }


    public Button getButtonSingleplayer()
    {
        return this.buttonSingleplayer;
    }

    public void setButtonSingleplayer(Button buttonSingleplayer)
    {
        this.buttonSingleplayer = buttonSingleplayer;
    }

    public Button getButtonMultiplayer()
    {
        return this.buttonMultiplayer;
    }

    public void setButtonMultiplayer(Button buttonMultiplayer)
    {
        this.buttonMultiplayer = buttonMultiplayer;
    }

    public Button getButtonHowTo()
    {
        return this.buttonHowTo;
    }

    public void setButtonHowTo(Button buttonHowTo)
    {
        this.buttonHowTo = buttonHowTo;
    }

    public Button getButtonSettings()
    {
        return this.buttonSettings;
    }

    public void setButtonSettings(Button buttonSettings)
    {
        this.buttonSettings = buttonSettings;
    }

    public Button getButtonClose()
    {
        return this.buttonClose;
    }

    public void setButtonClose(Button buttonClose)
    {
        this.buttonClose = buttonClose;
    }
}
