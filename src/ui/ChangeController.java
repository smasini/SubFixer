package ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.Callback;
import logic.SubChanger;
import logic.Utility;

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
    private ProgressBar progress;


    @FXML
    private void btnBrowseAction(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open sub file");
        if(txtInputFile.getText() != null && !txtInputFile.getText().isEmpty()){
            File file = new File(txtInputFile.getText());
            File dir = Utility.getFirstDirectory(file);
            if(dir!=null){
                fileChooser.setInitialDirectory(dir);
            }
        }
        File file = fileChooser.showOpenDialog(mStage);
        if(file != null){
            txtInputFile.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void btnSubmitAction(){
        int changeValue = Integer.parseInt(txtChangeValue.getText());
        final SubChanger subChanger = new SubChanger(txtInputFile.getText(), rdbAnticipate.isSelected(), txtFromTime.getText(), txtToTime.getText(), changeValue);
        progress.setProgress(10);
        subChanger.setCallback(new Callback() {
            @Override
            public void onComplete() {
                progress.setProgress(100);
            }

            @Override
            public void onError() {

            }
        });
        Runnable r = new Runnable() {
            public void run() {
                subChanger.parseAndSave();
            }
        };
        new Thread(r).start();
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
