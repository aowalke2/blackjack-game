package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {
    @FXML
    TextField nameTextField;

    private Stage stage;
    private Scene scene;
    private Parent root;

    //This method transitions to next scene after entering name and pressing button
    public void startGame(ActionEvent event) throws IOException {
        String playerName = nameTextField.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("gameScene.fxml"));
        root = loader.load();

        //This code shows the players name see -> BettingController class for info on displayName method.
        GameController gameController = loader.getController();
        gameController.displayName(playerName);
        gameController.hideStuff();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
