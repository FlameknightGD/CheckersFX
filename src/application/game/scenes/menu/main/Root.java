package application.game.scenes.menu.main;

import application.game.StageController;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Root extends VBox
{
    private Button buttonSingleplayer;
    private Button buttonMultiplayer;
    private Button buttonHowTo;
    private Button buttonSettings;
    private Button buttonClose;

    public Root(StageController stageController)
    {
        super();

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

        // Main Menu Button Functions

		this.getButtonSingleplayer().setOnAction(e -> {
            stageController.loadScene("MENU_SP");
		});

		this.getButtonMultiplayer().setOnAction(e -> {
            stageController.loadScene("MENU_MP");
		});

		this.getButtonHowTo().setOnAction(e -> {
            stageController.loadScene("MENU_HOW_TO");
		});

		this.getButtonSettings().setOnAction(e -> {
            stageController.loadScene("MENU_SETTINGS");
		});

		this.getButtonClose().setOnAction(e -> {
            stageController.exit();
		});

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
