package nonogram.menu;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public abstract class Menu {

    public GridPane menuGridPane;
    public Scene menuScene;

    public abstract Scene menuSetUp();

    public Scene getMenuScene() {
        return menuScene;
    }
}
