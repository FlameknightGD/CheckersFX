package application.game.scenes.menu.main;

import application.game.StageController;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MenuMain extends Scene
{
    public MenuMain(StageController stageController)
    {
        super(new Root(stageController));

        this.setOnKeyPressed((EventHandler<? super KeyEvent>) new EventHandler<KeyEvent>()
        {
			@Override
			public void handle(KeyEvent t)
            {
				if (t.getCode() == KeyCode.ESCAPE)
					stageController.exit();
			}
		});
    }
}
