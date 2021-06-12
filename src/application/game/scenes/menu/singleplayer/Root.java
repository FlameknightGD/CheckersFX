package application.game.scenes.menu.singleplayer;

import application.game.StageController;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Root extends VBox
{
    private Button buttonWhite;
    private Button buttonBlack;
    
    public Root(StageController stageController)
    {
        super();

        this.setButtonWhite(new Button("Play As White"));
        this.setButtonBlack(new Button("Play As Black"));

		this.getButtonWhite().setId("MENU_SP_BUTTON_WHITE");
		this.getButtonWhite().setMaxWidth(stageController.getStage().getMaxWidth() / 3);
		this.getButtonWhite().setAlignment(Pos.CENTER);

		this.getButtonBlack().setId("MENU_SP_BUTTON_BLACK");
		this.getButtonBlack().setMaxWidth(stageController.getStage().getMaxWidth() / 3);
		this.getButtonBlack().setAlignment(Pos.CENTER);

        this.getButtonWhite().setOnAction(e -> {
            stageController.loadScene("SP_PLAY_WHITE");
        });

        this.getButtonBlack().setOnAction(e -> {
            stageController.loadScene("SP_PLAY_BLACK");
        });

        this.getChildren().addAll(this.getButtonWhite(), this.getButtonBlack());
        this.setAlignment(Pos.CENTER);
    }

    public Button getButtonWhite()
    {
        return this.buttonWhite;
    }

    public void setButtonWhite(Button buttonWhite)
    {
        this.buttonWhite = buttonWhite;
    }

    public Button getButtonBlack()
    {
        return this.buttonBlack;
    }

    public void setButtonBlack(Button buttonBlack)
    {
        this.buttonBlack = buttonBlack;
    }
}
