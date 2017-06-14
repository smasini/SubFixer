package ui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by Simone Masini on 14/06/2017.
 */
public class MainController {

    @FXML
    private ChangeController changePageController;
    @FXML
    private ConvertController convertPageController;

    private Stage mStage;

    public void setStageAndSetupListeners(Stage stage) {
        this.mStage = stage;

    }
}
