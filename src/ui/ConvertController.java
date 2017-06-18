package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.SubConverter;
import logic.Utility;

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
    private ProgressBar progress;

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
        progress.setProgress(0);
        if(files != null) {
            int i = 1;
            for (File file : files) {
                SubConverter.convertFile(file);
                progress.setProgress(i*files.size() / 100);
                i++;
            }
            progress.setProgress(100);
        }
    }

    public void setStageAndSetupListeners(Stage stage) {
        this.mStage = stage;
    }
}
