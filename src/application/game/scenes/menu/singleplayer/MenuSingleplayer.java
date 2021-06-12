package application.game.scenes.menu.singleplayer;

import application.game.StageController;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MenuSingleplayer extends Scene 
{
    public MenuSingleplayer(StageController stageController)
    {
        super(new Root(stageController));

        setOnKeyPressed((EventHandler<? super KeyEvent>) new EventHandler<KeyEvent>()
        {
			@Override
			public void handle(KeyEvent t)
            {
				if (t.getCode() == KeyCode.ESCAPE)
					stageController.loadScene("MENU_MAIN");
			}
		});
    }    
}
