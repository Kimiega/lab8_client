import client.Environment;
import cmd.ICommand;
import controllersScenes.ElementWindowController;
import controllersScenes.MainWindowController;
import exceptions.ResourceException;
import ioManager.ConsoleManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.ObservableResourceFactory;

import java.io.IOException;
import java.util.*;

public class MainApp extends Application {
    static ResourceBundle rb;
    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Locale[] locales ={
                new Locale.Builder().setLanguageTag("ru-RU").build(),
                new Locale.Builder().setLanguageTag("en-US").build()
        };

        ResourceBundle resourceBundle;
        try {
            resourceBundle = ResourceBundle.getBundle("bundleLocales/locale");
        } catch (
    MissingResourceException e)
    {
        throw new ResourceException(e.getKey());
    }
        Environment env = new Environment(new HashMap<String, ICommand>(), ConsoleManager.getInstance(),ConsoleManager.getInstance(),false,312);
        MainWindowController.setEnv(env);
        ObservableResourceFactory orf = new ObservableResourceFactory(resourceBundle,locales);
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenesFXML/MainWindow.fxml"),orf.getResources());
        MainWindowController mainWindowController = loader.getController();
        root = loader.load();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Super Table Viewer 3000");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }
}
