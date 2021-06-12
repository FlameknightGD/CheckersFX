package application.game;

import java.util.HashMap;
import java.util.Objects;

import javafx.scene.Scene;
import javafx.stage.Stage;


public class StageController extends HashMap<String, Scene>
{
    private Stage stage;

    public StageController(Stage stage)
    {
        this.setStage(stage);
    }

    public Scene addScene(String key, Scene value)
    {
        return this.put(key, value);
    }

    public Scene removeScene(String key)
    {
        return this.remove(key);
    }

    public void loadScene(String sceneKey)  
    {
        stage.setScene(this.get(sceneKey));
    }

    public void exit()
    {
        stage.close();
    }

    public Stage getStage()
    {
        return this.stage;
    }

    private void setStage(Stage stage)
    {
        this.stage = stage;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this)
            return true;

        if (!(o instanceof StageController))
            return false;

        StageController stageController = (StageController) o;
        return Objects.equals(stage, stageController.stage);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(stage);
    }
}
