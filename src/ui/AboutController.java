package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Simone Masini on 18/06/2017.
 */
public class AboutController {

    @FXML
    private Hyperlink githubLink;

    @FXML
    private Hyperlink mailLink;

    @FXML
    public void mailClick(){
        mailTo(mailLink.getText());
    }

    @FXML
    public void linkClick(){
        openWebpage(githubLink.getText());
    }


    private void openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URL(url).toURI());
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(AboutController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mailTo(String address){
        try {
            Desktop.getDesktop().mail(new URI("mailto:" + address));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
