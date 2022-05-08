package AdminEntryScene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Bank;

import java.net.URL;

public class MainOfJavaFx extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();

        // load main fxml
        URL mainFXML = getClass().getResource("AdminEntrySceneFxml.fxml");
        loader.setLocation(mainFXML);
        ScrollPane root = loader.load(); //if the scroll pane is wraped around border pane, should i write border pane in here?

        // wire up controller
       AdminEntryController adminEntryController = loader.getController();
       Bank bank = new model.Bank();
       adminEntryController.setPrimaryStage(primaryStage);
       adminEntryController.setBusinessLogic(bank);

        // set stage
        primaryStage.setTitle("Alternative Banking System using javaFX");
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
