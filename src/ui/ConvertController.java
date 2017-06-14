package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.SubConverter;

import java.io.File;
import java.util.List;

/**
 * Created by Simone Masini on 14/06/2017.
 */
public class ConvertController {

    private Stage mStage;
    private List<File> files;

    @FXML
    private TextField txtInputFile;

    @FXML
    private void btnBrowseAction(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open sub file");
        files = fileChooser.showOpenMultipleDialog(mStage);
        if(files != null && files.size() > 0){
            txtInputFile.setText(files.size() + " files selected");
        }
    }

    @FXML
    private void btnSubmitAction(){
        if(files != null) {
            for (File file : files) {
                SubConverter.convertFile(file);
            }
        }
    }

    public void setStageAndSetupListeners(Stage stage) {
        this.mStage = stage;
    }
}
