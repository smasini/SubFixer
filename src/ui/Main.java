package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        MainController controller = (MainController) loader.getController();
        controller.setStageAndSetupListeners(primaryStage); // or what you want to do
        primaryStage.setTitle("Sub Fixer");
        primaryStage.setScene(new Scene(root, 600, 375));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
