package ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.SubChanger;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class ChangeController {

    private Stage mStage;

    @FXML
    private TextField txtInputFile;
    @FXML
    private TextField txtFromTime;
    @FXML
    private TextField txtToTime;
    @FXML
    private TextField txtChangeValue;
    @FXML
    private RadioButton rdbAnticipate;
    @FXML
    private RadioButton rdbPosticipate;

    @FXML
    private void btnBrowseAction(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open sub file");
        File file = fileChooser.showOpenDialog(mStage);
        if(file != null){
            txtInputFile.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void btnSubmitAction(){
        int changeValue = Integer.parseInt(txtChangeValue.getText());
        SubChanger subChanger = new SubChanger(txtInputFile.getText(), rdbAnticipate.isSelected(), txtFromTime.getText(), txtToTime.getText(), changeValue);
        subChanger.parseAndSave();
    }

    public void setStageAndSetupListeners(Stage stage) {
        this.mStage = stage;
    }

    /*
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(location.getPath());
    }*/
}
