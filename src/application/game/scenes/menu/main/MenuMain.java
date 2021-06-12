package application.game.scenes.menu.main;

import application.game.StageController;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MenuMain extends Scene
{
    /**
     * Initialises an extension of Scene which is used as "MENU_MAIN" / MenuMain scene.
     * @param stageController Reference to the stage controller.
     */

    public MenuMain(StageController stageController)
    {
        super(new Root(stageController));

        /**
         * Event listener for ESC (Escape) key; exits game.
         */

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
